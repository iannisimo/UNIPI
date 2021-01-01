package Worth.Server;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import Worth.Common.Status;
import Worth.Server.Projects.Project;
import Worth.Server.Projects.Projects;
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
        Projects.get("project").moveCard("card1", Status.INPROGRESS, Status.TOBEREVISED);
        // Project p = new Project("a", "admin");
        // p.addCard("card0", "description");
        // p.moveCard("card0", Status.TODO, Status.INPROGRESS);
        // p.moveCard("card0", Status.INPROGRESS, Status.DONE);
    }
}
