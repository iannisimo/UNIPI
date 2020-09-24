import java.util.Calendar;

public class DatePrinter {
    public static void main(String args[]) {
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
