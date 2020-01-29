/**
 * Typical El: <dim, lim, pos, {el_0 ... el_pos ... el_lim ... el_dim}>
 * Overview:   Container of finite elements of type T
 * Param dim:
 */
public interface Buffer<T> {

    /**
     * Overview: Puts the buffer in it's initial state
     * Modifies: this
     * Effects: this_post = {this_pre[dim], 0, 0, {}}
     */
    void clear();

    /**
     * Overview: Sets every position of the buffer re-readable/writable
     * Modifies: this
     * Effects:  this[lim] = this[pos] = 0
     */
    void rewind();

    /**
     * Overview: Puts the elements of src in the buffer
     * Requires: src != null, src.length <= this[lim] - this[pos]
     * Throws:   NullPointerException (Unchecked, predefined in Java)
     * Modifies: this
     *
     * Effects:  Puts up to this[lim] - this[pos] (free space) elements of src in the buffer
     * Effects:  if (src.length <= this[lim] - this[pos]): Puts all the elements of src in the buffer
     *                                               else: Puts this[lim] - this[pos] elements of src in the buffer
     *
     */
    void put(T[] src) throws NullPointerException;

    /**
     * Returns:  Every element of the buffer as an array of type T
     */
    T[] get();
}
