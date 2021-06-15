import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberTest {
  public static long nextBiggerNumber(long n) {
    final var digits = Number.toIntArray(n);
    final var nextBiggerNumber = Number.parseLong(calculateNextBiggerNumber(digits));

    if (nextBiggerNumber.equals(n)) {
      return -1;
    }

    return nextBiggerNumber;
  }

  private static int[] calculateNextBiggerNumber(int[] a) {
    final var copy = new int[a.length];
    System.arraycopy(a, 0, copy, 0, a.length);

    final var swapIndex = Number.getSwapIndex(copy);
    final var indexToSwap = Number.getIndexOfNextGreatest(copy, swapIndex);
    Number.swap(copy, indexToSwap, swapIndex);
    Number.sort(copy, swapIndex);

    return copy;
  }

  private static class Number {

    private static void swap(int[] a, int i, int j) {
      int swap = a[i];
      a[i] = a[j];
      a[j] = swap;
    }

    private static int getSwapIndex(int[] a) {
      var currentIndex = a.length - 1;
      for (int i = a.length - 1; i > 0; i--) {
        if (a[i - 1] < a[i]) {
          currentIndex = i - 1;
          break;
        }
      }
      return currentIndex;
    }

    private static int getIndexOfNextGreatest(int[] a, int index) {
      final var nextBiggest = a[index];

      var minDelta = Integer.MAX_VALUE;
      var currentIndex = a.length - 1;
      for (int i = index + 1; i < a.length; i++) {
        final var currentDiff = a[i] - nextBiggest;
        if (currentDiff < minDelta && currentDiff > 0) {
          minDelta = currentDiff;
          currentIndex = i;
        }
      }
      return currentIndex;
    }

    private static int[] toIntArray(long n) {
      return String.valueOf(n).chars()
              .map(c -> c - '0')
              .toArray();
    }

    private static Long parseLong(int[] a) {
      return Long.parseLong(Arrays.stream(a)
              .mapToObj(String::valueOf)
              .collect(Collectors.joining()));
    }

    private static void sort(int[] a, int index) {
      Arrays.sort(a, index + 1, a.length);
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
    assertThat(nextBiggerNumber(1090879862)).isEqualTo(1090882679);
    assertThat(nextBiggerNumber(1805583884)).isEqualTo(1805584388);
  }
}
