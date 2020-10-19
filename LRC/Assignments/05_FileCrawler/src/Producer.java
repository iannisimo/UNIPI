import java.io.File;
import java.io.FileNotFoundException;

public class Producer implements Runnable {
    private File rootDir;
    private SyncQueue queue;

    public Producer(String rootDir, SyncQueue queue) throws FileNotFoundException {
        this.rootDir = new File(rootDir);
        if(!this.rootDir.isDirectory()) throw new FileNotFoundException(rootDir + " is not a directory");
        this.queue = queue;
    }

    public void run() {
        enqueueFromDir(rootDir);
    }

    private void enqueueFromDir(File dir) {
        queue.add(dir.getAbsolutePath());
        for(File subDir : dir.listFiles()) {
            if(!subDir.isDirectory()) continue;
            enqueueFromDir(subDir);
        }
    }
}
