public class start {
  public static void main(String[] args) {
    Node n1 = new Node(1, null);
    Node n2 = new Node(2, n1);
    Node n3 = n2;
    n3.next.
  }
}

class Node {
  private int el;
  private Node next;

  public Node (int e0, Node n0) {
    el = e0;
    next = n0;
  }

  public int head () {
    return el;
  }

  public Node tail () {
    return next;
  }

  public void append (Node a) {
    next = a;
  }
}

persist.service.adb.enable=1\npersist.service.debuggable=1\npersist.sys.usb.config=mtp,adb
