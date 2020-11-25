import java.util.Random;

public class NetErr {

    private Random rand;

    public NetErr(int seed) {
        rand = new Random(seed);
    }

    public int sendAfter() {
        if(rand.nextInt(4) == 0) {
            return -1;
        }
        return rand.nextInt(Const.MAX_SLEEP);
    }
}
