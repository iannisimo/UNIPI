import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class EchoServer {
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = Const.DEFAULT_PORT;
        }

        Selector selector;
        try {
            selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.printf("Listening for connections on port <%d>\n", port);
        while(true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if(key.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ, new ReqResp());
                    } else if(key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ReqResp rr = (ReqResp) key.attachment();
                        channel.read(rr.req);
                        System.out.printf("%s> %s\n", channel.getRemoteAddress().toString().substring(1), new String(rr.req.array()));
                        channel.register(selector, SelectionKey.OP_WRITE, rr);
                    } else if(key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ReqResp rr = (ReqResp) key.attachment();
                        System.out.print("\tResponding\n");
                        channel.write(rr.prepareResponse());
                        channel.register(selector, SelectionKey.OP_READ, new ReqResp());
                    }
                } catch (IOException e) {
                    key.cancel();
                    e.printStackTrace();
                    try {
                        key.channel().close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        }
    }
}
