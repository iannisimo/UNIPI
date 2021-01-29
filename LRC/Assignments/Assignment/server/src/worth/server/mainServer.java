package worth.server;

import java.io.IOException;

import worth.server.projects.Projects;
import worth.server.tcp.Connection;
import worth.server.users.Users;
import worth.server.utils.Const;
import worth.server.utils.Utils;

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
        System.out.println("Initialization sequence complete");
        new Connection().run();
    }
}
