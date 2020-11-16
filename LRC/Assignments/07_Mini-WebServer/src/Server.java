import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable{
    private Socket socket;
    
    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Scanner scanner;
        try {
            scanner = new Scanner(socket.getInputStream());
            ReqHTTP request = new ReqHTTP(scanner);
            Packet packet = new Packet(request.getFilename());
            packet.sendPacket(socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            System.err.println("Can't open socket");
            e.printStackTrace();
        }
    }
}
