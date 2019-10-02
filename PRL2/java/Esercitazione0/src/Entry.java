public class Entry {

    private String key;
    private Integer value;

    public Entry(String key, Integer value) throws EmptyStringException {
        if(key.equals(new String(""))) throw new EmptyStringException("String failed successfully");
        this.key = key;
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getKey() {
        return this.key;
    }
}
