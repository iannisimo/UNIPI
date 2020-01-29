package BoardPKG;

import java.util.*;

public class Board <E extends Data> implements DataBoard<E> {

    private String user;
    private String passw;
    private Map<String, Category<E>> map = new HashMap<>();

    public Board(String user, String passw) {
        this.user = user;
        this.passw = passw;
    }

    @Override
    public void createCategory(String category, String passw)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(map.containsKey(category)) throw new InvalidActionException("DataBoard.Category already present");
        map.put(category, new Category<>());
    }

    @Override
    public void removeCategory(String category, String passw)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!map.containsKey(category)) throw new InvalidActionException("DataBoard.Category not present");
        map.remove(category);
    }

    @Override
    public void addFriend(String category, String passw, String friend)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null || friend == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!map.containsKey(category)) throw new InvalidActionException("DataBoard.Category not present");
        map.get(category).addFriend(friend); //FIXME
    }

    @Override
    public void removeFriend(String category, String passw, String friend)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null || friend == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!map.containsKey(category)) throw new InvalidActionException("DataBoard.Category not present");
        map.get(category).removeFriend(friend); //FIXME
    }

    @Override
    public boolean put(String passw, E dato, String category)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null || dato == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!map.containsKey(category)) throw new InvalidActionException("DataBoard.Category not present");
        if(dato.getCategory() == null)
            dato.setCategory(category);
        if(dato.getUser() == null)
            dato.setUser(user);
        if(!dato.getCategory().equals(category)) throw new InvalidActionException("DataBoard.Category mismatch");
        if(!dato.getUser().equals(user)) throw new InvalidActionException("User mismatch");
        return map.get(category).addElement(dato);
    }

    @Override
    public E get(String passw, E dato)
            throws NullPointerException, InvalidActionException {
        if(passw == null || dato == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        return map.get(dato.getCategory()).getElement(dato);
    }

    @Override
    public E remove(String passw, E dato)
            throws NullPointerException, InvalidActionException {
        if(passw == null || dato == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        E ret = map.get(dato.getCategory()).removeElement(dato);
        if(ret == null) throw new InvalidActionException("DataBoard.Data not found");
        return ret;
    }

    @Override
    public List<E> getDataCategory(String passw, String category)
            throws NullPointerException, InvalidActionException {
        if(passw == null || category == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!map.containsKey(category)) throw new InvalidActionException("DataBoard.Category not present");
        return map.get(category).getElements();
    }


    // FIXME
    @Override
    public Iterator<E> getIterator(String passw)
            throws NullPointerException, InvalidActionException {
        if(passw == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        List<E> list = new ArrayList<>();
        Set<String> set = map.keySet();
        for(String category : set) {
            list.addAll(map.get(category).getElements());
        }
        Collections.sort(list);
        return list.iterator();
    }

    @Override
    public void insertLike(String friend, E data)
            throws NullPointerException, InvalidActionException {
        if(friend == null) throw new NullPointerException();
        if(!map.get(data.getCategory()).getFriends().contains(friend)) throw new InvalidActionException("Not friend");
        map.get(data.getCategory()).addLike(friend, data);
    }

    @Override
    public Iterator<E> getFriendIterator(String friend)
            throws NullPointerException {
        if(friend == null) throw new NullPointerException();
        List<E> list = new ArrayList<>();
        Set<String> set = map.keySet();
        for(String category : set) {
            if(map.get(category).getFriends().contains(friend))
                list.addAll(map.get(category).getElements());
        }
        return list.iterator();
    }

    public String getUser() {
        return user;
    }
}
