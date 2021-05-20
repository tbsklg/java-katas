import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class HumanReadableTimeTest {

  @ParameterizedTest
  @CsvSource({
          "0, 00:00:00",
          "1, 00:00:01",
          "2, 00:00:02",
          "10, 00:00:10",
          "60, 00:01:00",
          "120, 00:02:00",
          "3599, 00:59:59",
          "3600, 01:00:00",
          "86399, 23:59:59",
          "359999, 99:59:59",
  })
  void shouldMakeReadable(int seconds, String expected) {
    assertThat(HumanReadableTime.makeReadable(seconds)).isEqualTo(expected);
  }
}
