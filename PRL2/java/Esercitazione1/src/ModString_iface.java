public interface ModString_iface {

    /**
     * Overview: This method returns the length of the ModString
     * Returns:  string length
     */
    public int size();

    /**
     * Overview: This method changes the character in position -num- with the character -c-
     * Modifies: this
     * Effects:  this_post[num] = c
     * Requires: 0 <= num < size()
     * Throws:   IllegalArgumentException
     */
    public void update(char c, int num) throws IllegalArgumentException;

    /**
     * Overview: Removes the character at position -num- and shifts the remaining characters
     * Modifies: this
     * Effects:
     * Requires: 0 <= dim < size()
     * Throws:   IllegalArgumentException
     */
    public void remove(int num) throws IllegalArgumentException;
}


