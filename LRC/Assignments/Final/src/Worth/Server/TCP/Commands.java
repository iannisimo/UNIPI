package Worth.Server.TCP;

import java.nio.channels.SelectionKey;
import Worth.Server.Users.Users;


public class Commands {

    protected static final byte LOGIN = (byte) 0x10;
    protected static final byte ERR = (byte) 0xFE;
    protected static final byte OK = (byte) 0xFF;

    public static void recognize(Attachment a, SelectionKey k) {
        a.request.rewind();
        byte cmd = a.request.get();
        switch (cmd) {
            case LOGIN:
                byte ul = a.request.get();
                byte pl = a.request.get();
                byte[] u = new byte[ul];
                byte[] p = new byte[pl];
                a.request.get(u, 0, ul);
                a.request.get(p, 0, pl);
                String username = new String(u);
                String password = new String(p);
                if(Users.matchPassword(username, password)) {
                    a.user = username;
                    a.logged = true;
                    a.response.put(OK);
                } else {
                    a.response.put(ERR);
                }
                a.response.flip();
        }
    }
}
