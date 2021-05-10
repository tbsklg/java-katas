import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class TopWords {
    public static List<String> top3(String s) {
        if (s.isBlank() || s.isEmpty()) {
            return new ArrayList<>();
        }

        final Function<String, String> stripInput =
                replaceLineBreaks()
                        .andThen(replacePunctuations())
                        .andThen(removeApostrophesOnly());

        String stripped = stripInput.apply(s);

        return Arrays.stream(stripped.split(" "))
                .map(String::toLowerCase)
                .filter(isNotEmpty())
                .filter(containsPunctuation())
                .collect(groupingBy(w -> w, counting()))
                .entrySet()
                .stream()
                .sorted(comparing(Map.Entry<String, Long>::getValue).reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    private static Function<String, String> replaceLineBreaks() {
        return s -> s.trim().replace("\n", " ");
    }

    private static Function<String, String> replacePunctuations() {
        return s -> s.replaceAll("[!,-.:;?_/]", " ");
    }

    private static Predicate<String> containsPunctuation() {
        return w -> !w.matches("!\"#\\$%&\\(\\)*+,-./:;<=>?@\\[]\\^_`\\{\\|}~");
    }

    private static Predicate<String> isNotEmpty() {
        return w -> !w.isEmpty();
    }

    private static Function<String, String> removeApostrophesOnly() {
        return s -> s.replaceAll("^[\\p{Punct}]+", " ");
    }
}
