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
        boolean exit = false;
        while(!exit) {
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
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        client.read(buffer);
                        System.out.println(new String(buffer.array()));
                        buffer.clear();
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ).attach(buffer);
                    } else if(key.isWritable()) {
                        if(!reader.ready()) break;
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        String userInput = reader.readLine();
                        if(userInput.equals("")) break;
                        if(userInput.equals("exit")) exit = true;
                        buffer.put(userInput.getBytes());
                        buffer.flip();
                        client.write(buffer);
                        buffer = ByteBuffer.allocate(Const.BUF_SIZE);
                        if(exit) {
                            key.cancel();
                            client.close();
                            break;
                        }
                        client.register(selector, SelectionKey.OP_READ).attach(buffer);
                    } 
                } catch(IOException e) {
                    key.cancel();
                    e.printStackTrace();
                    try {
                        client.close();
                    } catch(IOException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        }
    }
}
