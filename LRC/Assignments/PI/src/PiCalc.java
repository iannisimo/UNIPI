import java.security.DomainLoadStoreParameter;
import java.util.Calendar;

import static java.lang.Math.abs;

public class PiCalc extends Thread {
    private static final Double refPI = Math.PI;
    private final Float accuracy;
    private final Integer timeout;
    private Double pi = 0.0;


    public PiCalc(Float acc, Integer to) {
        this.accuracy = acc;
        this.timeout = to;
    }
    public Double getPi() {
        return new Double(pi);
    }
    public void run() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        int i = 0;
        do {
//            System.out.println("i: " + i + " val: " + 4.0 / (i*2+1) + " pi: " + pi);
            if (i%2 == 0) {
                pi += 4.0 / (i*2+1);
            } else {
                pi -= 4.0 / (i*2+1);
            }
            i++;
        } while(abs(pi - refPI) > accuracy && Calendar.getInstance().getTimeInMillis() - startTime <= timeout);
    }
}
