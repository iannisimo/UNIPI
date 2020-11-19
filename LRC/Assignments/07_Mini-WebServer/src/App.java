import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(6789)) {
            System.out.println("Server Running");
            ExecutorService pool = Executors.newFixedThreadPool(20);
            while(true) {
                pool.execute(new Server(serverSocket.accept()));
            }
        }
    }
}
