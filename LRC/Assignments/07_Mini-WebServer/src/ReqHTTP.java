import java.util.Scanner;

public class ReqHTTP {
    private String filename;
    private String host;

    public ReqHTTP(Scanner scanner) {
        String requestLine = scanner.nextLine();
        String hostLine = scanner.nextLine();
        filename = requestLine.split(" ")[1].substring(1);
        host = hostLine.split(" ")[1];
    }

    public String getFilename() {
        return filename;
    }

    public String getHost() {
        return host;
    }
}
