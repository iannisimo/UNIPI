package Worth.Server.Projects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import Worth.Server.Const;

public class Card {
    private String project;
    private String card;
    private String description;
    private List<Status> history;

    public Card(String project, String card, String description) {
        this.project = project;
        this.card = card;
        this.description = description;
        history = new ArrayList<>();
        history.add(Status.TODO);
        writeJSON();
    }

    public boolean move(Status from, Status to) {
        if(!from.equals(getStatus())) return false;
        if(!acceptedMoves(from, to)) return false;
        history.add(to);
        writeJSON();
        return true;
    }

    public String getName() {
        return this.card;
    }

    public String getDescription() {
        return this.description;
    }

    public Status getStatus() {
        // assert this.history.size() >= 1;
        return this.history.get(this.history.size() - 1);
    }


    // private boolean storeApp(Status s) {
    //     try {
    //         File f = new File(Const.PROJECT_CARD(this.project, this.card));
    //         if(!f.isFile()) f.createNewFile();
    //         FileOutputStream fos = new FileOutputStream(f, true);
    //         String data = String.format("%s\n", s);
    //         fos.write(data.getBytes());
    //         fos.close();
    //     } catch (IOException e) {
    //         if(Const.DEBUG) e.printStackTrace();
    //         return false;
    //     }
    //     return true;
    // }

    private boolean writeJSON() {
        Map<String, Object> map = new HashMap<>();
        map.put("p_name", this.project);
        map.put("c_name", this.card);
        map.put("description", this.description);
        map.put("history", this.history);
        JSONObject obj = new JSONObject(map);
        File f = new File(Const.PROJECT_CARD(this.project, this.card));
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f, false);
            fos.write(obj.toJSONString().getBytes());
            fos.close();
        } catch (IOException e) {
            if(Const.DEBUG) e.printStackTrace();
            return false;
        }
        return true;
    }

    // private boolean store() {
    //     try {
    //         File f = new File(Const.PROJECT_CARD(this.project, this.card));
    //         if(!f.isFile()) f.createNewFile();
    //         FileOutputStream fos = new FileOutputStream(f);
    //         StringBuilder data = new StringBuilder();
    //         data.append(description);
    //         data.append('\n');
    //         for(Status s : history) {
    //             data.append(s);
    //             data.append('\n');
    //         }
    //         fos.write(data.toString().getBytes());
    //         fos.close();
    //     } catch (IOException e) {
    //         if(Const.DEBUG) e.printStackTrace();
    //         return false;
    //     }
    //     return true;
    // }

    private boolean acceptedMoves(Status from, Status to) {
        switch (from) {
            case TODO:
                return to.equals(Status.INPROGRESS);
            case INPROGRESS:
                return to.equals(Status.TOBEREVISED) | to.equals(Status.DONE);
            case TOBEREVISED:
                return to.equals(Status.INPROGRESS) | to.equals(Status.DONE);
            default:
                break;
        }
        return false;
    }
}