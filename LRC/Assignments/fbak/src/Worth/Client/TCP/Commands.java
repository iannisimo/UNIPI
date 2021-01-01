package Worth.Client.TCP;

import java.nio.ByteBuffer;

import Worth.Common.Status;

public class Commands {

    /**
     * Format:
     * lowercase letter(s): size
     * UPPERCASE letter(s): parameter
     * e.g. xxUyyP:
     *      xx: First  parameter size
     *      U:  First  parameter
     *      yy: Second parameter size
     *      P:  Second parameter
     * Special:
     *      FT: From and To statuses, 1 Nibble each
     *          0: TODO
     *          1: INPROGRESS
     *          2: TOBEREVISED
     *          3: DONE
     * 
     * 
     * Requests:
     * 0x10xxUyyP:      Login (U)ser with (P)assword
     * 0x11:            Logout
     * 0x12:            List Projects
     * 0x13xxP:         Create (P)roject
     * 0x14xxPyyU:      Add (U)ser to (P)roject
     * 0x15xxP:         Show members in (P)roject
     * 0x16xxP:         Show cards in (P)roject
     * 0x17xxPyyC       Show (C)ard from (P)roject
     * 0x18xxPyyCzzD:   Add (C)ard to (P)roject with (D)escription
     * 0x19xxPyyCFT:    Move (C)ard from (P)roject (F)rom status (T)o status;
     * 0x1AxxPyyC:      Get (P)roject's (C)ard history
     * 0x1BxxP:         Read (P)roject's chat
     * 0x1CxxPyyM:      Send (M)essage in (P)roject's chat
     * 0x1DxxP:         Delete (P)roject
     * 
     * Responses:
     * 0xFFxxM:         OK      (M)essage
     * 0XFExxM:         ERROR   (M)essage
     * 0xFDxxM:         STREAM  (M)essasges; ending with OK
     * 
     */

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


    public static boolean login(String username, String password) {
        byte[] u = username.getBytes();
        byte[] p = password.getBytes();
        int size = u.length + p.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(LOGIN);
        buf.put((byte) u.length);
        buf.put(u);
        buf.put((byte) p.length);
        buf.put(p);
        return Connection.send(buf);
    }

    public static boolean logout() {
        ByteBuffer buf = ByteBuffer.allocate(1);
        buf.put(LOGOUT);
        return Connection.send(buf);
    }

    public static boolean listProjects() {
        ByteBuffer buf = ByteBuffer.allocate(1);
        buf.put(LIST_PROJECTS);
        return Connection.send(buf);
    }

    public static boolean createProject(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CREATE_PROJECT);
        buf.put((byte) p.length);
        buf.put(p);
        return Connection.send(buf);
    }

    public static boolean addMember(String project, String username) {
        byte[] p = project.getBytes();
        byte[] u = username.getBytes();
        int size = p.length + u.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(ADD_MEMBER);
        buf.put((byte) p.length);
        buf.put(p);
        buf.put((byte) u.length);
        buf.put(u);
        return Connection.send(buf);
    }

    public static boolean showMembers(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(SHOW_MEMBERS);
        buf.put((byte) p.length);
        buf.put(p);
        return Connection.send(buf);
    }

    public static boolean showCards(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(SHOW_CARDS);
        buf.put((byte) p.length);
        buf.put(p);
        return Connection.send(buf);
    }

    public static boolean showCard(String project, String card) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        int size = p.length + c.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(SHOW_CARD);
        buf.put((byte) p.length);
        buf.put(p);
        buf.put((byte) c.length);
        buf.put(c);
        return Connection.send(buf);
    }

    public static boolean addCard(String project, String card, String description) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        byte[] d = description.getBytes();
        int size = p.length + c.length + d.length + 4;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(ADD_CARD);
        buf.put((byte) p.length);
        buf.put(p);
        buf.put((byte) c.length);
        buf.put(c);
        buf.put((byte) d.length);
        buf.put(d);
        return Connection.send(buf);
    }

    public static boolean moveCard(String project, String card, Status from, Status to) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        int size = p.length + c.length + 4;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(ADD_MEMBER);
        buf.put((byte) p.length);
        buf.put(p);
        buf.put((byte) c.length);
        buf.put(c);
        byte ft = (byte) (((byte) from.ordinal() << (byte) 4) | to.ordinal());
        buf.put(ft);
        return Connection.send(buf);
    }
}

