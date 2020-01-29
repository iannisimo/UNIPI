import java.util.Vector;

/**
 * IR:
 *     elems != null
 *     && 0 <= position < limit < capacity
 */

public class MyBuffer<T> implements Buffer {

    private Vector<T> elems;
    private int capacity;
    private int limit;
    private int position;

    public MyBuffer(int cap, int lim) {
        if(lim < 0 || lim >= cap) throw new IllegalArgumentException();
        this.elems = new Vector<>(cap);
        this.capacity = cap;
        this.limit = lim;
        this.position = 0;
    }

    @Override
    public void clear() {
        this.elems.clear();
        this.position = 0;
    }

    @Override
    public void rewind() {
        this.position = 0;
    }

    @Override
    public void put(Object[] src) throws NullPointerException {
        if(src == null) throw new NullPointerException();
        for(Object obj : src) {
            if(position < limit) {
                elems.add((T) obj);
                position++;
            } else {
                break;
            }
        }
    }

    @Override
    public T[] get() {
        T[] out = (T[]) new Object[this.position];
        int i = 0;
        for(T obj : elems) {
            out[i] = obj;
            i++;
        }
        return out;
    }

}
