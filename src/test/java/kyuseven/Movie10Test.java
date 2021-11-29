package kyuseven;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class Movie10Test {

  @Test
  void shouldMoveByTen() {
    Assertions.assertThat(Movie10.move10("testcase")).isEqualTo("docdmkco");
    Assertions.assertThat(Movie10.move10("codewars")).isEqualTo("mynogkbc");
  }
}
