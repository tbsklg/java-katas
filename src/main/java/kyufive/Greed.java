package kyufive;

import static java.util.Arrays.stream;

public class Greed {

  public static int greedy(int[] dice) {
    if (dice.length == 0) return 0;

    final var head = dice[0];
    final var matches = stream(dice).filter(roll -> roll == head).count();

    if (matches == 3) return three(head) + greedy(
            stream(dice).filter(roll -> roll != head).toArray()
    );

    return one(head) + greedy(stream(dice).skip(1).toArray());
  }

  private static int three(int n) {
    if (n == 1) return 1000;
    return n * 100;
  }

  private static int one(int n) {
    if (n == 1) return 100;
    if (n == 5) return 50;
    return 0;
  }
}
