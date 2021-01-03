package worth.client.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import worth.client.Const;
import worth.client.tcp.Commands;

public class Chat implements Runnable {

    private String project;
    private Callback cb;
    private InetAddress ip;
    private int port;
    private boolean open = false;
    private DatagramPacket sendPacket;

    public Chat(String project, Callback cb) {
        this.project = project;
        this.cb = cb;
        try {
            String IPPort = Commands.joinChat(project).getMessage();
            String[] split = IPPort.split(":");
            this.ip = InetAddress.getByName(split[0]);
            if(!this.ip.isMulticastAddress()) throw new UnknownHostException();
            this.port = Integer.valueOf(split[1]);
        } catch (UnknownHostException e) {
            if(Const.DEBUG) e.printStackTrace();
            cb.chatUnreachable();
        }
        open = true;
    }

    public void close() {
        open = false;
        Commands.exitChat(project);
    }

    public void send(String message) {
        byte[] buf = message.getBytes();
        sendPacket = new DatagramPacket(buf, buf.length, ip, port);
    }

    @Override
    public void run() {
        try (MulticastSocket socket = new MulticastSocket(port)){
            socket.joinGroup(ip);
            socket.setSoTimeout(1000);
            byte[] buf = new byte[Const.CHAT_BUF_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            while(open) {
                if(sendPacket != null) {
                    socket.send(sendPacket);
                    sendPacket = null;
                }
                try {
                    socket.receive(receivePacket);
                    cb.newMessage(new String(receivePacket.getData(), 0, receivePacket.getLength()));
                } catch (SocketTimeoutException e) {}
            }
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            open = false;
            cb.chatUnreachable();
        }
    }
    
}
