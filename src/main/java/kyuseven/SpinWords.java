package kyuseven;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

public class SpinWords {

  public String spinWords(String sentence) {
    return of(sentence.split(" "))
            .map(word -> word.length() > 4 ? new StringBuilder(word).reverse() : word)
            .collect(joining(" "));
  }
}
