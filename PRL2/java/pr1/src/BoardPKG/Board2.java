package BoardPKG;

import java.util.*;

public class Board2 <E extends Data> implements DataBoard<E> {

    String user;
    String passw;
    List<String> cats = new ArrayList<>();
    List<Category<E>> elems = new ArrayList<>();

    public Board2(String user, String passw) {
        this.user = user;
        this.passw = passw;
    }

    @Override
    public void createCategory(String category, String passw)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(cats.contains(category)) throw new InvalidActionException("DataBoard.Category already present");
        cats.add(category);
        elems.add(new Category<>());
    }

    @Override
    public void removeCategory(String category, String passw)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!cats.contains(category)) throw new InvalidActionException("DataBoard.Category not present");
        elems.remove(cats.indexOf(category));
        cats.remove(category);
    }

    @Override
    public void addFriend(String category, String passw, String friend)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null || friend == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!cats.contains(category)) throw new InvalidActionException("DataBoard.Category not present");
        elems.get(cats.indexOf(category)).addFriend(friend); //FIXME
    }

    @Override
    public void removeFriend(String category, String passw, String friend)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null || friend == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!cats.contains(category)) throw new InvalidActionException("DataBoard.Category not present");
        elems.get(cats.indexOf(category)).removeFriend(friend); //FIXME
    }

    @Override
    public boolean put(String passw, E dato, String category)
            throws NullPointerException, InvalidActionException {
        if(category == null || passw == null || dato == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!cats.contains(category)) throw new InvalidActionException("DataBoard.Category not present");
        if(dato.getCategory() == null)
            dato.setCategory(category);
        if(dato.getUser() == null)
            dato.setUser(user);
        if(!dato.getCategory().equals(category)) throw new InvalidActionException("DataBoard.Category mismatch");
        if(!dato.getUser().equals(user)) throw new InvalidActionException("User mismatch");
        return elems.get(cats.indexOf(category)).addElement(dato);
    }

    @Override
    public E get(String passw, E dato)
            throws NullPointerException, InvalidActionException {
        if(passw == null || dato == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        return elems.get(cats.indexOf(dato.getCategory())).getElement(dato);
    }

    @Override
    public E remove(String passw, E dato)
            throws NullPointerException, InvalidActionException {
        if(passw == null || dato == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        E ret = elems.get(cats.indexOf(dato.getCategory())).removeElement(dato);
        if(ret == null) throw new InvalidActionException("DataBoard.Data not found");
        return ret;
    }

    @Override
    public List<E> getDataCategory(String passw, String category)
            throws NullPointerException, InvalidActionException {
        if(passw == null || category == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        if(!cats.contains(category)) throw new InvalidActionException("DataBoard.Category not present");
        return elems.get(cats.indexOf(category)).getElements();
    }

    @Override
    public Iterator<E> getIterator(String passw)
            throws NullPointerException, InvalidActionException {
        if(passw == null) throw new NullPointerException();
        if(!passw.equals(this.passw)) throw new InvalidActionException("Password mismatch");
        List<E> list = new ArrayList<>();
        for(Category<E> elem : elems) {
            list.addAll(elem.getElements());
        }
        Collections.sort(list);
        return list.iterator();
    }

    @Override
    public void insertLike(String friend, E data)
            throws NullPointerException, InvalidActionException {
        if(friend == null) throw new NullPointerException();
        if(!elems.get(cats.indexOf(data.getCategory())).getFriends().contains(friend)) throw new InvalidActionException("Not friend");
        elems.get(cats.indexOf(data.getCategory())).addLike(friend, data);
    }

    @Override
    public Iterator<E> getFriendIterator(String friend)
            throws NullPointerException {
        if(passw == null) throw new NullPointerException();
        List<E> list = new ArrayList<>();
        for(Category<E> elem : elems) {
            if(elem.getFriends().contains(friend)) {
                list.addAll(elem.getElements());
            }
        }
        Collections.sort(list);
        return list.iterator();
    }

    @Override
    public String getUser() {
        return this.user;
    }
}
