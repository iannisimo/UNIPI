public class test {
    public static void main(String[] args) {
        Animal l = new Lion(true, 10);
        Bunny b = new Bunny(false, 10);
        System.out.println(b);
        b.setAlive(100);
        System.out.println(l);
        System.out.println(b);
    }
}