import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafeCounter {
    private Map<String, Integer> counter;

    public SafeCounter(List<String> causals) {
        this.counter = new HashMap<>();
        for(String causal : causals) {
            this.counter.put(causal, 0);
        }
    }

    public synchronized void plusOne(String causal) {
        this.counter.replace(causal, this.counter.get(causal)+1);
    }

    public String toString() {
        return counter.toString();
    }
}
