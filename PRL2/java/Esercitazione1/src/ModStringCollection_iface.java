public interface ModStringCollection_iface {
    /**
     * * Overview: This method returns the number of occurrences of -str-
     * Requires: str != null
     * Throws:   NullPointerException
     * Returns:  The number of occurrences of -str- in this;
     *           0 if there are no occurrences of -str-
     */
    public int occurrences(ModString_iface str) throws NullPointerException;

    /**
     * * Overview: This method returns the cardinality of the collection
     * Returns: The cardinality of the collection
     */
    public int size();

    /**
     * Overview: This method adds -str- to the collection
     * Modifies: this
     * Effects;  this_post = this_pre ∪ {str}
     * Requires: str != null
     * Throws:   NullPointerException
     */
    public void insert(ModString_iface str) throws NullPointerException;

    /**
     * * Overview: This method removes at most -num- occurrences of -str- from this
     * Modifies: this
     * Effects:  if -num- >  0:
     *              Removes at most -num- occurrences of -str- from this
     *           if -num- == 0;
     *              Removes every occurrence of -stf- from this
     * Requires: str != null, num >= 0
     * Throws:   NullPointerException, IllegalArgumentException
     * Returns:  The effective number of strings removed from the collection
     */
    public int remove(ModString_iface str, int num) throws NullPointerException, IllegalArgumentException;

    /**
     * Overview: This method returns the elements without duplicates
     * Returns:  A String array containing all the elements in the collection without duplicates
     */
    public String[] getUnique();
}
