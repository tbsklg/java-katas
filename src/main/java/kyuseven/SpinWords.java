package kyuseven;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

public class SpinWords {

    private static final String ON = " ";

    public String spinWords(String sentence) {
        return of(sentence.split(ON)).map(word -> {
            if (word.length() > 5) {
                return new StringBuilder(word).reverse();
            }
            return word;
        }).collect(joining(ON));
    }
}
