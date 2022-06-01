package kyufive;

import java.util.Arrays;
import java.util.StringJoiner;

public class Encoder {
  public String compress(int[] raw) {
    return compress(raw, new StringJoiner(","));
  }

  private String compress(int[] raw, StringJoiner join) {
    if (raw.length == 0) {
      return join.toString();
    }

    if (raw.length == 1) {
      join.add(String.valueOf(raw[0]));
      return compress(new int[]{}, join);
    }

    final var head = raw[0];
    final var next = raw[1];

    if (head == next) {
      final var count = Arrays.stream(raw).takeWhile(i -> i == head).toArray().length;
      final var tail = Arrays.stream(raw).dropWhile(i -> i == head).toArray();

      join.add(String.valueOf(head).concat("*").concat(String.valueOf(count)));

      return compress(tail, join);
    }

    if (raw.length >= 3) {
      final var third = raw[2];
      if (head + 1 == next && next + 1 == third) {
        join.add("");
      }


      // interval
    }

    final var arr = new int[raw.length - 1];
    System.arraycopy(raw, 1, arr, 0, raw.length - 1);
    join.add(String.valueOf(head));

    return compress(arr, join);
  }
}
