package Worth.Client.TCP;

import java.nio.ByteBuffer;

public class Commands {

    /**
     * 0x00:            Spacer
     * 0x01 -> 0x0F:    Reserved
     * 0x10:            Login
     *   Format:      0x10xxyyuuuupppp
     *   xx: lenght of username
     *   yy: lenght of password
     * 
     */

    protected static final byte LOGIN = (byte) 0x10;
    protected static final byte OK = (byte) 0xFF;


    public static boolean login(String username, String password) {
        byte[] u = username.getBytes();
        byte[] p = password.getBytes();
        int size = u.length + p.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(LOGIN);
        buf.put((byte) u.length);
        buf.put((byte) p.length);
        buf.put(u);
        buf.put(p);
        return Connection.send(buf);
    }
}

