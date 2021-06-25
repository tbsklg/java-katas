import humanReadable.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;


public class HumanReadableDurationTest {

  public static String formatDuration(int seconds) {
    int yy = seconds / 31536000;
    int dd = seconds % 31536000 / 86400;
    int hh = seconds % 86400 / 3600;
    int mm = seconds % 3600 / 60;
    int ss = seconds % 60;

    final var time = new Time.Builder()
            .withYear(Year.year(yy))
            .withDay(Day.day(dd))
            .withHour(Hour.hour(hh))
            .withMinute(Minute.minute(mm))
            .withSecond(Second.second(ss))
            .create();

    return time.inHumanReadableFormat();
  }

  @CsvSource({
          "1, 1 second",
          "2, 2 seconds",
          "62, 1 minute and 2 seconds",
          "120, 2 minutes",
          "3600, 1 hour",
  })
  @ParameterizedTest
  void shouldFormatDuration(int actual, String expected) {
    assertThat(formatDuration(actual)).isEqualTo(expected);
  }

  @Test
  void integrationTests() {
    assertThat(formatDuration(7149637)).isEqualTo("82 days, 18 hours and 37 seconds");
    assertThat(formatDuration(15731080)).isEqualTo("182 days, 1 hour, 44 minutes and 40 seconds");
  }
}
