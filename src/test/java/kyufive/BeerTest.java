package kyufive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static kyufive.Beer.beeramid;
import static org.assertj.core.api.Assertions.assertThat;

class BeerTest {

  @ParameterizedTest
  @CsvSource({
          "9,2,1",
          "10,2,2",
          "11,2,2",
          "21,1.5,3",
          "454,5,5",
          "455,5,6",
          "4,4,1",
          "3,4,0",
          "0,4,0",
          "-1,4,0",
  })
  void shouldCalculateLevels(double bonus, double pricePerCan, int expectedLevel) {
    assertThat(beeramid(bonus, pricePerCan)).isEqualTo(expectedLevel);
  }
}