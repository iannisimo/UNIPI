class ThreadDemo extends Thread {
  public static void main(String[] args) {
    ThreadDemo t = new ThreadDemo();
    t.start();
    System.out.print("one");
    t.start();
    System.out.print("two");
  }
  public void run() {
    System.out.print("Thread ");
  }
}
