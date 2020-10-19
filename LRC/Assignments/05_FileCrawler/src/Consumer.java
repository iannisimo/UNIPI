import java.io.File;

public class Consumer implements Runnable {

    private SyncQueue queue;

    public Consumer(SyncQueue queue) {
        this.queue = queue;
    }

    public void run() {
        while(!queue.isEmpty()) {
            File dir = new File(queue.get());
            StringBuilder sb = new StringBuilder();
            sb.append(dir.getAbsolutePath() + "\n");
            for (File file : dir.listFiles()) {
                String type = file.isDirectory() ? "[D] " : "[F] ";
                sb.append("\t" + type + file.getName() + "\n");
            }
            System.out.println(sb);
        }
    }
}
