package BoardPKG;

import java.util.ArrayList;
import java.util.List;


/**
 * Overview: Base type for elements for the DataBoard.DataBoard.
 *           As such every subclass of DataBoard.Data can be used inside a DataBoard.Board
 *               (they must comply with the substitution principle obviously)
 * Typ-el:   {user, data, category, nLikes, like_0, ... , like_<nLikes>}
 *               user:     username of the owner of |this|
 *               data:     a string with the content of |this|
 *               category: the category in which |this| is posted
 *               nLikes:   number of likes of |this|
 *               like_i:   username of the i-th friend who liked |this|
 */
public class Data implements Comparable<Data>{

    private String user;
    private String data;
    private String category;
    private List<String> likes;

    public Data(String user, String data, String category) {
        this.user = user;
        this.data = data;
        this.category = category;
        this.likes = new ArrayList<>();
    }

    public Data(String user, String value) {
        this(user, value, null);
    }

    public Data(String value) {
        this(null, value, null);
    }

    /**
     * requires: friend != null
     * throws:   NullPointerException (unchecked, predefined)
     * modifies: this
     * effects:  if (this.likes doesn't contain friend):
     *             adds friend to this.likes
     *             returns true
     *           else:
     *             returns false
     */
    protected Boolean addLike(String friend) {
        if(likes.contains(friend)) return false;
        likes.add(friend);
        return true;
    }

    /**
     * requires: friend != null
     * throws:   NullPointerException (unchecked, predefined)
     * modifies: this
     * effects:  if (this.likes contains friend):
     *             removes friend from this.likes
     *             returns true
     *           else:
     *             returns false
     */
    protected Boolean remLike(String friend) {
        if(!likes.contains(friend)) return false;
        likes.remove(friend);
        return true;
    }

    /**
     * effects:  returns {like_0, ... , like_nLikes} as a list
     */
    public List<String> getLikes() {
        return new ArrayList<>(likes);
    }

    /**
     * effects:  returns nLikes
     */
    public Integer getNLikes() {
        return likes.size();
    }

    /**
     * effects:  returns a new DataBoard.Data maintaining {user, data, category}
     */
    protected Data copy() {
        return new Data(this.user, this.data, this.category);
    }

    /**
     * effects:  returns this.category
     */
    public String getCategory() {
        return category;
    }

    /**
     * requires: category != null
     * throws:   NullPointerException (unchecked, predefined)
     * modifies: this.category
     * effects:  sets the value of this.category to category IFF this.category is unset (== null)
     */
    protected void setCategory(String category) {
        if(this.category == null)
            this.category = category;
    }

    /**
     * effects:  returns this.user
     */
    public String getUser() {
        return user;
    }

    /**
     * requires: user != null
     * throws:   NullPointerException (unchecked, predefined)
     * modifies: this.user
     * effects:  sets the value of this.user to user IFF this.user is unset (== null)
     */
    protected void setUser(String user) {
        if(this.user == null)
            this.user = user;
    }

    /**
     * effects:  returns this.data
     */
    public String getData() {
        return data;
    }

    /**
     * effects:  returns the contents of this as a String
     */
    public String display() {
        return
                user +
                " wrote:\n\t" +
                data +
                "\nin category:\n\t" +
                category +
                "\nLiked by " +
                likes.size() +
                (likes.size() != 1 ? " people\n" : " person\n");
    }

    /**
     * Required by the Comparable interface
     * Used to sort DataBoard.Data elements
     */
    @Override
    public int compareTo(Data data) {
        if(this.equals(data)) return 0;
        if(this.getNLikes().equals(data.getNLikes())) {
            if(this.category.equals(data.getCategory()))
                return this.data.compareTo(data.getData());
            return this.category.compareTo(data.getCategory());
        }
        return this.getNLikes().compareTo(data.getNLikes());
    }
}
