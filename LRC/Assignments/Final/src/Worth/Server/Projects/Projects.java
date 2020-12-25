package Worth.Server.Projects;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Worth.Server.Const;
import Worth.Server.Utils;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;

public class Projects {
    private static Map<String, Project> projects;

    @SuppressWarnings({"unchecked"})
    public static void init() throws IOException, ParseException {
        projects = new HashMap<>();
        File projectsDir = new File(Const.PROJECTS_FOLDER);
        for (File project : projectsDir.listFiles(File::isDirectory)) {
            String projectName = project.getName();
            String jsonProj = Utils.readFile(Const.PROJECT_INFO(projectName));
            JSONParser pParser = new JSONParser();
            Map<String, Object> pMap = (Map<String, Object>) pParser.parse(jsonProj);

            List<String> members = (List<String>) pMap.get("members");
            Project proj = new Project(projectName, members.remove(0));
            for(String member : members) {
                proj.addMember(member);
            }

            List<String> cards = (List<String>) pMap.get("cards");
            for(String cardName : cards) {
                String jsonCard = Utils.readFile(Const.PROJECT_CARD(projectName, cardName));
                JSONParser cParser = new JSONParser();
                Map<String, Object> cMap = (Map<String, Object>) cParser.parse(jsonCard);
                proj.addCard(cardName, (String) cMap.get("description"));
            }

            projects.put(projectName, proj);
        }
    }

    public static Project get(String project) {
        return projects.get(project);
    }
}
