package Worth.Server.TCP;

import java.nio.channels.SelectionKey;
import Worth.Server.Users.Users;


public class Commands {

    protected static final byte LOGIN = (byte) 0x10;
    protected static final byte LOGOUT = (byte) 0x11;
    protected static final byte LIST_PROJECTS = (byte) 0x12;
    protected static final byte CREATE_PROJECT = (byte) 0x13;
    protected static final byte ADD_MEMBER = (byte) 0x14;
    protected static final byte SHOW_MEMBERS = (byte) 0x15;
    protected static final byte SHOW_CARDS = (byte) 0x16;
    protected static final byte SHOW_CARD = (byte) 0x17;
    protected static final byte ADD_CARD = (byte) 0x18;
    protected static final byte MOVE_CARD = (byte) 0x19;
    protected static final byte GET_CARD_HISTORY = (byte) 0x1A;
    protected static final byte READ_CHAT = (byte) 0x1B;
    protected static final byte WRITE_CHAT = (byte) 0x1C;
    protected static final byte DELETE_PROJECT = (byte) 0x1D;

    protected static final byte OK = (byte) 0xFF;
    protected static final byte ERROR = (byte) 0xFE;
    protected static final byte STREAM = (byte) 0xFD;


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
                    // a.response.put(OK);
                } else {
                    // a.response.put(ERROR);
                }
                // a.response.flip();
                break;
            case LOGOUT:
                if(a.logged) {
                    // a.response.put(OK);
                    a.logged = false;
                } else {
                    // a.response.put(ERROR);
                }
                // a.response.flip();
                break;
            case LIST_PROJECTS:
                if(!a.logged) {
                    // a.response.put(ERROR);
                } else {

                }
        }
    }
}
