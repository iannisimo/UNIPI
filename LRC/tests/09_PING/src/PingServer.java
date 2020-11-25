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

        final int port = Integer.parseInt(args[0]);
        final int seed = (args.length >= 2) ? Integer.parseInt(args[1]) : 1337;
        final NetErr netErr = new NetErr(seed);

        Selector selector;
        Selector selector2;
        try {
            selector = Selector.open();
            selector2 = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(port);
            channel.socket().bind(socketAddress);
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ).attach(new ReqResp());
            channel.register(selector2, SelectionKey.OP_READ, new ReqResp());
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
                        System.out.println(new String(rr.req.array()));
                    }
                } catch(IOException e) {
                    key.cancel();
                    e.printStackTrace();
                }
            }
        }
    }
}
