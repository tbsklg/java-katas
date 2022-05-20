package kyufive;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class CaesarTwo {

  public static List<String> encodeStr(String s, int shift) {
    final var encodedMessage = s.chars()
            .map(c -> shiftUp((char) c, shift))
            .mapToObj(Character::toString)
            .collect(joining());

    final var prefix = prefix(shift, s);

    return chunk(prefix + encodedMessage);
  }

  public static String decode(List<String> s) {
    final var firstChunk = s.get(0);
    final var places = rotate(firstChunk);

    final var encodedMessage = String.join("", s);
    final var chunked = chunk(encodedMessage);

    return chunked.stream()
            .flatMap(chunk -> chunk.chars()
                    .map(c -> shiftDown((char) c, places))
                    .mapToObj(Character::toString))
            .skip(2)
            .collect(joining());
  }

  public static char shiftUp(char from, int shift) {
    if (!Character.isAlphabetic(from)) {
      return from;
    }

    if (Character.isUpperCase(from)) {
      return (char) ((from + shift - 65) % 26 + 65);
    }

    if (Character.isLowerCase(from)) {
      return (char) ((from + shift - 97) % 26 + 97);
    }

    return from;
  }

  public static char shiftDown(char from, int shift) {
    if (!Character.isAlphabetic(from)) {
      return from;
    }

    if (Character.isUpperCase(from)) {
      return (char) (Math.floorMod(from - shift - 65, 26) + 65);
    }

    if (Character.isLowerCase(from)) {
      return (char) (Math.floorMod(from - shift - 97, 26) + 97);
    }

    return from;
  }

  public static String prefix(int shift, String message) {
    final var first = Character.toLowerCase(message.charAt(0));
    final var second = shiftUp(first, shift);

    return String.valueOf(first) + second;
  }

  public static int rotate(String message) {
    final var first = message.charAt(0);
    final var second = message.charAt(1);

    return "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
            .chars()
            .dropWhile(c -> c != first)
            .takeWhile(c -> c != second)
            .toArray()
            .length;
  }

  public static List<String> chunk(String message) {
    final var chunkSize = (int) Math.ceil(message.length() / 5.0);

    final var chunked = new ArrayList<String>();
    for (int i = 0; i < message.length(); i += chunkSize) {
      final var codePart = message.substring(i, Math.min(message.length(), i + chunkSize));
      chunked.add(codePart);
    }

    return chunked;
  }
}
