package worth.server;

import java.io.IOException;

import worth.server.Utils.Const;
import worth.server.Utils.Utils;
import worth.server.projects.Projects;
import worth.server.tcp.Connection;
import worth.server.users.Users;

public class mainServer {
    public static void main(String[] args) {
        // Initialization sequence
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
        // Utils.registerRegisterService();
        // Firing thread for TCP connections worker
        new Thread(new Connection()).start();
    }
}
