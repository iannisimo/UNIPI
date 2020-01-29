public class ArrayIndexedCollection<E> implements IndexedCollection{
    /**
     * IR:
     *     data != null &&
     *     0 < data.length = dim;
     */

    private static final int ARR_INCREMENT = 10;
    private E[] data;
    int dim;

    public ArrayIndexedCollection() {
        this.data = (E[]) new Object[ARR_INCREMENT];
        this.dim = ARR_INCREMENT;
    }

    public ArrayIndexedCollection(int dim) {
        this.data = (E[]) new Object[dim];
        this.dim = dim;
    }

    private void resize(int new_dim) {
        E[] new_data = (E[]) new Object[new_dim];
        for(int i = 0; i < dim; i++) {
            if(data[i] != null) {
                new_data[i] = data[i];
            }
        }
        data = new_data;
        dim = new_dim;
    }

    private int freePos() {
        for(int i = 0; i < dim; i++) {
            if(data[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertAt(Object elem, int p) throws NullPointerException, IllegalArgumentException {
        if(elem == null) throw new NullPointerException();
        if(p < 0) throw new IllegalArgumentException();
        if(data[p] == null) {
            data[p] = (E) elem;
        }
    }

    @Override
    public Object get(int p) throws IndexNotFoundException {
        return null;
    }

    @Override
    public int indexOf(Object elem) throws NullPointerException, ElementNotFoundException {
        return 0;
    }
}
