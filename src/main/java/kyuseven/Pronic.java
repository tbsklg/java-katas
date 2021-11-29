package kyuseven;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pronic {

  public static boolean isPronic(int pronic) {
    final var nextPronic = Stream
            .iterate(0, n -> n + 1)
            .map(n -> n * (n + 1))
            .dropWhile(n -> n < pronic) // available since Java 9
            .limit(1)
            .collect(Collectors.toList())
            .get(0);

    return nextPronic == pronic;
  }
}
