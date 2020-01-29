package BoardPKG;

import java.util.Iterator;
import java.util.List;

/**
 * Overview: Collection of elements E (extending DataBoard.Data) characterized by a category with a friend list for each one, and a master password
 *           Friends can see the elements of every category they are added to.
 *           Elements and categories can be modified only using thr master password
 *           (YES, I ACCEPT EMPTY PASSWORDS, CATEGORIES, NAMES ETC...)
 * Typ-el:   {category_0{friends, elements}, ... , category_<size()>{friends, elements}, user, password}
 */

public interface DataBoard<E extends Data>{

    /**
     * requires: category != null
     *           category ~[in] this
     *           passw != null
     *           passw == this.password
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * modifies: this
     * effects:  Adds a new empty category to this
     */
    void createCategory(String category, String passw) throws NullPointerException, InvalidActionException;
    /**
     * requires: category != null
     *           passw != null
     *           passw == this.password
     *           category [in] this
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * modifies: this
     * effects:  Removes category from this
     */
    void removeCategory(String category, String passw) throws NullPointerException, InvalidActionException;

    /**
     * requires: category != null
     *           category [in] this
     *           passw != null
     *           passw == this.password
     *           friend != null
     *           friend ~[in] this.category
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * modifies: this.category
     * effects:  Adds friend to this.category.friends
     */
    void addFriend(String category, String passw, String friend) throws NullPointerException, InvalidActionException;

    /**
     * requires: category != null
     *           category [in] this
     *           passw != null
     *           passw == this.password
     *           friend != null
     *           friend [in] this.category.friends
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * modifies: this.category
     * effects:  Removes friend from this.category.friends
     */
    void removeFriend(String category, String passw, String friend) throws NullPointerException, InvalidActionException;

    /**
     * requires: category != null
     *           category [in] this
     *           category = dato.category
     *           passw != null
     *           passw == this.password
     *           dato != null
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * modifies: this.category
     * effects:  Adds dato to this.category.elements, returns false if dato was already present
     */
    boolean put(String passw, E dato, String category) throws NullPointerException, InvalidActionException;

    /**
     * requires: passw != null
     *           passw == this.password
     *           dato != null
     *           dato.category [in] this
     *           dato [in] this.{dato.category}.elements
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * effects:  Returns a copy of dato from this.category.elements, null if it in not present
     */
    E get(String passw, E dato) throws NullPointerException, InvalidActionException;

    /**
     * requires: passw != null
     *           passw == this.password
     *           dato != null
     *           dato [in] this.{dato.category}.elements
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * modifies: this
     * effects:  Removes dato from this.{dato.category}.elements and returns it
     */
    E remove(String passw, E dato) throws NullPointerException, InvalidActionException;

    /**
     * requires: category != null
     *           category [in] this
     *           passw != null
     *           passw == this.password
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * effects:  Returns a copy of this.category,elements as a List
     */
    List<E> getDataCategory(String passw, String category) throws NullPointerException, InvalidActionException;

    /**
     * requires: passw != null
     *           passw == this.password
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     * effects:  Returns every element in this.*.elements as an Iterator ordered with ascending likes
     */
    Iterator<E> getIterator(String passw) throws NullPointerException, InvalidActionException;

    /**
     * requires: data != null
     *           data.category [in] this
     *           data [in] this.{data.category}.elements
     *           friend != null
     *           friend [in] this.{data.category}.friends
     * throws:   NullPointerException (unchecked, predefined)
     *           DataBoard.InvalidActionException (checked, not native)
     */
    void insertLike(String friend, E data) throws NullPointerException, InvalidActionException;

    /**
     * requires: friend != null
     * throws:   NullPointerException (unchecked, predefined)
     * effects:  Returns every element in this.cat.elements if friend [in] this.cat.friends | cat [in] {category_0, category_<size()>}
     */
    Iterator<E> getFriendIterator(String friend) throws NullPointerException;

    /**
     * effects:  Returns this.user
     */
    String getUser();
}