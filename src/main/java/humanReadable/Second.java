package humanReadable;

import java.text.MessageFormat;

public class Second implements Format {
  private final static String SECOND = "second";

  private final int value;

  private Second(int value) {
    this.value = value;
  }

  public static Second second(int seconds) {
    return new Second(seconds);
  }

  public int getValue() {
    return value;
  }

  @Override
  public String asString() {
    final var second = this.value > 1 ? PluralFormat.plural(SECOND) : SECOND;
    return MessageFormat.format("{0} {1}", this.value, second);
  }
}
