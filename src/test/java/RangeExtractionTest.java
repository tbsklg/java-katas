import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

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
    assertThat(rangeExtraction(with(-6, 0))).isEqualTo("-6,0");
    assertThat(rangeExtraction(with(60, 0))).isEqualTo("60,0");
  }

  @Test
  void shouldExtractForThreeIntsInARow() {
    assertThat(rangeExtraction(with(-3, -2, -1))).isEqualTo("-3--1");
  }

  private static int[] with(int... n) {
    int[] result = new int[n.length];
    System.arraycopy(n, 0, result, 0, n.length);

    return result;
  }

  private static String rangeExtraction(int[] arr) {
    final var result = new StringJoiner(",");

    int i1 = 0;
    int i2 = 0;

    while (i1 < arr.length) {
      do {
        i2++;
      } while (i2 < arr.length && arr[i2 - 1] + 1 == arr[i2]);
      if (i2 - i1 > 2) {
        final var minus = new StringJoiner("-");
        minus.add(String.valueOf(arr[i1]));
        minus.add(String.valueOf(arr[i2 - 1]));
        result.merge(minus);
        i1 = i2;
      } else {
        for (; i1 < i2; i1++)
          result.add(String.valueOf(arr[i1]));
        }
    }

    return result.toString();
  }


  private static void separate(StringBuilder result, String s, int i1) {
    result.append(s);
    result.append(i1);
  }
}
