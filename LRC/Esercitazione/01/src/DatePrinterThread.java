import java.util.Calendar;

public class DatePrinterThread extends Thread {
    public static void main(String args[]) {
        DatePrinterThread dpt = new DatePrinterThread();
        dpt.start();
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
