package worth.server.projects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import worth.common.Status;
import worth.server.users.Users;

public class Project {
    protected Map<String, Card> cards;
    protected Set<String> members;

    public Project(String member) {
        this.cards = new HashMap<>();
        this.members = new HashSet<>();
        this.members.add(member);
    }

    public boolean addMember(String member) {
        if(!Users.exists(member)) return false;
        return members.add(member);
    }

    public boolean addCard(String card, String description) {
        if(cards.containsKey(card)) return false;
        cards.put(card, new Card(description));
        return true;
    }

    public boolean moveCard(String card, Status from, Status to) {
        if(!cards.containsKey(card)) return false;
        return cards.get(card).move(from, to);
    }
}
