import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Producer implements Runnable {
    private File file;
    private BlockingQueue<Account> blockingQueue;

    public Producer(String file, BlockingQueue<Account> blockingQueue) {
        this.file = new File(file);
        if (!this.file.exists()) {
            System.err.println("File not found!");
            System.exit(1);
        }
        this.blockingQueue = blockingQueue;
    }

    public void run() {
        ObjectMapper objectMapper = new ObjectMapper();
        Accounts accounts = null;
        try {
            accounts = objectMapper.readValue(this.file, Accounts.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Account account : accounts.getAccounts()) {
            try {
                blockingQueue.put(account);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
