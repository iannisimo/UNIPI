import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;

public class PingClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java PingClient hostname port");
            System.exit(1);
        }

        final String HOSTNAME = args[0];
        final int PORT;

        try {
            PORT = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("ERR -arg 2");
            return;
        }

        String sendFormat = "PING %d %d";
        String receiveFormat = "%s RTT: %s ms\n";
        try (DatagramSocket socket = new DatagramSocket()){
            socket.setSoTimeout(2000);
            DatagramPacket receivePacket = new DatagramPacket(new byte[100], 100);
            for(int i = 0; i < Const.REQNO; i++) {
                long sentTimestamp = new Date().getTime();
                String data = String.format(sendFormat, i, sentTimestamp);
                DatagramPacket packet = new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getByName(HOSTNAME), PORT);
                socket.send(packet);
                String rtt;
                try {
                    socket.receive(receivePacket);
                    rtt = "" + (new Date().getTime() - sentTimestamp);
                } catch(SocketTimeoutException e) {
                    rtt = "*";
                }
                System.out.printf(receiveFormat, data, rtt);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
