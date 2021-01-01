package worth.client.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import worth.client.Const;
import worth.common.CMD;

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

    public static Response send(ByteBuffer buf) {
        buf.flip();
        try {
            channel.write(buf);
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            connected = false;
            return new Response(false);
        }
        return readResponse();
    }

    public static boolean isConnected() {
        return connected;
    }

    private static int findSeparator(ByteBuffer bb) {
        if(!bb.hasRemaining()) return 0;
        int i = 0;
        bb.mark();
        while(bb.get() != CMD.SPACER) i++;
        bb.reset();
        return i;
    }

    private static Response readResponse() {
        List<String> messages = new ArrayList<>();
        boolean status = false;
        ByteBuffer bb = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
        try {
            channel.read(bb);
            bb.flip();
            byte code = bb.get();
            status = code == CMD.OK;
            int len;
            while((len = findSeparator(bb)) != 0) {
                byte[] ba = new byte[len];
                bb.get(ba, 0, len);
                messages.add(new String(ba));
                // Removing separator from buffer
                bb.get();
            }
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            System.err.println("Error while reading response, exiting");
            System.exit(1);
        }
        return new Response(status, messages);
    }
}
