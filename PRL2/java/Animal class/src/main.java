import java.util.Vector;

public class main {
    public static void main(String[] args) {
        Vector<Lion> lions = new Vector<Lion>(100);
        lions.setSize(100);
        for(int i = 0; i < lions.capacity(); i++) {
            Lion lion = new Lion(i);
            lions.set(i, lion);
        }
        for(Lion lion : lions) {
            lion.kill(500);
            System.out.println(lion);
        }
    }
}
