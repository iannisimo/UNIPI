import java.util.Random;

public class utils {
    public static int randominRange(int min, int max) {
        return min + new Random().nextInt(max - min);
    }
}
