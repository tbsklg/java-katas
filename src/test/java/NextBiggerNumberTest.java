import org.junit.jupiter.api.Test;

import static java.lang.Integer.*;
import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberTest {
  public static long nextBiggerNumber(long n) {
    final var nAsString = String.valueOf(n);

    if (nAsString.length() == 2) {
      final var first = parseInt(nAsString.substring(0, 1));
      final var second = parseInt(nAsString.substring(1));

      if (first < second) {
        return swap(first, second);
      }
    }

    return -1;
  }

  private static long swap(long first, long second) {
    final var firstAsString = String.valueOf(first);
    final var secondAsString = String.valueOf(second);

    return parseLong(secondAsString.concat(firstAsString));
  }

  @Test
  void shouldCalculateForSingleDigitNumber() {
    assertThat(nextBiggerNumber(1)).isEqualTo(-1);
  }

  @Test
  void shouldCalculateForTwoDigitNumber() {
    assertThat(nextBiggerNumber(12)).isEqualTo(21);
    assertThat(nextBiggerNumber(21)).isEqualTo(-1);
  }
}
