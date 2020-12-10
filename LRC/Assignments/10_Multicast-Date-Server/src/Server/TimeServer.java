package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Date;


public class TimeServer {
    public static void main(String[] args) {

        InetAddress addr;
        int port;

        try {
            addr = InetAddress.getByName(args[0]);
            if (!addr.isMulticastAddress())
                throw new IOException();
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.err.println("Usage: TimeServer <Multicast Address> <Port>");
            return;
        }

        try (DatagramSocket socket = new DatagramSocket()){
            while(true) {
                Long timeStamp = new Date().getTime();
                byte[] data = ByteBuffer.allocate(Long.BYTES).putLong(timeStamp).array();
                DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
                socket.send(packet);
                System.out.printf("Sent new message: %d\n", timeStamp);
                Thread.sleep(Const.INTERVAL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
