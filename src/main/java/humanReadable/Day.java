package humanReadable;

import java.text.MessageFormat;

public class Day implements Format {
  private final static String DAY = "day";

  private final int value;

  private Day(int value) {
    this.value = value;
  }

  public static Day day(int seconds) {
    return new Day(seconds);
  }

  public int getValue() {
    return this.value;
  }

  public String asString() {
    final var day = this.value > 1 ? PluralFormat.plural(DAY) : DAY;
    return MessageFormat.format("{0} {1}", this.value, day);
  }
}
