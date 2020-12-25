package Worth.Server.TCP;

import java.nio.ByteBuffer;

import Worth.Server.Const;

public class Attachment {
    protected String user;
    protected boolean logged;
    protected ByteBuffer request;
    protected ByteBuffer response;

    public Attachment() {
        this.user = null;
        request = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
        response = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
        logged = false;
    }
}
