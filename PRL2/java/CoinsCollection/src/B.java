public class B {
    public void foo(B obj) {
        System.out.print("B1 ");
    }

    public void foo(C obj) {
        System.out.print("B2 ");
    }
}