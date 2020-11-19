import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class Packet {

    private byte[] body;
    private String header;

    private String type;
    private String status;

    public Packet(String filename) throws FileNotFoundException, IOException {
        System.out.println("Generating packet for \"" + filename + "\"");
        File file = new File("res/" + filename);
        status = "200";
        if(!file.isFile()) {
            // Redirecting to 404
            System.out.println("Requested file not present on the server, redirecting on 404 page");
            filename = Const._404;
            status = "404";
        }
        body = buildBody(filename);
        String ext = getExtension(filename);
        if(!Const.EXT_MAP.containsKey(ext)) {
            type = Const.EXT_MAP.get("");
        } else {
            type = Const.EXT_MAP.get(ext);
        }
        header = buildHeader(type, status, body.length);
    }

    public void sendPacket(OutputStream outputStream) throws IOException {
        System.out.println("Sending packet...");
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes(header);
        dataOutputStream.write(body);
        dataOutputStream.flush();
        dataOutputStream.close();
        System.out.println("Packet sent");
    }


    private String buildHeader(String fileType, String status, int len) {
        String headerTmp = String.format(Const.HEADER_FORMAT, status, Const.STATUS_MAP.get(status), fileType, len);
        return headerTmp;
    }

    private static byte[] buildBody(String filename) throws IOException {
        File file = new File("res/" + filename);
        return Files.readAllBytes(file.toPath());
    }

    private static String getExtension(String filename) {
        if(!filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
