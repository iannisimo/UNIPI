public interface IndexedCollection<E> {
    /**
     * Overview:   Collection of non-null elements of type E distinguishable by a unique index
     * Typical EL: {<i_0, el_0> ... <i_size(), el_size()>}
     */

    /**
     * Overview: Inserts elem in the collection in position p, shifting, if present, the element in <p, old_elem> to a free position <new_p, old_elem>
     * Requires: elem != null, p >= 0
     * Throws:   NullPointerException (unchecked, predefined in Java)
     *           IllegalArgumentException (unchecked, predefined in Java)
     * Modifies: this
     * Effects:  if (this does not contain <p, old_elem>): adds <p,  elem>
     *                                               else: moves <p, old_elem> to <new_p, old_elem> and adds <p, elem> to this
     */
    void insertAt(E elem, int p) throws NullPointerException, IllegalArgumentException;

    /**
     * Overview: Returns the element in position p
     * Requires: this must contain <p, ?>
     * Throws:   IndexNotFoundException (checked, not present in Java)
     * Modifies: n.a.
     * Returns:  elem of <p, elem>
     */
    E get(int p) throws IndexNotFoundException;

    /**
     * Overview: Returns the position of elem in the collection
     * Requires: elem != null, this must contain <?, elem>
     * Throws:   NullPointerException (unchecked, predefined in Java)
     *           ElementNotFoundException (checked, not present in java)
     * Modifies: n.a.
     * Returns:  p of <p, elem>
     */
    int indexOf(E elem) throws NullPointerException, ElementNotFoundException;
}

class IndexNotFoundException extends Exception{
    public IndexNotFoundException() {
        super();
    }
    public IndexNotFoundException(String s) {
        super (s);
    }
}

class ElementNotFoundException extends Exception {
    public ElementNotFoundException() {
        super();
    }
    public ElementNotFoundException(String s) {
        super (s);
    }
}