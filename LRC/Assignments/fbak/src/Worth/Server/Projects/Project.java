package Worth.Server.Projects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import Worth.Common.Status;
import Worth.Server.Const;
import Worth.Server.Users.Users;

public class Project {
    private String project;
    private List<String> members;
    private Map<String, Card> cards;
    private boolean sync = false;

    public Project(String project, String member, boolean sync) {
        this.sync = sync;
        this.project = project;
        this.members = new ArrayList<>();
        this.members.add(member);
        this.cards = new HashMap<>();
        writeJSON();
    }

    public Project(String project, String member) {
        this(project, member, true);
    }

    public boolean addMember(String username) {
        if (!Users.isRegistered(username))
            return false;
        boolean val = this.members.add(username);
        writeJSON();
        return val;
    }

    public boolean addCard(String card, String description) {
        if (cards.containsKey(card))
            return false;
        cards.put(card, new Card(this.project, card, description, this.sync));
        writeJSON();
        return true;
    }

    public boolean moveCard(String card, Status from, Status to) {
        if (!cards.containsKey(card))
            return false;
        return cards.get(card).move(from, to);
    }

    private boolean writeJSON() {
        if(!this.sync) return false;
        Map<String, Object> map = new HashMap<>();
        map.put("p_name", this.project);
        map.put("members", this.members);
        map.put("cards", new ArrayList<>(this.cards.keySet()));
        JSONObject obj = new JSONObject(map);
        File d = new File(Const.PROJECT_FOLDER(this.project));
        File f = new File(Const.PROJECT_INFO(this.project));
        try {
            d.mkdir();
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f, false);
            fos.write(obj.toJSONString().getBytes());
            fos.close();
        } catch (IOException e) {
            if (Const.DEBUG)
                e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Card getCard(String card) {
        return this.cards.get(card);
    }

    // private boolean store() {
    // try {
    // File d = new File(Const.PROJECT_FOLDER(this.name));
    // if(!d.isDirectory()) d.mkdir();
    // File f = new File(Const.PROJECT_INFO(this.name));
    // if(!f.isFile()) f.createNewFile();
    // FileOutputStream fos = new FileOutputStream(f);
    // StringBuilder data = new StringBuilder();
    // for(String m : this.members) {
    // data.append(m);
    // data.append('\n');
    // }
    // fos.write(data.toString().getBytes());
    // fos.close();
    // } catch (IOException e) {
    // if(Const.DEBUG) e.printStackTrace();
    // return false;
    // }
    // return true;
    // }

    public String getProject() {
        return this.project;
    }

    public void startSync() {
        this.sync = true;
    }
}
