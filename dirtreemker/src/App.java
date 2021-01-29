import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        File root = new File(args[0]);
        recprint(root, 2);
    }

    private static void recprint(File dir, int depth) {
        for(File f : dir.listFiles()) {
            System.out.printf(".%d %s.\n", depth, f.getName());
            if(f.isDirectory()) recprint(f, depth + 1);
        }
    }
}
