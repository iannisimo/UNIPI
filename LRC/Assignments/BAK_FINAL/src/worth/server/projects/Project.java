package worth.server.projects;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import worth.common.Status;
import worth.server.chat.IPGen;
import worth.server.users.Users;
import worth.server.utils.ConcurrentHashSet;

/**
 * Structure containing a project's information
 */
public class Project {
    private Map<Status, Map<String, Card>> cards;
    private Set<String> members;

    private Set<String> onlineMembers;
    private String mcastIP;

    public Project(String member) {
        this.cards = new ConcurrentHashMap<>();
        this.cards.put(Status.TODO, new ConcurrentHashMap<>());
        this.cards.put(Status.INPROGRESS, new ConcurrentHashMap<>());
        this.cards.put(Status.TOBEREVISED, new ConcurrentHashMap<>());
        this.cards.put(Status.DONE, new ConcurrentHashMap<>());
        this.members = new ConcurrentHashSet<>();
        this.members.add(member);
        this.onlineMembers = new ConcurrentHashSet<>();
    }

    /**
     * Gets a new Multicast IP for the project, if not already present
     * @param member The name of the user requesting the chat's address
     * @return The IP for the project's chat
     */
    public String joinChat(String member) {
        if(mcastIP == null) mcastIP = IPGen.getNewIPPort();
        onlineMembers.add(member);
        return mcastIP;
    }

    /**
     * Notify the user has exited the chat
     * @param member Name of the user exiting
     * @return {@code true} if {@code member} was in the chat
     */
    public boolean exitChat(String member) {
        boolean ret = onlineMembers.remove(member);
        if(onlineMembers.isEmpty() && mcastIP != null) IPGen.freeIP(mcastIP);
        mcastIP = null;
        return ret;
    }

    /**
     * Add a new member to the project
     * @param member New member to be added
     * @return {@code true} on success
     */
    public boolean addMember(String member) {
        if(!Users.exists(member)) return false;
        return members.add(member);
    }

    /**
     * @return {@code true} if all the cards in the project are in the DONE list
     */
    public boolean allDone() {
        return this.cards.entrySet().stream().filter(m -> !m.getKey().equals(Status.DONE)).flatMap(a -> a.getValue().values().stream()).count() == 0;
    }

    /**
     * @return All the members of the project
     */
    public List<String> getMembers() {
        return this.members.stream().collect(Collectors.toList());
    }

    /**
     * @param member Name of the user to be checked
     * @return {@code true} if the user is a member of the project
     */
    public boolean isMember(String member) {
        return this.members.contains(member);
    }

    /**
     * @param card Card whose existance is to be verified
     * @return {@code true} if the project contains the card
     */
    public boolean exists(String card) {
        return cards.values().stream().anyMatch(m -> m.containsKey(card));
    }

    /**
     * Add a card to the project
     * @param card Card's name
     * @param description Card's description
     * @return {@code true} on success
     */
    public boolean addCard(String card, String description) {
        if(exists(card)) return false;
        cards.get(Status.TODO).put(card, new Card(description));
        return true;
    }

    /**
     * Move a card from the {@code from} list to the {@code to} list
     * @param card Name of the card
     * @param from
     * @param to
     * @return {@code true} on success
     */
    public boolean moveCard(String card, Status from, Status to) {
        if(!exists(card)) return false;
        if(!cards.get(from).containsKey(card)) return false;
        if(!cards.get(from).get(card).move(from, to)) return false;
        cards.get(to).put(card, cards.get(from).remove(card));
        return true;
    }

    /**
     * Get the current status of the card
     * @param card Name of the card
     * @return The current status of the task defined by the card, {@code null} if the card is not found
     */
    public Status getCardStatus(String card) {
        return this.cards.entrySet().stream().filter(e -> e.getValue().get(card) != null).findFirst().map(e -> e.getKey()).orElse(null);
    }

    /**
     * @param card Name of the card to retreive
     * @return A Card element identified by it's name
     * @throws NoSuchElementException If the card is not present in the project
     */
    public Card get(String card) throws NoSuchElementException{
        return this.cards.values().stream().flatMap(m -> m.entrySet().stream()).filter(m -> m.getKey().equals(card)).findFirst().orElseThrow().getValue();
    }

    /**
     * @return All the cards
     */
    public List<String> getCards() {
        return this.cards.values().stream().flatMap(m -> m.keySet().stream()).collect(Collectors.toList());
    }
}
