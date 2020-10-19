import java.io.FileNotFoundException;

public class FileCrawler {
    private static Thread producer;
    private static Thread[] consumer;
    private static SyncQueue queue;

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage: filecrawler <directory>");
            System.exit(1);
        }
        queue = new SyncQueue();
        try {
            Producer p = new Producer(args[0], queue);
            producer = new Thread(p);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        consumer = new Thread[Const.N_CONSUMERS];
        producer.start();
        Thread.sleep(10);
        for(int i = 0; i < consumer.length; i++) {
            consumer[i] = new Thread(new Consumer(queue));
            consumer[i].start();
        }
    }
}
