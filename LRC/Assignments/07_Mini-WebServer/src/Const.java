import java.util.HashMap;

public class Const {
    public static final String HEADER_FORMAT = "HTTP/1.1 %s %s\r\nContent-Type: %s\r\nContent-Length: %s\r\n\r\n";
    public static final HashMap<String, String> EXT_MAP = new HashMap<>() {
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
    public static final HashMap<String, String> STATUS_MAP = new HashMap<>() {
        private static final long serialVersionUID = 1L;
        {
            put("200", "OK");
            put("404", "Not Found");
    }};
}
