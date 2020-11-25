import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class PingServer {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: java PingServer port [seed]");
            System.exit(1);
        }

        final int PORT;
        final int SEED;

        try {
            PORT = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            System.out.println("ERR -arg 1");
            return;
        }
        try {
            SEED = (args.length >= 2) ? Integer.parseInt(args[1]) : 1337;
        } catch(NumberFormatException e) {
            System.out.println("ERR -arg 2");
            return;
        }

        final NetErr netErr = new NetErr(SEED);

        Selector selector;
        try {
            selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(PORT);
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
                            new Thread(new KeyWait(channel, selector, rr, wait)).start();
                        } else {
                            System.out.printf("%s> %s ACTION: not sent\n", rr.socketAddress.toString().substring(1), new String(rr.req.array()));
                        }
                    } else if(key.isWritable()) {
                        DatagramChannel channel = (DatagramChannel) key.channel();
                        ReqResp rr = (ReqResp) key.attachment();
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
