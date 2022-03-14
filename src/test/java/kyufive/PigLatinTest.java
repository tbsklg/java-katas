package kyufive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PigLatinTest {

  @Test
  public void FixedTests() {
    Assertions.assertThat(PigLatin.pigIt("Pig latin is cool")).isEqualTo("igPay atinlay siay oolcay");
    Assertions.assertThat(PigLatin.pigIt("This is my string")).isEqualTo("hisTay siay ymay tringsay");
  }
}