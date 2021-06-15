import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class RangeExtractionTest {

  @Test
  void integrationTests() {
    assertThat(rangeExtraction(new int[]{-6, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20})).isEqualTo("-6,-3-1,3-5,7-11,14,15,17-20");
  }

  @Test
  void shouldExtractForSingleInt() {
    assertThat(rangeExtraction(with(-6))).isEqualTo("-6");
    assertThat(rangeExtraction(with(5))).isEqualTo("5");
  }

  @Test
  void shouldExtractForTwoInts() {
    assertThat(rangeExtraction(with(-6, -3))).isEqualTo("-6,-3");
  }

  private static int[] with(int... n) {
    int[] result = new int[n.length];
    System.arraycopy(n, 0, result, 0, n.length);

    return result;
  }

  private static String rangeExtraction(int[] ints) {
    final var result = Arrays.stream(ints)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(","));

    return result;
  }
}
