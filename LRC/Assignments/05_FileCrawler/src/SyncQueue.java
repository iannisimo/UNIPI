import java.util.LinkedList;
import java.util.List;

public class SyncQueue {
    private static List<String> queue;

    public SyncQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void add(String file) {
        if(file == null) throw new NullPointerException();
        queue.add(file);
    }

    public synchronized String get() {
        return queue.remove(0);
    }

    public synchronized Boolean isEmpty() {
        return queue.isEmpty();
    }
}
