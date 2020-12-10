import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class EchoClient {
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

        try (
            SocketChannel client = SocketChannel.open(new InetSocketAddress(address, port));
            Scanner stdin = new Scanner(System.in)
            ) {
            while(true) {
                String userInput = stdin.nextLine();
                if(userInput.equals("")) continue;
                ByteBuffer writeBuf = ByteBuffer.allocate(Const.BUF_SIZE);
                ByteBuffer readBuf;
                writeBuf.put(userInput.getBytes());
                writeBuf.flip();
                client.write(writeBuf);
                /*
                 * The next few lines are needed to read messages that cannot be stored in the buffer at once.
                 */
                StringBuilder sb = new StringBuilder();
                do {
                    readBuf = ByteBuffer.allocate(Const.BUF_SIZE);
                    client.read(readBuf);
                    sb.append(new String(readBuf.array()));
                } while(readBuf.array()[readBuf.array().length-1] != '\0');
                System.out.println(sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
