import java.util.Random;

public class Utils {
    public static enum UserType {
        Professor,
        Grad,
        Undergrad
    }

    public static int random(int lowerBound, int upperBound) {
        return new Random().nextInt(upperBound - lowerBound) + lowerBound;
    }
}
