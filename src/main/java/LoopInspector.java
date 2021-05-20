public class LoopInspector {
  static int loopSize(Node node) {
    Node tortoise = node;
    Node hare = node.getNext();

    while (tortoise != hare) {
      tortoise = tortoise.getNext();
      hare = hare.getNext().getNext();
    }

    int length = 0;

    do {
      hare = hare.getNext();
      length++;
    } while (tortoise != hare);

    return length;
  }

  static class Node {
    private Node next;

    public Node getNext() {
      return this.next;
    }

    public static Node createFrom(int tail, int cycle) {
      Node start = new Node();
      Node tailNode = start;
      for (int i = 0; i < tail; i++) {
        tailNode.next = new Node();
        tailNode = tailNode.next;
      }

      Node cycleNode = tailNode;
      for (int i = 0; i < cycle; i++) {
        if (i == cycle - 1) {
          cycleNode.next = tailNode;
          break;
        } else {
          cycleNode.next = new Node();
          cycleNode = cycleNode.next;
        }

      }

      return start;
    }
  }
}
