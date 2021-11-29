package kyuseven;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PronicTest {

  @ParameterizedTest
  @CsvSource({
          "0, True",
          "1, False",
          "2, True",
          "3, False",
          "4, False",
          "5, False",
          "6, True",
          "-3, False",
          "-27, False"
  })
  void isPronic(int pronic, boolean isPronic) {
    assertThat(Pronic.isPronic(pronic)).isEqualTo(isPronic);
  }
}
