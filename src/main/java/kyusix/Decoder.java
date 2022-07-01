package kyusix;

import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;
import static java.lang.Math.abs;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;
import static java.util.stream.IntStream.generate;
import static java.util.stream.IntStream.iterate;

public class Decoder {

  public static final String DELIMITER = ",";

  public int[] uncompress(final String music) {
    return stream(music.split(DELIMITER))
            .flatMap(this::decode)
            .mapToInt(i -> i)
            .toArray();
  }

  private Stream<Integer> decode(String e) {
    final var isNumber = e.matches("^-?\\d*$");
    if (isNumber) return Stream.of(valueOf(e));

    final var isIdentical = e.matches("^-?\\d*[*]-?\\d*$");
    if (isIdentical) return decodeIdentical(e);

    final var isConsecutive = e.matches("^-?\\d*[-]-?\\d*$");
    if (isConsecutive) return decodeConsecutive(e);

    final var isInterval = e.matches("^-?\\d*[-]-?\\d*[/]-?\\d*$");
    if (isInterval) return decodeInterval(e);

    throw new UnsupportedOperationException(format("Decoding operation for input ''{0}'' is not supported", e));
  }

  private Stream<Integer> decodeInterval(String e) {
    final var splitOnMinus = e.split("[-]", 2);
    final var from = parseInt(splitOnMinus[0]);

    final var splitOnForwardSlash = splitOnMinus[1].split("[/]");
    final var to = parseInt(splitOnForwardSlash[0]);
    final var interval = parseInt(splitOnForwardSlash[1]);

    return iterate(from, from <= to ? i -> i + interval : i -> i - interval)
            .limit(abs((from - to) / interval) + 1)
            .boxed();
  }

  private Stream<Integer> decodeConsecutive(String e) {
    final var splitted = e.split("[-]", 2);
    final var from = parseInt(splitted[0]);
    final var to = parseInt(splitted[1]);

    return iterate(from, from <= to ? i -> i + 1 : i -> i - 1)
            .limit(abs(from - to) + 1)
            .boxed();
  }

  private Stream<Integer> decodeIdentical(String e) {
    final var splitted = e.split("[*]");
    final var number = parseInt(splitted[0]);
    final var times = parseInt(splitted[1]);

    return generate(() -> number)
            .limit(times)
            .boxed();
  }
}
