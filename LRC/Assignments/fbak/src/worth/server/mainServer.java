package worth.server;



public class mainServer {
    public static void main(String[] args) {
        Utils.setupEnviroment();
        Utils.parseArgs(args);
        Utils.registerRegisterService();
    }
}
