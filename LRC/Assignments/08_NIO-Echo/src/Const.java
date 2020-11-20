import javax.print.DocFlavor.STRING;

public class Const {
    public static final int DEFAULT_PORT = 2020;
    public static final String DEBUG_ADDRESS = "127.0.0.1";
    public static final int MAX_STRING_LEN = 256;
    public static final String CONCAT = "Echoed: ";
    public static final int BUF_SIZE = (MAX_STRING_LEN + CONCAT.length()) * 2;
}
