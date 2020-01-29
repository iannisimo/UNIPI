public class main {
    public static void main(String[] args) {
        B c = new C();
        B b = new B();
        b.foo(c);
        c.foo(b);
        c.foo(c);
    }
}
