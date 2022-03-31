package kyufive;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class WeightSort {

  public static String orderWeight(String strng) {
    return stream(strng.split(" "))
            .filter(s -> !s.isBlank())
            .map(Weight::of)
            .sorted()
            .map(Weight::raw)
            .collect(joining(" "));
  }

  static class Weight implements Comparable<Weight> {

    public static final Comparator<Weight> WEIGHT_COMPARATOR =
            Comparator.comparingInt((Weight w) -> w.sumOfDigits)
                    .thenComparing(w -> w.raw);

    private final int sumOfDigits;
    private final String raw;

    private Weight(int sumOfDigits, String raw) {
      this.sumOfDigits = sumOfDigits;
      this.raw = raw;
    }

    public static Weight of(String raw) {
      final var sumOfDigits = sumOfDigits(raw);
      return new Weight(sumOfDigits, raw);
    }

    private static int sumOfDigits(String from) {
      return from.chars()
              .map(Character::getNumericValue)
              .reduce(0, Integer::sum);
    }

    public String raw() {
      return this.raw;
    }

    @Override
    public int compareTo(Weight other) {
      return WEIGHT_COMPARATOR
              .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Weight weight = (Weight) o;
      return sumOfDigits == weight.sumOfDigits && raw.equals(weight.raw);
    }

    @Override
    public int hashCode() {
      return Objects.hash(sumOfDigits, raw);
    }

    @Override
    public String toString() {
      return "Weight{" +
              "sumOfDigits=" + sumOfDigits +
              ", raw='" + raw + '\'' +
              '}';
    }
  }
}
