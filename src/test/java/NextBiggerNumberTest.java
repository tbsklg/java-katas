import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberTest {
  public static long nextBiggerNumber(long n) {
    final var nAsString = String.valueOf(n);
    final var digits = nAsString.chars()
            .mapToObj(Digit::valueOf)
            .toArray(Digit[]::new);

    nextBiggerNumber(digits);

    final var result = Arrays.stream(digits)
            .map(d -> Character.toString(d.getValue()))
            .collect(Collectors.joining());

    if (result.equals(String.valueOf(n))) {
      return -1;
    }

    return Long.parseLong(result);
  }

  private static void nextBiggerNumber(Digit[] a) {
    int N = a.length;

    for (int i = N - 1; i > 0; i--) {
      if (less(a[i - 1], a[i])) {
        exch(a, i, i - 1);
        break;
      }
    }
  }

  private static boolean less(Digit a, Digit b) {
    return a.compareTo(b) < 0;
  }

  private static void exch(Digit[] a, int i, int j) {
    Digit swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private static class Digit implements Comparable<Digit> {

    private final int value;

    private Digit(int value) {
      this.value = value;
    }

    private static Digit valueOf(int value) {
      return new Digit(value);
    }

    public int getValue() {
      return value;
    }

    @Override
    public int compareTo(@NotNull Digit o) {
      return Integer.compare(this.value, o.value);
    }

    @Override
    public String toString() {
      return "Digit{" +
              "value=" + value +
              '}';
    }
  }


  @Test
  void shouldCalculateForSingleDigitNumber() {
    assertThat(nextBiggerNumber(1)).isEqualTo(-1);
  }

  @Test
  void shouldCalculateForTwoDigitNumber() {
    assertThat(nextBiggerNumber(12)).isEqualTo(21);
    assertThat(nextBiggerNumber(21)).isEqualTo(-1);
    assertThat(nextBiggerNumber(33)).isEqualTo(-1);
    assertThat(nextBiggerNumber(14)).isEqualTo(41);
  }

  @Test
  void shouldCalculateForThreeDigetNumber() {
    assertThat(nextBiggerNumber(101)).isEqualTo(110);
    assertThat(nextBiggerNumber(110)).isEqualTo(-1);
    assertThat(nextBiggerNumber(120)).isEqualTo(210);
    assertThat(nextBiggerNumber(102)).isEqualTo(120);
    assertThat(nextBiggerNumber(513)).isEqualTo(531);
    assertThat(nextBiggerNumber(414)).isEqualTo(441);
    assertThat(nextBiggerNumber(144)).isEqualTo(414);
  }
}
