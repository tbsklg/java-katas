package kyuseven;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
https://www.codewars.com/kata/59a96d71dbe3b06c0200009c/solutions/java/all/best_practice
 */
public class BuildASquare {

  public static String generateShape(int n) {
    final var row = Stream.iterate(0, x -> x + 1)
            .limit(n)
            .map(str -> "+".repeat(n))
            .collect(Collectors.toUnmodifiableList());

    return String.join("\n", row);
//    return ("+".repeat(n) + "\n").repeat(n).trim();
  }
}
