import java.util.NoSuchElementException;

public class Queue {
    private String[] q = new String[8];

    private int head = 0;
    private int tail = 0;
    private int n = 0;

    public boolean isEmpty() {
        return n == 0;
    }

    public void enqueue(String item) {
        if (n == q.length) resize(2 * q.length);
        q[tail++] = item;
        if (tail == q.length) tail = 0;
        n++;
    }

    public String dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        String item = q[head];
        q[head] = null;
        n--;
        head++;

        if (head == q.length) head = 0;

        if (n > 0 && n == q.length / 4) resize(q.length / 2);
        return item;
    }

    private void resize(int capacity) {
        assert capacity >= n;

        String[] copy = new String[capacity];

        for (int i = 0; i < n; i++) {
            copy[i] = q[(head + i) % q.length];
        }

        q = copy;
        head = 0;
        tail = n;
    }
}
