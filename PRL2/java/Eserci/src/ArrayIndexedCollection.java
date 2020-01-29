public class ArrayIndexedCollection<E> implements IndexedCollection {

    private E[] data;
    private int arr_dim;
    private int size;

    public ArrayIndexedCollection(int len) {
        data = (E[]) new Object[len];
        size = 0;
        arr_dim = len;
    }

    @Override
    public void insertAt(Object elem, int p) throws NullPointerException, IndexOutOfBoundsException {
        if(elem == null) throw new NullPointerException();
        if(p < 0) throw new IndexOutOfBoundsException();
        if(p > size) {
            newSize(p + 1);

        }
    }

    @Override
    public Object get(int p) throws UndefinedIndexException {
        return null;
    }

    @Override
    public int indexOf(Object elem) throws NullPointerException {
        return 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void newSize(int new_size) {
        E[] new_data = (E[]) new Object[new_size];
        for(int i = 0; i < arr_dim; i++) {
            new_data[i] = data[i];
        }
        data = new_data;
        size = new_size;
    }
}
