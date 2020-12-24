package Worth.Server.Projects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardLists {
    private Map<Status, Map<String, Card>> lists;
    private List<String> names;

    public CardLists() {
        lists = new HashMap<>();
        lists.put(Status.TODO, new HashMap<>());
        lists.put(Status.INPROGRESS, new HashMap<>());
        lists.put(Status.TOBEREVISED, new HashMap<>());
        lists.put(Status.DONE, new HashMap<>());
        names = new ArrayList<>();
    }

    protected boolean add(String name, String description) {
        if(names.contains(name)) return false;
        names.add(name);
        lists.get(Status.TODO).put(name, new Card(name, description));
        return true;
    }

    public Boolean move(String card, Status from, Status to) {
        if(!acceptedMoves(from, to)) return false;
        if(!lists.get(from).containsKey(card)) return false;
        Card c = lists.get(from).remove(card);
        c.move(to);
        lists.get(to).put(c.getName(), c);
        return true;
    }

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


enum Status {
    TODO,
    INPROGRESS,
    TOBEREVISED,
    DONE;
}