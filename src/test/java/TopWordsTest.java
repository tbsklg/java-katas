import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class TopWordsTest {

    @Test
    void shouldReturnNoWord() {
        assertThat(top3("")).isEmpty();
    }

    @Test
    void shouldReturnSingleWord() {
        assertThat(top3("a")).containsExactly("a");
    }

    @Test
    void shouldReturnTwoWords() {
        assertThat(top3("a b")).containsExactly("a", "b");
    }

    @Test
    void shouldReturnTopWordsOnce() {
        assertThat(top3("a a a")).containsExactly("a");
    }

    @Test
    void shouldReturnTopWordsInLowerCase() {
        assertThat(top3("a B C")).containsExactly("a", "b", "c");
    }

    @Test
    void shouldOnlyReturnThreeWords() {
        assertThat(top3("a B c d")).containsExactly("a", "b", "c");
    }

    @Test
    void shouldIgnorePunctuation() {
        assertThat(top3("a. b, c")).containsExactly("a", "b", "c");
        assertThat(top3("  ...  ")).isEmpty();
        assertThat(top3("  //wont won't won't ")).containsExactly("won't", "wont");
    }

    @Test
    void shouldReturnTopWordsInLargeText() {
        assertThat(top3(String.join("\n", "In a village of La Mancha, the name of which I have no desire to call to",
                "mind, there lived not long since one of those gentlemen that keep a lance",
                "in the lance-rack, an old buckler, a lean hack, and a greyhound for",
                "coursing. An olla of rather more beef than mutton, a salad on most",
                "nights, scraps on Saturdays, lentils on Fridays, and a pigeon or so extra",
                "on Sundays, made away with three-quarters of his income."))).containsExactly("a", "of", "on");
    }

    @Test
    void shouldReturnTopWordsInRandomText() {
        assertThat(top3("QjF QjF-tOqKwhEyqD-ThvD stJNUEbW qxPK nwkEZov_CMWIcJEp;QjF/UhnbdMIJ" +
                " ThvD nwkEZov-QjF?CMWIcJEp?qxPK stJNUEbW,QjF ThvD,ThvD/tOqKwhEyqD:tOqKwhEyqD:ttYGX " +
                "CMWIcJEp,tOqKwhEyqD-ThvD?qxPK/stJNUEbW!qxPK qxPK tOqKwhEyqD CMWIcJEp stJNUEbW.QjF!tOqKwhEyqD " +
                "stJNUEbW stJNUEbW ThvD nwkEZov qxPK.QjF:tOqKwhEyqD stJNUEbW qxPK;ThvD/CMWIcJEp:ThvD " +
                "tOqKwhEyqD,QjF-tOqKwhEyqD CMWIcJEp ThvD ThvD,stJNUEbW.tOqKwhEyqD-tOqKwhEyqD " +
                "stJNUEbW_ttYGX,ThvD CMWIcJEp-UhnbdMIJ/ThvD ThvD?tOqKwhEyqD-ThvD qxPK?stJNUEbW_QjF ThvD:stJNUEbW:QjF " +
                "stJNUEbW tOqKwhEyqD QjF tOqKwhEyqD-qxPK-stJNUEbW.nwkEZov QjF ThvD CMWIcJEp " +
                "QjF?stJNUEbW_tOqKwhEyqD.ThvD stJNUEbW CMWIcJEp_stJNUEbW,stJNUEbW QjF " +
                "qxPK.tOqKwhEyqD?ThvD.tOqKwhEyqD CMWIcJEp!qxPK;QjF qxPK_CMWIcJEp ThvD:stJNUEbW,stJNUEbW!ttYGX.ThvD " +
                "stJNUEbW?stJNUEbW,QjF QjF qxPK CMWIcJEp stJNUEbW,tOqKwhEyqD CMWIcJEp " +
                "dfSyCF/CMWIcJEp nwkEZov;dfSyCF?QjF QjF/CMWIcJEp;ThvD?nwkEZov;nwkEZov!stJNUEbW?QjF " +
                "ThvD-qxPK CMWIcJEp QjF.QjF.ThvD stJNUEbW,UhnbdMIJ tOqKwhEyqD CMWIcJEp,dfSyCF:QjF qxPK " +
                "CMWIcJEp CMWIcJEp,qxPK/stJNUEbW/QjF/QjF UhnbdMIJ tOqKwhEyqD:stJNUEbW CMWIcJEp QjF,QjF " +
                "QjF!QjF-qxPK nwkEZov stJNUEbW,ThvD-dfSyCF tOqKwhEyqD ttYGX ")).containsExactly("qjf", "stjnuebw", "thvd");
    }

    @Test
    void shouldIgnoreOnlyApostrophe() {
        assertThat(top3("    '    ")).isEmpty();
        assertThat(top3("  '''  ")).isEmpty();
    }

    @Test
    void shouldSortWordsByFrequency() {
        assertThat(top3("b a a")).containsExactly("a", "b");
        assertThat(top3("a a a  b  c c  d d d d  e e e e e")).containsExactly("e", "d", "a");
    }

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


