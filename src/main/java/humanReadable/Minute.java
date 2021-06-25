package humanReadable;

import java.text.MessageFormat;

public class Minute implements Format {
  private final int value;
  private final static String MINUTE = "minute";

  private final static String MINUTES = MINUTE.concat("s");

  private Minute(int value) {
    this.value = value;
  }

  public static Minute minute(int minutes) {
    return new Minute(minutes);
  }

  public int getValue() {
    return value;
  }

  public String asString() {
    final var minute = this.value > 1 ? PluralFormat.plural(MINUTE) : MINUTE;
    return MessageFormat.format("{0} {1}", this.value, minute);
  }
}
