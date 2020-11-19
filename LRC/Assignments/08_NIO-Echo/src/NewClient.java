import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.io.InputStreamReader;

public class NewClient {
    public static void main(String[] args) {
        String address;
        int port;
        try {
            address = args[0];
        } catch (Exception e) {
            address = Const.DEBUG_ADDRESS;
        }
        try {
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            port = Const.DEFAULT_PORT;
        }
        // Scanner stdin = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SocketChannel client;
        Selector selector;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(Const.BUF_SIZE);
            client = SocketChannel.open(new InetSocketAddress(address, port));
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_WRITE).attach(buffer);
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
                    if(key.isWritable()) {
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if(!reader.ready()) break;
                        String userInput = reader.readLine();
                        buffer.put(userInput.getBytes());
                        buffer.flip();
                        client.write(buffer);
                        client.register(selector, SelectionKey.OP_READ);
                        Thread.sleep(100);
                    } else if(key.isReadable()) {
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        client.read(buffer);
                        System.out.println(new String(buffer.array()));
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
