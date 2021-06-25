package humanReadable;

import java.util.Map;

public class PluralFormat {
  private static final Map<String, String> asPlural = Map.of(
          "second", "seconds",
          "minute", "minutes",
          "hour", "hours",
          "day", "days",
          "year", "years"
  );

  public static String plural(String singular) {
    return asPlural.get(singular);
  }
}
