package humanReadable;

import java.util.Stack;

public class Time {
  private final Year year;
  private final Day day;
  private final Hour hour;
  private final Minute minute;
  private final Second second;

  public static final class Builder {
    private Year year;
    private Day day;
    private Hour hour;
    private Minute minute;
    private Second second;

    public Builder withYear(Year year) {
      this.year = year;
      return this;
    }

    public Builder withDay(Day day) {
      this.day = day;
      return this;
    }

    public Builder withHour(Hour hour) {
      this.hour = hour;
      return this;
    }

    public Builder withMinute(Minute minute) {
      this.minute = minute;
      return this;
    }

    public Builder withSecond(Second second) {
      this.second = second;
      return this;
    }

    public Time create() {
      return new Time(year, day, hour, minute, second);
    }
  }

  private Time(Year year, Day day, Hour hour, Minute minute, Second second) {
    this.year = year;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
    this.second = second;
  }

  public String inHumanReadableFormat() {
    final var hasYear = year.getValue() != 0;
    final var hasDay = day.getValue() != 0;
    final var hasHour = hour.getValue() != 0;
    final var hasMinute = minute.getValue() != 0;
    final var hasSecond = second.getValue() != 0;

    final var stack = new Stack<Format>();

    if (hasSecond) {
      stack.push(second);
    }

    if (hasMinute) {
      stack.push(minute);
    }

    if (hasHour) {
      stack.push(hour);
    }

    if (hasDay) {
      stack.push(day);
    }

    if (hasYear) {
      stack.push(year);
    }

    if (stack.isEmpty()) {
      return "now";
    }

    final var comma = ", ";
    final var and = " and ";
    final var humanReadableFormat = new StringBuilder();
    while (!stack.isEmpty()) {
      final var current = stack.pop();
      humanReadableFormat.append(current.asString());

      if (stack.size() == 1) {
        humanReadableFormat.append(and);
      } else {
        if (stack.size() != 0) {
          humanReadableFormat.append(comma);
        }
      }
    }

    return humanReadableFormat.toString();
  }
}
