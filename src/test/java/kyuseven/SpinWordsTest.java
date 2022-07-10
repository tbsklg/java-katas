package kyuseven;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SpinWordsTest {

  @Test
  public void test() {
    assertThat("emocleW").isEqualTo(new SpinWords().spinWords("Welcome"));
    assertThat("Hey wollef sroirraw").isEqualTo(new SpinWords().spinWords("Hey fellow warriors"));
  }
}