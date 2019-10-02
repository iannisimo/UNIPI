public class StringTable {

    private static final int dim_incr = 10;

    private int capacity;
    private Entry[] entrys;
    private int index;
    private int min_index;

    // Constructor

    public StringTable() {;
        capacity = dim_incr;
        entrys = new Entry[capacity];
        index = 0;
        min_index = 0;
    }

    // Private methods

    private void incrCapacity() {
        System.out.println("Resizing... Wait");
        int new_capacity = capacity + dim_incr;
        Entry[] new_entrys = new Entry[new_capacity];
        for(int i = 0; i < capacity; i++) {
            new_entrys[i] = entrys[i];
        }
        capacity = new_capacity;
        entrys = new_entrys;
    }

    private Boolean tableContainsKey(String key) {
        for(int i = 0; i < index; i++) {
            if(entrys[i].getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    // Public Methods

    public void addName(String key, int value) throws DuplicateEntryException, EmptyStringException {
        if(tableContainsKey(key)) throw new DuplicateEntryException("Task failed successfully");
        // Add entry and check for min;
        entrys[index] = new Entry(key, value);
        if(value < entrys[min_index].getValue()) {
            min_index = index;
        }
        index++;
        // Increment capacity
        if(index >= capacity) {
            incrCapacity();
        }
    }

    public Integer getVal(String key) throws keyNotFoundException {
        for (int i = 0; i < index; i++) {
            if(entrys[i].getKey().equals(key)) {
                return entrys[i].getValue();
            }
        }
        throw new keyNotFoundException("Fra, nun ce sta\'");
    }

    public String getLowest() throws keyNotFoundException {
        if(index == 0) throw new keyNotFoundException("Fra, nun c\'abbiamo  le chiavi");
        return entrys[min_index].getKey();
    }

    public int size() {
        return index;
    }
}
