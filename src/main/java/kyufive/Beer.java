package kyufive;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Beer {

  public static int beeramid(double bonus, double pricePerCan) {
    final var current = new AtomicInteger(0);

    return IntStream.iterate(1, i -> i + 1)
            .mapToDouble(i -> Math.pow(i, 2))
            .mapToInt(i -> (int) i)
            .map(current::addAndGet)
            .takeWhile(i -> i <= Math.floor(bonus / pricePerCan))
            .toArray().length;
  }
}


