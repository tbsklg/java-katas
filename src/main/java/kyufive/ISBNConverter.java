package kyufive;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ISBNConverter {

  public static String isbnConverter(String isbn) {
    return Convert
            .from(ISBN.from(isbn))
            .toISBN13()
            .asString();
  }

  static class ISBN {

    private final String raw;
    private final Numbers numbers;

    private ISBN(String raw, Numbers numbers) {
      this.raw = raw;
      this.numbers = numbers;
    }

    public static ISBN from(String raw) {
      return new ISBN(raw, numbersFrom(raw));
    }

    public static ISBN from(Numbers numbers) {
      final var raw = asString(numbers);

      return new ISBN(raw, numbers);
    }

    private static String asString(Numbers numbers) {
      return numbers
              .stream()
              .map(Number::asString)
              .collect(joining("-"));
    }

    private static Numbers numbersFrom(String raw) {
      return Numbers.from(
              Arrays.stream(raw.split("-"))
                      .map(String::valueOf)
                      .map(Number::from)
                      .collect(toList())
      );
    }

    public String asString() {
      return this.raw;
    }

    public Numbers numbers() {
      return this.numbers;
    }
  }

  static class Convert {

    private final Numbers numbers;

    private Convert(Numbers numbers) {
      this.numbers = numbers;
    }

    public static Convert from(ISBN isbn) {
      return new Convert(isbn.numbers());
    }

    public ISBN toISBN13() {
      return addPrefix()
              .appendChecksum()
              .toIsbn();
    }

    private Convert addPrefix() {
      final var prefix = Number.from(978);
      final var numbers = this.numbers.prepend(prefix);

      return new Convert(numbers);
    }

    private Convert appendChecksum() {
      final var checksum = Number.from(Checksum.from(this.numbers.init()));
      final var numbers = this.numbers.init().append(checksum);

      return new Convert(numbers);
    }


    private ISBN toIsbn() {
      return ISBN.from(this.numbers);
    }
  }

  static class Checksum {
    private final List<Integer> digits;

    private Checksum(List<Integer> digits) {
      this.digits = digits;
    }

    public static int from(Numbers numbers) {
      List<Integer> digits = Stream.of(numbers)
              .flatMap(Numbers::stream)
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
      return raw
              .chars()
              .filter(Character::isDigit)
              .mapToObj(Character::toString)
              .map(Integer::valueOf)
              .collect(toList());
    }

    public String asString() {
      return this.stream()
              .map(String::valueOf)
              .collect(joining());
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
      final var next = Stream
              .concat(this.stream(), Stream.of(n))
              .collect(toList());

      return Numbers.from(next);
    }

    public Numbers prepend(Number n) {
      final var next = Stream
              .concat(Stream.of(n), this.stream())
              .collect(toList());

      return Numbers.from(next);
    }

    public Numbers init() {
      final var init = this.stream()
              .limit(this.numbers.size() - 1)
              .collect(toList());

      return new Numbers(init);
    }

    public Stream<Number> stream() {
      return this.numbers.stream();
    }
  }
}
