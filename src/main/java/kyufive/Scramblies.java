package kyufive;

import java.util.HashMap;

public class Scramblies {

  public static boolean scramble(String str1, String str2) {

    final var d1 = new HashMap<Character, Integer>();
    for (char c : str1.toCharArray()) {
      d1.merge(c, 1, Integer::sum);
    }

    for (char c : str2.toCharArray()) {
      final var count = d1.get(c);
      if (count == null || count == 0) {
        return false;
      }

      d1.merge(c, -1, Integer::sum);
    }

    return true;
  }
}
