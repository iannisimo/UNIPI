import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;

public class PingServer {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: java PingServer port [seed]");
            System.exit(1);
        }

        final int port = Integer.parseInt(args[0]);
        final int seed = (args.length >= 2) ? Integer.parseInt(args[1]) : 1337;
        final NetErr netErr = new NetErr(seed);

        Selector selector;
        try {
            selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(port);
            channel.socket().bind(socketAddress);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ).attach(new ReqResp());
            System.out.println("*** PingServer Ready ***");
        } catch(IOException e) {
            e.printStackTrace();
           return;
        }
        while(true) {
            try {
                selector.select();
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if(key.isReadable()) {
                        DatagramChannel channel = (DatagramChannel) key.channel();
                        ReqResp rr = (ReqResp) key.attachment();
                        rr.socketAddress = channel.receive(rr.req);
                        rr.req.flip();
                        int wait = netErr.sendAfter();
                        channel.register(selector, SelectionKey.OP_READ, new ReqResp());
                        if(wait != -1) {
                            System.out.printf("%s> %s ACTION: delayed %d ms\n", rr.socketAddress.toString().substring(1), new String(rr.req.array()), wait);
                            rr.resp = rr.req;
                            new Thread(new KeyWait(channel, selector, rr, wait)).run();
                        } else {
                            System.out.printf("%s> %s ACTION: not sent\n", rr.socketAddress.toString().substring(1), new String(rr.req.array()));
                        }
                    } else if(key.isWritable()) {
                        DatagramChannel channel = (DatagramChannel) key.channel();
                        ReqResp rr = (ReqResp) key.attachment();
                        
                        // Debug
                        String host = rr.socketAddress.toString();
                        String timestamp = (new String(rr.resp.array()).split("\0")[0].split(" ")[2]);
                        System.out.printf("%s: sent after %d ms\n", host, new Date().getTime() - Long.parseLong(timestamp));

                        channel.send(rr.resp, rr.socketAddress);
                        channel.register(selector, SelectionKey.OP_READ, new ReqResp());
                    }
                } catch(IOException e) {
                    key.cancel();
                    e.printStackTrace();
                }
            }
        }
    }
}
