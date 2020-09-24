public class ThreadJoinExample {
    public static void main(String[] args) {
        Integer[] values = new Integer[] {3, 1, 14, 3, 4, 5, 6, 7, 8, 9, 11, 3, 2, 1 };
        Average avg = new Average(values);
        Median median = new Median(values);
        Thread t1 = new Thread(avg, "t1");
        Thread t2 = new Thread(median, "t2");
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Average: " + avg.getMean() + ", Median: " + median.getMedian());
    }
}
