public class Average implements Runnable {

    private final Integer[] vals;
    private Float mean = null;

    public Average(Integer[] in) {
        vals = in.clone();
    }
    public Float getMean() {
        return mean;
    }
    @Override
    public void run() {
        float sum = 0;
        for (Integer val : vals) {
            sum += val;
        }
        mean = sum / vals.length;
    }
}
