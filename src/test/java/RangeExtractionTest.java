import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RangeExtractionTest {

  @Test
  void integrationTests() {
    assertThat(RangeExtraction.rangeExtraction(new int[]{-6, -3, -2, -1, 0, 1, 3, 4, 5, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 20})).isEqualTo("-6,-3-1,3-5,7-11,14,15,17-20");
  }

  @Test
  void shouldExtractForSingleInt() {
    assertThat(RangeExtraction.rangeExtraction(with(-6))).isEqualTo("-6");
    assertThat(RangeExtraction.rangeExtraction(with(5))).isEqualTo("5");
  }

  @Test
  void shouldExtractForTwoInts() {
    assertThat(RangeExtraction.rangeExtraction(with(-6, -3))).isEqualTo("-6,-3");
    assertThat(RangeExtraction.rangeExtraction(with(-6, 0))).isEqualTo("-6,0");
    assertThat(RangeExtraction.rangeExtraction(with(60, 0))).isEqualTo("60,0");
  }

  @Test
  void shouldExtractForThreeIntsInARow() {
    assertThat(RangeExtraction.rangeExtraction(with(-3, -2, -1))).isEqualTo("-3--1");
  }

  private static int[] with(int... n) {
    int[] result = new int[n.length];
    System.arraycopy(n, 0, result, 0, n.length);

    return result;
  }
}
