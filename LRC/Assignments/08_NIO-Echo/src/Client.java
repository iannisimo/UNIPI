import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class Client {

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

        Scanner stdin = new Scanner(System.in);

        try {
            SocketChannel client = SocketChannel.open(new InetSocketAddress(address, port));
            client.configureBlocking(true);
            while(true) {
                while(!stdin.hasNextLine());
                String userInput = stdin.nextLine();
                if(userInput.equals("exit")) break;
                if(userInput.length() > Const.MAX_STRING_LEN) userInput = userInput.substring(0, Const.MAX_STRING_LEN-1);
                ByteBuffer outputBuffer = ByteBuffer.allocate(Const.MAX_STRING_LEN);
                ByteBuffer inputBuffer = ByteBuffer.allocate(Const.MAX_STRING_LEN + Const.CONCAT.length()*2);
                outputBuffer.put(userInput.getBytes());
                outputBuffer.flip();
                client.write(outputBuffer);
                client.read(inputBuffer);
                System.out.println(new String(inputBuffer.array()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
