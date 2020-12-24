package Worth.Server.Projects;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private String name;
    private String description;
    private List<Status> history;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
        history = new ArrayList<>();
        history.add(Status.TODO);
    }

    protected void move(Status to) {
        history.add(to);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}