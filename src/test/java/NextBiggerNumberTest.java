import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberTest {
  public static long nextBiggerNumber(long n) {
    final var nAsString = String.valueOf(n);
    final var sorted = List.of(nAsString.split("")).stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.joining());

    if (Long.valueOf(n).equals(Long.valueOf(sorted))) {
      return -1;
    }

    return Long.parseLong(sorted);
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
    assertThat(nextBiggerNumber(33)).isEqualTo(-1);
    assertThat(nextBiggerNumber(14)).isEqualTo(41);
  }

  @Test
  void shouldCalculateForThreeDigetNumber() {
    assertThat(nextBiggerNumber(101)).isEqualTo(110);
    assertThat(nextBiggerNumber(110)).isEqualTo(-1);
    assertThat(nextBiggerNumber(120)).isEqualTo(210);
  }
}
