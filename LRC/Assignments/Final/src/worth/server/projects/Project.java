package worth.server.projects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import worth.common.Status;
import worth.server.chat.IPGen;
import worth.server.users.Users;

public class Project {
    private Map<Status, Map<String, Card>> cards;
    private Set<String> members;

    private Set<String> onlineMembers;
    private String mcastIP;

    public Project(String member) {
        this.cards = new HashMap<>();
        this.cards.put(Status.TODO, new HashMap<>());
        this.cards.put(Status.INPROGRESS, new HashMap<>());
        this.cards.put(Status.TOBEREVISED, new HashMap<>());
        this.cards.put(Status.DONE, new HashMap<>());
        this.members = new HashSet<>();
        this.members.add(member);
        this.onlineMembers = new HashSet<>();
    }

    public String joinChat(String member) {
        if(mcastIP == null) mcastIP = IPGen.getNewIPPort();
        onlineMembers.add(member);
        return mcastIP;
    }

    public boolean exitChat(String member) {
        boolean ret = onlineMembers.remove(member);
        if(onlineMembers.isEmpty() && mcastIP != null) IPGen.freeIP(mcastIP);
        mcastIP = null;
        return ret;
    }

    public boolean addMember(String member) {
        if(!Users.exists(member)) return false;
        return members.add(member);
    }

    public boolean allDone() {
        return this.cards.entrySet().stream().filter(m -> !m.getKey().equals(Status.DONE)).flatMap(a -> a.getValue().values().stream()).count() == 0;
    }

    public List<String> getMembers() {
        return this.members.stream().collect(Collectors.toList());
    }

    public boolean isMember(String member) {
        return this.members.contains(member);
    }

    public boolean exists(String card) {
        return cards.values().stream().anyMatch(m -> m.containsKey(card));
    }

    public boolean addCard(String card, String description) {
        if(exists(card)) return false;
        cards.get(Status.TODO).put(card, new Card(description));
        return true;
    }

    public boolean moveCard(String card, Status from, Status to) {
        if(!exists(card)) return false;
        if(!cards.get(from).containsKey(card)) return false;
        if(!cards.get(from).get(card).move(from, to)) return false;
        cards.get(to).put(card, cards.get(from).remove(card));
        return true;
    }

    public Status getCardStatus(String card) {
        return this.cards.entrySet().stream().filter(e -> e.getValue().get(card) != null).findFirst().map(e -> e.getKey()).orElse(null);
    }

    public Card get(String card) {
        return this.cards.values().stream().flatMap(m -> m.entrySet().stream()).filter(m -> m.getKey().equals(card)).findFirst().orElseThrow().getValue();
    }

    public List<String> getCards() {
        return this.cards.values().stream().flatMap(m -> m.keySet().stream()).collect(Collectors.toList());
    }
}
