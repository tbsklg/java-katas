package kyufive;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static kyufive.Encoder.MyListCollector.toMyList;

public class Encoder {
  public String compress(int[] raw) {
    return Music.from(raw).encode().asString();
  }

  private static class Music {
    private final MyList<Integer> elements;
    private final MyList<String> encodings;

    private Music(MyList<Integer> elements, MyList<String> encodings) {
      this.elements = elements;
      this.encodings = encodings;
    }

    public static Music from(int[] raw) {
      final var elements = MyList.from(Arrays.stream(raw).boxed().collect(toList()));
      return new Music(elements, MyList.create());
    }

    private static Music from(MyList<Integer> elements, MyList<String> encodings) {
      return new Music(elements, encodings);
    }

    private Music encode() {
      if (elements.isEmpty()) return this;

      if (isIdentical()) return identicalEncoding().encode();

      if (isInterval()) return intervalEncoding().encode();

      return singleEncoding().encode();
    }

    private boolean isIdentical() {
      return elements.length() >= 2 && elements.head().equals(elements.tail().head());
    }

    private boolean isInterval() {
      return elements.length() > 2
              && elements.head() - elements.tail().head() == elements.tail().head() - elements.tail().tail().head();
    }

    private Music singleEncoding() {
      final var encoding = SingleEncoding.from(elements).encode();
      final var nextEncoding = encodings.append(encoding.left);

      return Music.from(encoding.right, nextEncoding);
    }

    private Music identicalEncoding() {
      final var encoding = IdenticalEncoding.from(elements).encode();
      final var nextEncoding = encodings.append(encoding.left);

      return Music.from(encoding.right, nextEncoding);
    }

    private Music intervalEncoding() {
      final var encoding = IntervalEncoding.from(elements).encode();
      final var nextEncoding = encodings.append(encoding.left);

      return Music.from(encoding.right, nextEncoding);
    }

    public String asString() {
      return encodings.stream().collect(joining(","));
    }
  }

  static class IntervalEncoding implements Encoding {

    private final MyList<Integer> elements;
    private final int distance;

    private IntervalEncoding(MyList<Integer> elements, int distance) {
      this.elements = elements;
      this.distance = distance;
    }

    public static IntervalEncoding from(MyList<Integer> elements) {
      assert elements.length() > 2;
      return new IntervalEncoding(elements, distance(elements));
    }

    private static int distance(MyList<Integer> elements) {
      final var head = elements.head();
      final var next = elements.tail().head();

      return next - head;
    }

    @Override
    public Pair<String, MyList<Integer>> encode() {
      final var other = IntStream
              .iterate(elements.head(), i -> i + distance)
              .limit(elements.length())
              .boxed()
              .collect(toList());

      final var zipped = elements.zip(MyList.from(other));

      final var result = zipped.stream()
              .takeWhile(Pair::identical)
              .map(Pair::fst)
              .collect(toMyList());

      final var next = zipped.stream()
              .dropWhile(Pair::identical)
              .map(Pair::fst)
              .collect(toMyList());

      return Pair.with(encoded(result), next);
    }

    private String encoded(MyList<Integer> l) {
      if (Math.abs(distance) == 1) return format("{0}-{1}", l.head(), l.last());

      return format("{0}-{1}/{2}", l.head(), l.last(), Math.abs(distance));
    }
  }

  static class SingleEncoding implements Encoding {
    private final MyList<Integer> elements;

    private SingleEncoding(MyList<Integer> elements) {
      this.elements = elements;
    }

    public static SingleEncoding from(MyList<Integer> elements) {
      return new SingleEncoding(elements);
    }

    @Override
    public Pair<String, MyList<Integer>> encode() {
      final var result = String.valueOf(elements.head());
      final var next = elements.tail();

      return Pair.with(result, next);
    }
  }

  static class IdenticalEncoding implements Encoding {
    private final MyList<Integer> elements;

    private IdenticalEncoding(MyList<Integer> elements) {
      this.elements = elements;
    }

    public static IdenticalEncoding from(MyList<Integer> elements) {
      return new IdenticalEncoding(elements);
    }

    @Override
    public Pair<String, MyList<Integer>> encode() {
      final var encoded = format("{0}*{1}", elementsToEncode().head(), elementsToEncode().length());
      return Pair.with(encoded, furtherElements());
    }

    private MyList<Integer> elementsToEncode() {
      return elements.stream()
              .takeWhile(i -> i.equals(elements.head()))
              .collect(toMyList());
    }

    private MyList<Integer> furtherElements() {
      return elements.stream()
              .dropWhile(i -> i.equals(elements.head()))
              .collect(toMyList());
    }
  }

  interface Encoding {
    Pair<String, MyList<Integer>> encode();
  }

  static class MyList<E> {

    private final Object[] raw;

    public MyList(Object[] raw) {
      this.raw = raw;
    }

    public static <E> MyList<E> from(Collection<? extends E> raw) {
      return new MyList<E>(raw.toArray());
    }

    private static <E> MyList<E> from(Object[] raw) {
      return new MyList<E>(raw);
    }

    public static <E> MyList<E> create(MyList<E> r) {
      return new MyList<>(r.raw);
    }

    public static <E> MyList<E> create() {
      return new MyList<E>(new Object[]{});
    }

    public E head() {
      return raw(0);
    }

    public E last() {
      return raw(raw.length - 1);
    }

    public MyList<E> append(E item) {
      final var nextRaw = grow(1);

      System.arraycopy(raw, 0, nextRaw, 0, raw.length);
      nextRaw[raw.length] = item;

      return MyList.from(nextRaw);
    }

    public MyList<E> prepend(E item) {
      final var nextRaw = grow(1);

      System.arraycopy(raw, 0, nextRaw, 1, raw.length);
      nextRaw[0] = item;

      return MyList.from(nextRaw);
    }

    public MyList<Pair<E, E>> zip(MyList<E> toZip) {
      final var length = Math.min(raw.length, toZip.length());

      final var zipped = IntStream.range(0, length)
              .mapToObj(i -> Pair.with(raw(i), toZip.raw(i)))
              .toArray(Pair[]::new);

      return MyList.from(zipped);
    }

    public MyList<E> init() {
      final var init = Arrays.stream(raw).limit(this.raw.length - 1).toArray();
      return MyList.from(init);
    }

    public MyList<E> tail() {
      final var tail = Arrays.stream(this.raw).skip(1).toArray();
      return MyList.from(tail);
    }

    @SuppressWarnings("unchecked")
    public Stream<E> stream() {
      return (Stream<E>) Arrays.stream(this.raw);
    }

    public int length() {
      return raw.length;
    }

    public boolean isEmpty() {
      return this.length() == 0;
    }

    private Object[] grow(int length) {
      return new Object[raw.length + length];
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MyList<?> myList = (MyList<?>) o;
      return Arrays.equals(raw, myList.raw);
    }

    public MyList<E> appendAll(MyList<E> r) {
      final var nextRaw = grow(r.length());

      System.arraycopy(raw, 0, nextRaw, 0, raw.length);
      System.arraycopy(r.raw, 0, nextRaw, r.raw.length, r.length());

      return MyList.from(nextRaw);
    }

    @SuppressWarnings("unchecked")
    private E raw(int index) {
      return (E) raw[index];
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(raw);
    }

    @Override
    public String toString() {
      return "MyList{" +
              "raw=" + Arrays.toString(raw) +
              '}';
    }
  }

  static class MyListCollector<T> implements Collector<T, List<T>, MyList<T>> {

    public static <T> MyListCollector<T> toMyList() {
      return new MyListCollector<T>();
    }

    @Override
    public Supplier<List<T>> supplier() {
      return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
      return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
      return (l1, l2) -> {
        l1.addAll(l2);
        return l1;
      };
    }

    @Override
    public Function<List<T>, MyList<T>> finisher() {
      return MyList::from;
    }

    @Override
    public Set<Characteristics> characteristics() {
      return Set.of(Characteristics.UNORDERED);
    }
  }

  public static class Pair<L, R> {
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

    public boolean identical() {
      return left.equals(right);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pair<?, ?> pair = (Pair<?, ?>) o;
      return left.equals(pair.left) && right.equals(pair.right);
    }

    @Override
    public int hashCode() {
      return Objects.hash(left, right);
    }

    @Override
    public String toString() {
      return "Pair{" +
              "left=" + left +
              ", right=" + right +
              '}';
    }
  }
}
