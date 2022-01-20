package kyuseven;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

public class SpinWords {

    private static final String ON = " ";

    public String spinWords(String sentence) {
        return of(sentence.split(ON)).map(word -> {
            if (word.length() > 5) {
                return reverse(word);
            }
            return word;
        }).collect(joining(ON));
    }

    private String reverse(String word) {
        if (word.isEmpty()) {
            return "";
        }

        final var last = word.substring(word.length() - 1);
        final var init = word.substring(0, word.length() - 1);

        return last + reverse(init);
    }
}
