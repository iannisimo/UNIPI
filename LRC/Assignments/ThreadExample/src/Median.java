import java.util.Arrays;

public class Median implements Runnable{

    private final Integer[] vals;
    private Float median = null;

    public Median(Integer[] in) {
        vals = in.clone();
    }

    public Float getMedian() {
        return median;
    }

    public void run() {
        Arrays.sort(vals);
        int middle = vals.length/2;
        if(vals.length % 2 == 1) {
            median = Float.valueOf(vals[middle]);
            return;
        }
        median = ( (float) vals[middle-1] + vals[middle]) / 2;
    }
}
