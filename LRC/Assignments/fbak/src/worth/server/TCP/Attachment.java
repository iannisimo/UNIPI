package Worth.Server.TCP;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import Worth.Server.Const;

public class Attachment {
    protected String user;
    protected boolean logged;
    protected boolean error;
    protected ByteBuffer request;
    protected List<String> queue;

    public Attachment() {
        this.user = null;
        request = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
        logged = false;
        error = false;
        queue = new ArrayList<>();
    }

    protected ByteBuffer getResponse() throws NullPointerException {
        if(queue.isEmpty()) throw new NullPointerException("Empty queue");
        ByteBuffer buf = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
        byte[] resp = queue.remove(0).getBytes();
        if(queue.isEmpty()) {
            buf.put(Commands.OK);
        } else {
            buf.put(Commands.STREAM);
        }
        buf.put((byte) resp.length);
        buf.put(resp);
        buf.flip();
        return buf;
    }
}
