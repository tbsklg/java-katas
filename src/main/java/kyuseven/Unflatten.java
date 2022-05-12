package kyuseven;

import java.util.Arrays;

public class Unflatten {

  public static Object[] unflatten(int[] flatArray) {
    return unflatten(flatArray, new Object[]{});
  }

  private static Object[] unflatten(int[] flatArray, Object[] result) {
    if (flatArray.length == 0) {
      return result;
    }

    final var head = flatArray[0];
    final var nextResult = new Object[result.length + 1];

    if (head < 3) {
      final var next = drop(1, flatArray);
      System.arraycopy(result, 0, nextResult, 0, result.length);
      nextResult[result.length] = flatArray[0];

      return unflatten(next, nextResult);
    }

    final var next = drop(head, flatArray);
    System.arraycopy(result, 0, nextResult, 0, result.length);
    nextResult[result.length] = take(head, flatArray);

    return unflatten(next, nextResult);
  }

  private static int[] take(int n, int[] flatArray) {
    return Arrays.stream(flatArray)
            .limit(n)
            .toArray();
  }

  private static int[] drop(int n, int[] flatArray) {
    return Arrays.stream(flatArray)
            .skip(n)
            .toArray();
  }
}
