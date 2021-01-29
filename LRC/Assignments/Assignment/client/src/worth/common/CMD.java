package worth.common;

public class CMD {
    /**
     * TCP Commands
     * 
     * Special Format:
     *      FT: From and To statuses, 1 Nibble each
     *          0: TODO
     *          1: INPROGRESS
     *          2: TOBEREVISED
     *          3: DONE
     *      Example:
     *          FT = 0x13 means
     *              From    1   INPROGRESS
     *              To      3   DONE
     * 
     * 
     * Requests:
     * 0x03:            Spacer (s)
     * 0x10 - UsPs:     Login (U)ser with (P)assword
     * 0x11 - s:        Logout
     * 0x12 - s:        List Projects
     * 0x13 - Ps:       Create (P)roject
     * 0x14 - PsUs:     Add (U)ser to (P)roject
     * 0x15 - Ps:       Show members in (P)roject
     * 0x16 - Ps:       Show cards in (P)roject
     * 0x17 - PsCs:     Show (C)ard from (P)roject
     * 0x18 - PsCsDs:   Add (C)ard to (P)roject with (D)escription
     * 0x19 - FTPsCs:   Move (C)ard from (P)roject (F)rom status (T)o status;
     * 0x1A - PsCs:     Get (P)roject's (C)ard history
     * 0x1B - Ps:       Delete (P)roject
     * 0x20 - Ps:       Join (P)roject's chat IP
     * 0x21 - Ps:       Exit (P)roject's chat
     * 
     * Responses:
     * 0xFFMs(Ms(Ms()): OK      (M)essage
     * 0XFEMs:          ERROR   (M)essage
     * 
     */

    public static final byte SPACER = (byte) 0x03;
    public static final byte LOGIN = (byte) 0x10;
    public static final byte LOGOUT = (byte) 0x11;
    public static final byte LIST_PROJECTS = (byte) 0x12;
    public static final byte CREATE_PROJECT = (byte) 0x13;
    public static final byte ADD_MEMBER = (byte) 0x14;
    public static final byte SHOW_MEMBERS = (byte) 0x15;
    public static final byte SHOW_CARDS = (byte) 0x16;
    public static final byte SHOW_CARD = (byte) 0x17;
    public static final byte ADD_CARD = (byte) 0x18;
    public static final byte MOVE_CARD = (byte) 0x19;
    public static final byte GET_CARD_HISTORY = (byte) 0x1A;
    public static final byte DELETE_PROJECT = (byte) 0x1B;
    public static final byte JOIN_CHAT = (byte) 0x20;
    public static final byte EXIT_CHAT = (byte) 0x21;

    public static final byte OK = (byte) 0xFF;
    public static final byte ERROR = (byte) 0xFE;
}
