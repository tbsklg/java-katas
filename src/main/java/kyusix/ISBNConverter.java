package kyusix;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ISBNConverter {

  public static String isbnConverter(String isbn) {
    return ISBN10.from(isbn).toIsbn13().asString();
  }

  static class ISBN10 {

    private final Numbers numbers;

    private ISBN10(Numbers numbers) {
      this.numbers = numbers;
    }

    public static ISBN10 from(String raw) {
      return new ISBN10(numbersFrom(raw));
    }

    public static ISBN10 from(Numbers numbers) {
      return new ISBN10(numbers);
    }

    private static Numbers numbersFrom(String raw) {
      return Numbers.from(Arrays.stream(raw.split("-")).map(String::valueOf).map(Number::from).collect(toList()));
    }

    public ISBN13 toIsbn13() {
      final var withPrefix = this.numbers.prepend(Number.from(978));
      final var checksum = Number.from(Checksum.from(withPrefix.init()));

      final var numbers = withPrefix.init().append(checksum);
      return ISBN13.from(numbers);
    }
  }

  static class ISBN13 {

    private final Numbers numbers;

    private ISBN13(Numbers numbers) {
      this.numbers = numbers;
    }

    public static ISBN13 from(Numbers numbers) {
      return new ISBN13(numbers);
    }

    private String asString() {
      return this.numbers
              .stream()
              .map(Number::asString)
              .collect(joining("-"));
    }
  }

  static class Checksum {
    private final List<Integer> digits;

    private Checksum(List<Integer> digits) {
      this.digits = digits;
    }

    public static int from(Numbers numbers) {
      List<Integer> digits = numbers.stream()
              .flatMap(Number::stream)
              .collect(toList());

      return new Checksum(digits).retrieve();
    }

    private int retrieve() {
      final var checksum = IntStream
              .range(0, digits.size())
              .map(this::oneOrThree)
              .reduce(0, Integer::sum) % 10;

      if (checksum != 0) return 10 - checksum;

      return checksum;
    }

    private int oneOrThree(int index) {
      final var digit = digits.get(index);

      if (odd(index)) return digit * 3;

      return digit;
    }

    private boolean odd(int i) {
      return i % 2 != 0;
    }
  }

  static class Number {
    private final List<Integer> digits;

    private Number(List<Integer> digits) {
      this.digits = digits;
    }

    public static Number from(String raw) {
      return new Number(extractDigits(raw));
    }

    public static Number from(Integer raw) {
      return new Number(extractDigits(String.valueOf(raw)));
    }

    private static List<Integer> extractDigits(String raw) {
      return raw.chars().filter(Character::isDigit).mapToObj(Character::toString).map(Integer::valueOf).collect(toList());
    }

    public String asString() {
      return this.stream().map(String::valueOf).collect(joining());
    }

    public Stream<Integer> stream() {
      return this.digits.stream();
    }
  }

  static class Numbers {
    private final List<Number> numbers;

    private Numbers(List<Number> numbers) {
      this.numbers = numbers;
    }

    public static Numbers from(List<Number> numbers) {
      return new Numbers(numbers);
    }

    public Numbers append(Number n) {
      final var next = Stream.concat(this.stream(), Stream.of(n)).collect(toList());

      return Numbers.from(next);
    }

    public Numbers prepend(Number n) {
      final var next = Stream.concat(Stream.of(n), this.stream()).collect(toList());

      return Numbers.from(next);
    }

    public Numbers init() {
      final var init = this.stream().limit(this.numbers.size() - 1).collect(toList());

      return new Numbers(init);
    }

    public Stream<Number> stream() {
      return this.numbers.stream();
    }
  }
}
