import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable{
    private BlockingQueue<Account> blockingQueue;
    private SafeCounter safeCounter;

    public Consumer(BlockingQueue<Account> blockingQueue, SafeCounter safeCounter) {
        this.blockingQueue = blockingQueue;
        this.safeCounter = safeCounter;
    }

    public void run() {
        Account account;
        try {
            while ((account = blockingQueue.poll(1000, TimeUnit.MILLISECONDS)) != null) {
                int[] counters = new int[Const.causals.size()];
                for(Movement movement : account.getMovements()) {
                    counters[Const.causals.indexOf(movement.getCausal())]++;
                    safeCounter.plusOne(movement.getCausal());
                }
                // System.out.printf("%s:\t%s\n", account.getName(), Arrays.toString(counters));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
