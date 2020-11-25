public class PingClient {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: java PingClient hostname port");
            System.exit(1);
        }
    }
}
