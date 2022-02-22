package kyufive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MaxSubarraySumTest {

  @Test
  public void testEmptyArray() throws Exception {
    Assertions.assertThat(MaxSubarraySum.sequence(new int[]{})).isEqualTo(0);
  }

  @Test
  public void testExampleArray() throws Exception {
    Assertions.assertThat(
            MaxSubarraySum.sequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})).isEqualTo(6);
  }
}
