package Server;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class ReqResp {
    public ByteBuffer req = ByteBuffer.allocate(Const.BUF_SZ);
    public ByteBuffer resp;
    public SocketAddress socketAddress;
}
