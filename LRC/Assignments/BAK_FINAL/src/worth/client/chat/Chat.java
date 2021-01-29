package worth.client.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import worth.client.utils.Const;
import worth.client.tcp.Commands;

public class Chat implements Runnable {

    private String project;
    private InetAddress ip;
    private int port;
    private boolean open = false;
    private DatagramPacket sendPacket;
    private ObservableList<Text> messages;

    public Chat(String project) {
        this.project = project;
        try {
            String IPPort = Commands.joinChat(project).getMessage();
            String[] split = IPPort.split(":");
            this.ip = InetAddress.getByName(split[0]);
            if(!this.ip.isMulticastAddress()) throw new UnknownHostException();
            this.port = Integer.valueOf(split[1]);
        } catch (UnknownHostException e) {
            if(Const.DEBUG) e.printStackTrace();
        }
        open = true;
        messages = FXCollections.observableArrayList();
        new Thread(this).start();
    }

    public ObservableList<Text> getMessages() {
        return this.messages;
    }

    public void close() {
        open = false;
        Commands.exitChat(project);
    }

    public void send(String username, String message) {
        String m = username + ": " + message;
        byte[] buf = m.getBytes();
        sendPacket = new DatagramPacket(buf, buf.length, ip, port);
    }

    @Override
    public void run() {
        try (MulticastSocket socket = new MulticastSocket(port)){
            socket.joinGroup(ip);
            socket.setSoTimeout(250);
            byte[] buf = new byte[Const.CHAT_BUF_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            while(open) {
                if(sendPacket != null) {
                    socket.send(sendPacket);
                    sendPacket = null;
                }
                try {
                    socket.receive(receivePacket);
                    messages.add(new Text(new String(receivePacket.getData(), 0, receivePacket.getLength())));
                } catch (SocketTimeoutException e) {}
            }
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            open = false;
        }
    }
    
}
