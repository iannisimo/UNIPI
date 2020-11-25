import java.net.BindException;
import java.net.DatagramSocket;

public class App {

    public static final int MIN_PORTS = 1024;
    public static final int MAX_PORTS = 10000;

    public static void main(String[] args) throws Exception {
        for(int i = MIN_PORTS; i < MAX_PORTS; i++) {
            try {
                DatagramSocket socket = new DatagramSocket(i);     
            } catch (BindException e) {
                System.out.printf("Port %d:\t O\n", i);       
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
