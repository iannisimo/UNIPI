import java.nio.ByteBuffer;

public class ReqResp {
    ByteBuffer req = ByteBuffer.allocate(Const.BUF_SIZE);
    private ByteBuffer resp = ByteBuffer.allocate(Const.BUF_SIZE + Const.RESP_HEADER.length);

    public ByteBuffer prepareResponse() {
        resp.put(Const.RESP_HEADER);
        req.flip();
        resp.put(req);
        resp.flip();
        return resp;
    }
}
