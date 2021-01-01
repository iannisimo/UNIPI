package worth.server.projects;

import java.util.ArrayList;
import java.util.List;

import worth.common.Status;

public class Card {
    protected String description;
    protected List<Status> history;
    
    public Card(String description) {
        this.description = description;
        this.history = new ArrayList<>();
        this.history.add(Status.TODO);
    }

    public boolean move(Status from, Status to) {
        if(!getStatus().equals(from)) return false;
        if(!acceptedMoves(from, to)) return false;
        return history.add(to);
    }

    public Status getStatus() {
        return history.get(history.size() - 1);
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
