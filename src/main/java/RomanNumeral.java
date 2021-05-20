import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.naturalOrder;

public class RomanNumeral {

  private static final Map<Integer, String> romanConversions = new HashMap<>();
  private static final Map<String, Integer> numeralConversions = new HashMap<>();


  static {
    romanConversions.put(1, "I");
    romanConversions.put(4, "IV");
    romanConversions.put(5, "V");
    romanConversions.put(9, "IX");
    romanConversions.put(10, "X");
    romanConversions.put(40, "XL");
    romanConversions.put(50, "L");
    romanConversions.put(90, "XC");
    romanConversions.put(100, "C");
    romanConversions.put(400, "CD");
    romanConversions.put(500, "D");
    romanConversions.put(900, "CM");
    romanConversions.put(1000, "M");
  }

  static {
    numeralConversions.put("I", 1);
    numeralConversions.put("V", 5);
    numeralConversions.put("X", 10);
    numeralConversions.put("L", 50);
    numeralConversions.put("C", 100);
    numeralConversions.put("D", 500);
    numeralConversions.put("M", 1000);
  }

  public static String toRoman(int input) {
    String roman = romanConversions.get(input);

    if (roman == null) {
      roman = "";
      while (input > 0) {
        int lookup = determineLookup(input);
        roman += romanConversions.get(lookup);
        input -= lookup;
      }
    }

    return roman;
  }

  private static int determineLookup(int n) {
    return romanConversions.keySet().stream()
            .filter(key -> key <= n)
            .max(naturalOrder())
            .orElse(1);
  }

  public static int fromRoman(String input) {
    int sum = 0;

    for (int i = 0; i < input.length(); i++) {
      char current = input.charAt(i);
      Integer currentNumeral = numeralConversions.get(String.valueOf(current));

      if (i + 1 < input.length()) {
        char next = input.charAt(i + 1);
        Integer nextNumeral = numeralConversions.get(String.valueOf(next));
        sum -= currentNumeral < nextNumeral ? currentNumeral : -currentNumeral;
      } else {
        sum += currentNumeral;
      }

    }

    return sum;
  }
}
