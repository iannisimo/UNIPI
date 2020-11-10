import java.util.concurrent.*;

public class GestioneConti {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: GestioneConti <json-file>");
        }
        BlockingQueue<Account> blockingQueue = new LinkedBlockingQueue<Account>();
        Producer producer = new Producer(args[0], blockingQueue);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Consumer[] consumers = new Consumer[Const.N_CONSUMERS];
        Thread[] consumerThreads = new Thread[Const.N_CONSUMERS];
        System.out.println("nome:\t[Bn,Ac,Bl,F2,PB]");
        for(int i = 0; i < Const.N_CONSUMERS; i++) {
            consumers[i] = new Consumer(blockingQueue);
            consumerThreads[i] = new Thread(consumers[i]);
            consumerThreads[i].start();
        }


    }
}
