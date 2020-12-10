import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Server {
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException e) {
            port = Const.DEFAULT_PORT;
        }
        System.out.printf("Listening for connections on port %d\n", port);
        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while(true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                boolean closeConn = false;
                try {
                    if(key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.printf("Accepted connection from %s\n", client);
                        client.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(Const.BUF_SIZE);
                        client.register(selector, SelectionKey.OP_READ).attach(buffer);
                    } else if(key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        buffer.put(Const.CONCAT.getBytes());
                        client.read(buffer);
                        if(new String(buffer.array()).contains("exit\0")) {
                            key.cancel();
                            key.channel().close();
                            System.out.println("Closing connection on client's request...");
                            break;
                        }
                        System.out.printf("Got new message from client.\n");
                        buffer.flip();
                        client.register(selector, SelectionKey.OP_WRITE).attach(buffer);
                    } else if(key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        System.out.printf("Sending back response...\n\t<%s>\n", new String(buffer.array()));
                        client.write(buffer);
                        buffer = ByteBuffer.allocate(Const.BUF_SIZE);
                        client.register(selector, SelectionKey.OP_READ).attach(buffer);
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
