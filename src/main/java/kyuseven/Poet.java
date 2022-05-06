package kyuseven;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Poet {
  public static int[] pendulum(final int[] values) {
    Arrays.sort(values);

    IntPredicate isEven = i -> i % 2 == 0;
    IntStream evens = IntStream.iterate(values.length - 1, i -> i >= 0, i -> i - 1)
            .filter(isEven)
            .map(i -> values[i]);

    IntPredicate isOdd = i -> i % 2 != 0;
    IntStream odds = IntStream.range(0, values.length)
            .filter(isOdd)
            .map(i -> values[i]);

    return IntStream.concat(evens, odds).toArray();
  }
}
