public class C extends B {
    public void foo(B obj) {
        System.out.print("C1 ");
    }
    public void foo(C obj) {
        System.out.print("C2 ");
    }
}
