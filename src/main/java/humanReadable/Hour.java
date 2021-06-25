package humanReadable;

import java.text.MessageFormat;

public class Hour implements Format {
  private final static String HOUR = "hour";

  private final int value;

  private Hour(int value) {
    this.value = value;
  }

  public static Hour hour(int seconds) {
    return new Hour(seconds);
  }

  public int getValue() {
    return value;
  }

  public String asString() {
    final var hour = this.value > 1 ? PluralFormat.plural(HOUR) : HOUR;
    return MessageFormat.format("{0} {1}", this.value, hour);
  }
}
