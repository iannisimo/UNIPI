package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

public class TimeClient {
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

        try (MulticastSocket socket = new MulticastSocket(port)) {
            socket.joinGroup(addr);
            DatagramPacket packet = new DatagramPacket(new byte[Long.BYTES], Long.BYTES);
            ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
            for(int i = 0; i < Const.N_ITER; i++) {
                socket.receive(packet);
                byteBuffer.put(packet.getData());
                byteBuffer.flip();
                Long timestamp = byteBuffer.getLong();
                System.out.printf("Received new date-time:\nRaw:\t%d\nDate:\t%s\nTime:\t%s\n", timestamp, timestampToDate(timestamp), timestampToTime(timestamp));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String timestampToDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timestamp));
        return String.format("%d/%d/%d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
    }

    private static String timestampToTime(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timestamp));
        return String.format("%d:%d:%d:%d", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND));
    }

}
