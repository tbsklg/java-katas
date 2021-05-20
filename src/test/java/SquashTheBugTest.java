import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SquashTheBugTest {

  @Test
  public void shouldReturnLengthOfLongestWord() {
    assertThat(SquashTheBug.findLongest("A")).isEqualTo(1);
    assertThat(SquashTheBug.findLongest("The")).isEqualTo(3);
    assertThat(SquashTheBug.findLongest("The quick")).isEqualTo(5);
    assertThat(SquashTheBug.findLongest("The quick white fox jumped around the massive dog")).isEqualTo(7);
    assertThat(SquashTheBug.findLongest("Take me to tinseltown with you")).isEqualTo(10);
    assertThat(SquashTheBug.findLongest("Sausage chops")).isEqualTo(7);
    assertThat(SquashTheBug.findLongest("Wind your body and wiggle your belly")).isEqualTo(6);
    assertThat(SquashTheBug.findLongest("Lets all go on holiday")).isEqualTo(7);
  }
}
