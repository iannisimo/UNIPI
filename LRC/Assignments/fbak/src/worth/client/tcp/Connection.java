package worth.client.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import Worth.Client.Const;

public class Connection {
    private static SocketChannel channel;
    private static boolean connected = false;


    /**
     * 
     * @return {true, false} -> {connected, server_unreachable}
     */
    public static boolean connect() {
        try {
            channel = SocketChannel.open(new InetSocketAddress(Const.IP, Const.TCP_PORT));
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
        connected = true;
        return true;
    }

    public static boolean send(ByteBuffer buf) {
        buf.flip();
        try {
            channel.write(buf);
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            connected = false;
            return false;
        }
        return true;
    }

    public static boolean isConnected() {
        return connected;
    }
}
