package helpers;

public class Stack {

  private final Integer[] s;
  private int N = 0;

  public Stack(int capacity) {
    this.s = new Integer[capacity];
  }

  public void push(int item) {
    s[N++] = item;
  }

  public int pop() {
    if (this.isEmpty()) {
      return 0;
    }

    var item = s[--N];
    s[N] = null;
    return item;
  }

  public int peek() {
    if (N == 0) {
      return 0;
    }

    return s[N - 1];
  }

  public boolean isEmpty() {
    return N == 0;
  }
}
