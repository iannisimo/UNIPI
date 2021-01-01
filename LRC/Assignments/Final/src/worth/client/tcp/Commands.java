package worth.client.tcp;

import java.nio.ByteBuffer;

import worth.client.Utils;
import worth.common.CMD;
import worth.common.Status;

public class Commands {

    /**
     * Send login command to the server
     * @param username
     * @param password
     * @return {@code Response} from the server
     */
    public static Response login(String username, String password) {
        byte[] u = username.getBytes();
        byte[] h = Utils.hashPass(password).getBytes();
        int size = u.length + h.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.LOGIN);
        buf.put(u);
        buf.put(CMD.SPACER);
        buf.put(h);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response logout() {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.put(CMD.LOGOUT);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response listProjects() {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.put(CMD.LIST_PROJECTS);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response createProject(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.CREATE_PROJECT);
        buf.put(p);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response addMember(String project, String username) {
        byte[] p = project.getBytes();
        byte[] u = username.getBytes();
        int size = p.length + u.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.ADD_MEMBER);
        buf.put(p);
        buf.put(CMD.SPACER);
        buf.put(u);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response showMembers(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.SHOW_MEMBERS);
        buf.put(p);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response showCards(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.SHOW_CARDS);
        buf.put(p);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response showCard(String project, String card) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        int size = p.length + c.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.SHOW_CARD);
        buf.put(p);
        buf.put(CMD.SPACER);
        buf.put(c);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response addCard(String project, String card, String description) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        byte[] d = description.getBytes();
        int size = p.length + c.length + d.length + 4;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.ADD_CARD);
        buf.put(p);
        buf.put(CMD.SPACER);
        buf.put(c);
        buf.put(CMD.SPACER);
        buf.put(d);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response moveCard(String project, String card, Status from, Status to) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        int size = p.length + c.length + 5;
        ByteBuffer buf = ByteBuffer.allocate(size);
        byte ft = (byte) (((byte) from.ordinal() << (byte) 4) | to.ordinal());
        buf.put(CMD.MOVE_CARD);
        buf.put(p);
        buf.put(CMD.SPACER);
        buf.put(c);
        buf.put(CMD.SPACER);
        buf.put(ft);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response getCardHistory(String project, String card) {
        byte[] p = project.getBytes();
        byte[] c = card.getBytes();
        int size = p.length + c.length + 3;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.GET_CARD_HISTORY);
        buf.put(p);
        buf.put(CMD.SPACER);
        buf.put(c);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }

    public static Response deleteProject(String project) {
        byte[] p = project.getBytes();
        int size = p.length + 2;
        ByteBuffer buf = ByteBuffer.allocate(size);
        buf.put(CMD.DELETE_PROJECT);
        buf.put(p);
        buf.put(CMD.SPACER);
        return Connection.send(buf);
    }
}

