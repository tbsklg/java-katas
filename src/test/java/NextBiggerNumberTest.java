import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberTest {
  private static class Numbers {

    private static int[] toIntArray(long n) {
      final var nAsString = String.valueOf(n);
      return nAsString.chars()
              .map(c -> c - '0')
              .toArray();
    }

    private static Long parseLong(int [] a) {
      return Long.parseLong(Arrays.stream(a)
              .mapToObj(String::valueOf)
              .collect(Collectors.joining()));
    }
  }

  public static long nextBiggerNumber(long n) {
    final var digits = Numbers.toIntArray(n);

    nextBiggerNumber(digits);

    final var result = Numbers.parseLong(digits);

    if (result.equals(n)) {
      return -1;
    }

    return result;
  }

  private static void nextBiggerNumber(int[] a) {
    final var N = a.length;

    var swapAt = N - 1;
    for (int i = N - 1; i > 0; i--) {
      if (less(a[i - 1], a[i])) {
        swapAt = i - 1;
        break;
      }
    }

    final var nextBiggest = a[swapAt];
    var currentDiff = Integer.MAX_VALUE;
    var indexToSwap = N - 1;
    for (int i = swapAt + 1; i < N; i++) {
      final var appleCake = a[i] - nextBiggest;
      if (appleCake < currentDiff && appleCake > 0) {
        currentDiff = appleCake;
        indexToSwap = i;
      }
    }

    exch(a, indexToSwap, swapAt);

    for (int i = swapAt + 1; i < N; i++) {
      int min = i;
      for (int j = i + 1; j < N; j++) {
        if (less(a[j], a[min])) {
          min = j;
        }
      }
      exch(a, i, min);
    }
  }

  private static boolean less(int a, int b) {
    return a < b;
  }

  private static void exch(int[] a, int i, int j) {
    int swap = a[i];
    a[i] = a[j];
    a[j] = swap;
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
    assertThat(nextBiggerNumber(120)).isEqualTo(201);
    assertThat(nextBiggerNumber(102)).isEqualTo(120);
    assertThat(nextBiggerNumber(513)).isEqualTo(531);
    assertThat(nextBiggerNumber(414)).isEqualTo(441);
    assertThat(nextBiggerNumber(144)).isEqualTo(414);
  }

  @Test
  void shouldCalculateForFourDigetNumber() {
    assertThat(nextBiggerNumber(2017)).isEqualTo(2071);
  }

  @Test
  void shouldCalculateForFiveDigetNumber() {
    assertThat(nextBiggerNumber(10990)).isEqualTo(19009);
  }

  @Test
  void shouldCalculateForBigNumber() {
//    Assertions.assertThat(nextBiggerNumber(1090879862)).isEqualTo(1090882679);
    assertThat(nextBiggerNumber(1805583884)).isEqualTo(1805584388);
  }
}
