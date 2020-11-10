import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class GestioneConti {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: GestioneConti <json-file>");
        }
        SafeCounter safeCounter = new SafeCounter(Const.causals);
        BlockingQueue<Account> blockingQueue = new LinkedBlockingQueue<Account>();
        Producer producer = new Producer(args[0], blockingQueue);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Consumer[] consumers = new Consumer[Const.N_CONSUMERS];
        Thread[] consumerThreads = new Thread[Const.N_CONSUMERS];
        for (int i = 0; i < Const.N_CONSUMERS; i++) {
            consumers[i] = new Consumer(blockingQueue, safeCounter);
            consumerThreads[i] = new Thread(consumers[i]);
            consumerThreads[i].start();
        }
        try {
            producerThread.join();
            for(Thread thread : consumerThreads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(safeCounter);
    }
}
