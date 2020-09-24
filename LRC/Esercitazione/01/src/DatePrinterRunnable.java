import java.util.Calendar;

public class DatePrinterRunnable implements Runnable{
    public static void main(String args[]) {
        DatePrinterRunnable dpr = new DatePrinterRunnable();
        Thread t = new Thread(dpr);
        t.start();
        System.out.println(Thread.currentThread());
    }

    public void run() {
        while(true) {
            Calendar c = Calendar.getInstance();
            System.out.println(Thread.currentThread() + " | " + c.getTime());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
