package kyufive;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class PigLatin {
  public static String pigIt(String str) {
    return words(str)
            .map(PigLatin::toPigLatin)
            .collect(unwords());
  }

  private static Stream<String> words(String str) {
    return stream(str.split(" "));
  }

  private static Collector<CharSequence, ?, String> unwords () {
    return Collectors.joining(" ");
  }

  private static String toPigLatin(String str) {
    if (str.equals("!") || str.equals("?")) {
      return str;
    }


    final var head = str.substring(0, 1);
    final var tail = str.substring(1);
    final var ay = "ay";

    return String.join("", tail, head, ay);
  }
}
