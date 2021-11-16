package kyuseven;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Movie10 {

  public static String move10(String move) {
    return Stream.of(move.chars())
            .map(c -> move(String.valueOf(c)))
            .collect(Collectors.joining());
  }

  private static String move(String c) {
    return Stream.iterate(97, n -> n + 1)
            .map(x -> {
              System.out.println(x);
              return String.valueOf(x);
            })
            .dropWhile(x -> !x.equals(String.valueOf(99)))
            .limit(11)
            .collect(Collectors.toList())
            .get(11);
  }
}
