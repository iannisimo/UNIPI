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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import worth.common.Status;
import worth.server.Const;
import worth.server.Utils;

public class Projects {
    private static Map<String, Project> projects = new HashMap<>();

    public synchronized static boolean isMember(String project, String member) {
        if(!exists(project)) return false;
        return projects.get(project).members.contains(member);
    }

    public synchronized static boolean deleteProject(String project) {
        if(!projects.containsKey(project)) return false;
        if(projects.get(project).cards.values().stream().anyMatch(c -> !c.getStatus().equals(Status.DONE))) return false;
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

    public synchronized static boolean addProject(String project, String member) {
        if(projects.containsKey(project)) return false;
        projects.put(project, new Project(member));
        store(project);
        return true;
    }

    public synchronized static List<Status> getCardHistory(String project, String card) {
        if(!exists(project, card)) return null;
        return projects.get(project).cards.get(card).history;
    }

    public synchronized static List<String> getCardInfo(String project, String card) {
        if(!exists(project, card)) return null;
        Card c = projects.get(project).cards.get(card);
        List<String> resp = new ArrayList<>();
        resp.add(card);
        resp.add(c.description);
        resp.add(c.getStatus().toString());
        return resp;
    }

    public synchronized static List<String> getCards(String project) {
        if(!exists(project)) return null;
        return new ArrayList<String>(projects.get(project).cards.keySet());
    }

    public synchronized static List<String> getMembers(String project) {
        if(!exists(project)) return null;
        return new ArrayList<String>(projects.get(project).members);
    }

    public synchronized static boolean exists(String project, String card) {
        if(!exists(project)) return false;
        return projects.get(project).cards.containsKey(card);
    }

    public synchronized static boolean exists(String project) {
        return projects.containsKey(project);
    }

    public synchronized static List<String> findProjects(String member) {
        List<String> ret = new ArrayList<>();
        projects.forEach((k, v) -> {
            if(v.members.contains(member)) ret.add(k);
        });
        return ret;
    }

    public synchronized static boolean addMember(String project, String member) {
        if(!projects.containsKey(project)) return false;
        boolean ret = projects.get(project).addMember(member);
        store(project);
        return ret;
    }

    public synchronized static boolean addCard(String project, String card, String description) {
        if(!exists(project)) return false;
        if(!projects.get(project).addCard(card, description)) return false;
        store(project);
        store(project, card);
        return true;
    }

    public synchronized static boolean moveCard(String project, String card, Status from, Status to) {
        if(!projects.containsKey(project)) return false;
        boolean ret = projects.get(project).moveCard(card, from, to);
        store(project, card);
        return ret;
    }

    @SuppressWarnings("all")
    private synchronized static boolean store(String project) {
        if(!projects.containsKey(project)) return false;

        JSONArray members = new JSONArray();
        members.addAll(projects.get(project).members);
        JSONArray cards = new JSONArray();
        cards.addAll(projects.get(project).cards.keySet());

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
        if(!projects.get(project).cards.containsKey(card)) return false;
        Card c = projects.get(project).cards.get(card);
        Map<String, Object> json = new HashMap<>();
        json.put("card", card);
        json.put("description", c.description);
        json.put("history", c.history);
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
                    List<Long> history = (List<Long>) c_map.get("history");
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
