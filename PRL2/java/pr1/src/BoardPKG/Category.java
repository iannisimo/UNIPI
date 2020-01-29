package BoardPKG;

import java.util.ArrayList;
import java.util.List;

public class Category<E extends Data> {

    private List<String> friends;
    private List<E> elements;

    protected Category() {
        this.friends = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    protected Boolean addFriend(String friend) {
        if(this.friends.contains(friend)) return false;
        this.friends.add(friend);
        return true;
    }

    protected Boolean removeFriend(String friend) {
        if(!this.friends.contains(friend)) return false;
        this.friends.remove(friend);
        return true;
    }

    public List<String> getFriends() {
        return new ArrayList<>(friends);
    }

    protected boolean addElement(E dato) {
        if(elements.contains(dato)) return false;
        elements.add(dato);
        return true;
    }

    @SuppressWarnings("unchecked")
    public E getElement(E dato) {
        if(!elements.contains(dato)) return null;
        return (E) elements.get(elements.indexOf(dato)).copy();
    }

    protected E removeElement(E dato) {
        if(!elements.contains(dato)) return null;
        return elements.remove(elements.indexOf(dato));
    }

    public List<E> getElements() {
        return new ArrayList<>(elements);
    }

    protected Boolean addLike(String friend, E dato) {
        return elements.get(elements.indexOf(dato)).addLike(friend);
    }
}
