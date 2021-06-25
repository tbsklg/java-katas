package humanReadable;

import java.text.MessageFormat;

public class Year implements Format {
  private final static String YEAR = "year";

  private final int value;

  private Year(int value) {
    this.value = value;
  }

  public static Year year(int seconds) {
    return new Year(seconds);
  }

  public int getValue() {
    return value;
  }

  @Override
  public String asString() {
    final var second = this.value > 1 ? PluralFormat.plural(YEAR) : YEAR;
    return MessageFormat.format("{0} {1}", this.value, second);
  }
}
