import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws Exception {
        // ServerSocket serverSocket = new ServerSocket(6789);

        try (ServerSocket serverSocket = new ServerSocket(6789)) {
            System.out.println("Server Running");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while(true) {
                pool.execute(new Server(serverSocket.accept()));
            }
        }

        // while(true) {
        //     Socket socket = serverSocket.accept();
        //     Scanner scanner = new Scanner(socket.getInputStream());
        //     ReqHTTP request = new ReqHTTP(scanner);
        //     Packet packet = new Packet(request.getFilename());
        //     packet.sendPacket(socket.getOutputStream());
        //     socket.close();
        // }
    }
}
