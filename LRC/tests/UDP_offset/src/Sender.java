import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;

/*
 * Receive with <nc -ul PORT>
 */

public class Sender {

    private static final File LIPSUM = new File("LIpsum.txt");
    private static final int PORT = 8080;
    private static final String ADDR = "127.0.0.1";
    
    public static void main(String[] args) {
        DatagramSocket socket; 
        try {
            socket = new DatagramSocket();
            byte[] lipsum = Files.readAllBytes(LIPSUM.toPath());
            // sendFirstPacket(socket, lipsum);
            sendMaxPacketSize(socket, lipsum);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static void sendMaxPacketSize(DatagramSocket socket, byte[] arr) throws IOException {
        DatagramPacket packet = new DatagramPacket(arr, 0, 512, InetAddress.getByName(ADDR), PORT);
        int bSent = 0;
        do {
            socket.send(packet);
            bSent += packet.getLength();
            int bToSend = arr.length - bSent;
            int size = Math.min(bToSend, 512);
            packet.setData(arr, bSent, size);
        } while(bSent < arr.length);
    }
}