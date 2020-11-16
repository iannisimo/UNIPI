import java.util.HashMap;

public class Const {
    public static final String STATUS_LINE = "HTTP/1.1 200 OK\r\n";
    public static final HashMap<String, String> EXT_MAP = new HashMap<>()
    {
        private static final long serialVersionUID = 1L;
        {
        put("txt", "text/plain");
        put("jpg", "image/jpeg");
        put("jpeg", "image/jpeg");
        put("png", "image/png");
        put("gif", "image/gif");
        put("html", "text/html");
        put("", "application/octet-stream");
    }};
    public static final String _404 = "404.html";

}
