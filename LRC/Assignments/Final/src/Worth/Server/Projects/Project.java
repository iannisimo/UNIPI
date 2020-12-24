package Worth.Server.Projects;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private List<String> members;
    private CardLists cards;

    public Project(String name, String member) {
        this.name = name;
        this.members = new ArrayList<>();
        this.members.add(member);
        this.cards = new CardLists();
    }

    public boolean addCard(String name, String description) {
        return cards.add(name, description);
    }

    public String getName() {
        return this.name;
    }
}
