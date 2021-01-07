package worth.server.projects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import worth.common.Status;
import worth.server.Utils.Const;
import worth.server.Utils.Utils;

public class Projects {
    private static Map<String, Project> projects = new ConcurrentHashMap<>();

    public static boolean isMember(String project, String member) {
        if(!exists(project)) return false;
        return projects.get(project).isMember(member);
    }

    public static boolean deleteProject(String project) {
        if(!projects.containsKey(project)) return false;
        if(!projects.get(project).allDone()) return false;
        try {
            Files.walk(Path.of(Const.PROJECT_FOLDER(project))).sorted(Comparator.reverseOrder()).map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
        projects.remove(project);
        return true;
    }

    public static boolean addProject(String project, String member) {
        if(!project.matches(Const.PNAME_REGEX)) return false;
        if(projects.containsKey(project)) return false;
        projects.put(project, new Project(member));
        store(project);
        return true;
    }

    public static List<Status> getCardHistory(String project, String card) {
        if(!exists(project, card)) return null;
        return projects.get(project).get(card).getHistory();
    }

    public static List<String> getCardHistoryString(String project, String card) {
        if(!exists(project, card)) return null;
        return projects.get(project).get(card).getHistoryString();
    }

    public static List<String> getCardInfo(String project, String card) {
        if(!exists(project, card)) return null;
        List<String> resp = new ArrayList<>();
        resp.add(card);
        resp.add(projects.get(project).get(card).getDescription());
        resp.add(projects.get(project).getCardStatus(card).toString());
        return resp;
    }

    public static List<String> getCards(String project) {
        if(!exists(project)) return null;
        return projects.get(project).getCards();
    }

    public static List<String> getMembers(String project) {
        if(!exists(project)) return null;
        return projects.get(project).getMembers();
    }

    public static boolean exists(String project, String card) {
        if(!exists(project)) return false;
        return projects.get(project).exists(card);
    }

    public static boolean exists(String project) {
        return projects.containsKey(project);
    }

    public static List<String> findProjects(String member) {
        List<String> ret = new ArrayList<>();
        projects.forEach((k, v) -> {
            if(v.getMembers().contains(member)) ret.add(k);
        });
        return ret;
    }

    public static boolean addMember(String project, String member) {
        if(!projects.containsKey(project)) return false;
        boolean ret = projects.get(project).addMember(member);
        store(project);
        return ret;
    }

    public static boolean addCard(String project, String card, String description) {
        if(!exists(project)) return false;
        if(!projects.get(project).addCard(card, description)) return false;
        store(project);
        store(project, card);
        return true;
    }

    public static boolean moveCard(String project, String card, Status from, Status to) {
        if(!projects.containsKey(project)) return false;
        if(!projects.get(project).moveCard(card, from, to)) return false;
        store(project, card);
        return true;
    }

    public static String joinChat(String project, String member) {
        if(!projects.containsKey(project)) return null;
        if(!projects.get(project).isMember(member)) return null;
        return projects.get(project).joinChat(member);
    }

    public static boolean exitChat(String project, String member) {
        if(!exists(project)) return false;
        return projects.get(project).exitChat(member);
    }

    // TOFIX: find chat for chat member
    public static void exitAllChats(String member) {
        for(String project : findProjects(member)) {
            projects.get(project).exitChat(member);
        }
    }

    @SuppressWarnings("all")
    private synchronized static boolean store(String project) {
        if(!projects.containsKey(project)) return false;

        JSONArray members = new JSONArray();
        members.addAll(projects.get(project).getMembers());
        JSONArray cards = new JSONArray();
        cards.addAll(projects.get(project).getCards());

        Map<String, Object> json = new HashMap<>();
        json.put("project", project);
        json.put("members", members);
        json.put("cards", cards);
        JSONObject obj = new JSONObject(json);
        File pDir = new File(Const.PROJECT_FOLDER(project));
        File pInfo = new File(Const.PROJECT_INFO(project));
        try {
            pDir.mkdir();
            pInfo.createNewFile();
            FileOutputStream fos = new FileOutputStream(pInfo);
            fos.write(obj.toJSONString().getBytes());
            fos.close();
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
        return true;
    }

    private synchronized static boolean store(String project, String card) {
        if(!projects.containsKey(project)) return false;
        if(!projects.get(project).exists(card)) return false;
        Card c = projects.get(project).get(card);
        Map<String, Object> json = new HashMap<>();
        json.put("card", card);
        json.put("description", c.getDescription());
        json.put("history", c.getHistoryString());
        JSONObject obj = new JSONObject(json);
        File cFile = new File(Const.PROJECT_CARD(project, card));
        try {
            cFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(cFile);
            fos.write(obj.toJSONString().getBytes());
            fos.close();
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static boolean restore() throws IOException {
        File ps_dir = new File(Const.PROJECTS_FOLDER);
        if(!ps_dir.isDirectory()) return false;
        for(File p_dir : ps_dir.listFiles()) {
            if(!p_dir.isDirectory()) break;
            String p_json = Utils.readFile(Const.PROJECT_INFO(p_dir.getName()));
            try {
                Map<String, Object> p_map = (Map<String, Object>) new JSONParser().parse(p_json);
                String project = (String) p_map.get("project");
                List<String> cards = (List<String>) p_map.get("cards");
                List<String> members = (List<String>) p_map.get("members");
                projects.put(project, new Project(members.remove(0)));
                for(String member : members) {
                    projects.get(project).addMember(member);
                }
                for(String card : cards) {
                    String c_json = Utils.readFile(Const.PROJECT_CARD(project, card));
                    Map<String, Object> c_map = (Map<String, Object>) new JSONParser().parse(c_json);
                    String description = (String) c_map.get("description");
                    List<Integer> history = ((List<String>) c_map.get("history")).stream().map(v -> Integer.valueOf(v)).collect(Collectors.toList());
                    projects.get(project).addCard(card, description);
                    for(int i = 1; i < history.size(); i++) {
                        projects.get(project).moveCard(card, Status.fromOrdinal(history.get(i-1).intValue()),  Status.fromOrdinal(history.get(i).intValue()));
                    }
                }
            } catch (ParseException e) {
                if(Const.DEBUG) e.printStackTrace();
                continue;
            }
        }
        return true;
    }
}
