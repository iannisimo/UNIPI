package Worth.Server;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import Worth.Server.Projects.Projects;
import Worth.Server.Projects.Status;
import Worth.Server.TCP.Connection;

public class mainServer {
    public static void main(String[] args) {
        Utils.setupEnviroment();
        Utils.parseArgs(args);
        Utils.registerRegisterService();
        try {
            Projects.init();
        } catch (IOException | ParseException e) {
            if(Const.DEBUG) e.printStackTrace();
            return;
        }
        new Thread(new Connection()).start();
        Projects.get("test").moveCard("a", Status.TODO, Status.INPROGRESS);
    }
}
