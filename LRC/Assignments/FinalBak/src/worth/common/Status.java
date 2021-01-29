package worth.common;

import java.util.Arrays;
import java.util.List;

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

    public static List<String> getListString() {
        return Arrays.asList("Todo", "In progress", "To be revised", "Done");
    }

    public static Status fromString(String s) {
        return fromOrdinal(getListString().indexOf(s));
    }
}