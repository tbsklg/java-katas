package kyufive;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class DirReduction {

  public static String[] dirReduc(String[] arr) {
    return Path.from(arr).toArray();
  }

  private static class Path {
    private final MyStack<Direction> path;

    private Path(MyStack<Direction> path) {
      this.path = path;
    }

    public static Path from(String[] dirs) {
      final var directions =
              stream(dirs).map(Direction::from).toArray(Direction[]::new);

      return new Path(MyStack.empty()).reduce(directions);
    }

    private Optional<Direction> last() {
      return path.pop()
              .map(myStackDirectionPair -> myStackDirectionPair.right);
    }

    private Path add(Direction direction) {
      final var newPath = path.push(direction);
      return new Path(newPath);
    }

    private Path remove() {
      final var maybePath = path.pop();
      return maybePath.
              map(myStackDirectionPair -> new Path(myStackDirectionPair.left))
              .orElseGet(() -> new Path(MyStack.empty()));
    }

    private Path reduce(Direction[] arr) {
      if (arr.length == 0) {
        return this;
      }

      final var currentDirection = arr[0];
      final var furtherDirs = copyOfRange(arr, 1, arr.length);

      final var maybeLastDir = this.last();
      if (maybeLastDir.isEmpty()) {
        return this.add(currentDirection).reduce(furtherDirs);
      }

      final var lastDir = maybeLastDir.get();
      if (currentDirection.isOppositeOf(lastDir)) {
        return this.remove().reduce(furtherDirs);
      }

      return this.add(currentDirection).reduce(furtherDirs);
    }

    public String[] toArray() {
      final var path =
              this.path.stream()
                      .map(Direction::from)
                      .collect(Collectors.toList());

      Collections.reverse(path);

      return path.toArray(String[]::new);
    }
  }

  private enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    static Direction from(String dir) {
      return Direction.valueOf(dir);
    }

    static String from(Direction direction) {
      return direction.toString();
    }

    boolean isOppositeOf(Direction direction) {
      return this == NORTH && direction == SOUTH ||
              this == SOUTH && direction == NORTH ||
              this == WEST && direction == EAST ||
              this == EAST && direction == WEST;
    }
  }

  private static class MyStack<T> {

    private final Deque<T> deque;

    private MyStack(Deque<T> deque) {
      this.deque = deque;
    }

    public static <T> MyStack<T> empty() {
      Deque<T> deque = new ArrayDeque<>();

      return new MyStack<>(deque);
    }

    public MyStack<T> push(T c) {

      final var newDeque = new ArrayDeque<T>(deque);
      newDeque.push(c);

      return new MyStack<T>(newDeque);
    }

    private Optional<Pair<MyStack<T>, T>> pop() {
      if (this.deque.isEmpty()) {
        return Optional.empty();
      }

      final var newDeque = new ArrayDeque<T>(this.deque);

      final var first = newDeque.pop();
      final var myStack = new MyStack<T>(newDeque);

      return Optional.of(Pair.with(myStack, first));
    }

    public boolean isEmpty() {
      return this.deque.isEmpty();
    }

    public Stream<T> stream() {
      return this.deque.stream();
    }
  }

  private static class Pair<L, R> {
    private final L left;
    private final R right;

    private Pair(L left, R right) {
      this.left = left;
      this.right = right;
    }

    public static <L, R> Pair<L, R> with(final L left, final R right) {
      return new Pair<L, R>(left, right);
    }

    public L fst() {
      return left;
    }

    public R snd() {
      return right;
    }
  }
}
