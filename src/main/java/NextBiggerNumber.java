import java.util.Arrays;
import java.util.stream.Collectors;

public class NextBiggerNumber {
  public static long nextBiggerNumber(long n) {
    final var digits = Number.toIntArray(n);
    final var nextBiggerNumber = Number.parseLong(calculateNextBiggerNumber(digits));

    if (nextBiggerNumber.equals(n)) {
      return -1;
    }

    return nextBiggerNumber;
  }

  static int[] calculateNextBiggerNumber(int[] a) {
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
}
