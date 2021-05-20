import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Character.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * [number][op][number]=[number]
 */
public class FindTheUnknownDigitTest {

  @Test
  void shouldSolveResultForAddition() {
    assertThat(solveExpression("1+1=?")).isEqualTo(2);
    assertThat(solveExpression("2+1=?")).isEqualTo(3);
  }

  private int solveExpression(String expression) {
    final var op1 = getNumericValue(expression.charAt(0));
    final var op2 = getNumericValue(expression.charAt(expression.indexOf("=") - 1));

    return op1 + op2;
  }
}
