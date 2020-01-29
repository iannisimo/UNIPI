public interface IndexedCollection<E> {
    /**
     * Overview:   Collezione idicizzata di elementi non null con dimensione finita
     * Typical el: <ind_0, el_0>, ... <ind_{size()-1}, el_{size()-1}>
     */

    /**
     * Requires: elem != null, p >= 0
     * Throws;   NullPointerException (unchecked, predefined)
     *           IndexOutOfBoundsException (unchecked, predefined)
     * Modifies: this
     * Effects:  Inserisce la coppia <p, elem> alla posizione p; se essa era occupata dalla coppia <p, old_elem>, viene inserita la coppia <k, old_elem> con k non presente nella collezione
     */
    public void insertAt(E elem, int p) throws NullPointerException, IndexOutOfBoundsException;

    /**
     * Requires: <p, elem> appartiene alla collezione
     * Throws:   UndefinedIndexException (checked, user-defined)
     * Effects:  Ritorna l'elemento el_p
     */
    public E get(int p) throws UndefinedIndexException;

    /**
     * Requires: elem != null
     * Throws:   NullPointerException (unchecked, predefined)
     * Effects:  Ritorna p se <p, elem> appartiene alla collezione e per ogni i t.c. <i, elem> appartiene alla collezione si ha i<=p
     */
    public int indexOf(E elem) throws NullPointerException;

    /**
     * Effects: Restituisce la cardinalita' della collezione
     */
    public int size();

}