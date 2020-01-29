public class main {
    public static void main(String[] args) {
        MyBuffer<Integer> myBuffer = new MyBuffer<>(10, 9);
        Integer[] a = {10, 2, 14, 4};
        myBuffer.put(a);
        Integer[] b = myBuffer.get();
        System.out.println(b);
    }
}
