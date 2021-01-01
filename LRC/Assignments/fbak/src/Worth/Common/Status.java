package Worth.Common;

public enum Status {
    TODO,
    INPROGRESS,
    TOBEREVISED,
    DONE;

    @Override
    public String toString() {
        return String.valueOf(super.ordinal());
    }

    public static Status fromOrdinal(Integer o) throws IllegalArgumentException {
        if (o < 0 || o > Status.values().length-1) throw new IllegalArgumentException();
        return Status.values()[o];
    }
}