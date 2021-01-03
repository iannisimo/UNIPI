package worth.server.projects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import worth.common.Status;

/**
 * Structure containing a card's information
 */
public class Card {
    private String description;
    private List<Status> history;
    
    public Card(String description) {
        this.description = description;
        this.history = new ArrayList<>();
        this.history.add(Status.TODO);
    }

    public boolean move(Status from, Status to) {
        if(!acceptedMoves(from, to)) return false;
        return this.history.add(to);
    }

    public String getDescription() {
        return this.description;
    }

    public List<Status> getHistory() {
        return this.history;
    }

    public List<String> getHistoryString() {
        return this.history.stream().map(s -> s.toString()).collect(Collectors.toList());
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
