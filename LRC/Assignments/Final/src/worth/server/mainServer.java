package worth.server;

import java.io.IOException;

import worth.server.projects.Projects;
import worth.server.tcp.Connection;
import worth.server.users.Users;

public class mainServer {
    public static void main(String[] args) {
        Utils.setupEnviroment();
        Utils.parseArgs(args);
        try {
            Users.restore();
            Projects.restore();
            Users.initCallbackService();
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            System.err.println("Unrecoverable error while restoring the status of the program, exiting...");
            System.exit(1);
        }
        Utils.registerRegisterService();
        new Thread(new Connection()).start();
    }
}
