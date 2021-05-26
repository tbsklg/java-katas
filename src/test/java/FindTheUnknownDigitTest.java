import org.junit.jupiter.api.Test;

import java.util.function.IntBinaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * [number][op][number]=[number]
 * <p>
 * range of a number: -1000000 to 1000000
 * number never starts with 0
 * ? represents digit rune, never an operator
 * expression contains 1-n ?s
 * expression has exactly one operator
 * <p>
 * returns -1 if no digit works
 */
public class FindTheUnknownDigitTest {

  @Test
  void shouldSolveResultForAddition() {
    assertThat(solveExpression("1+1=?")).isEqualTo(2);
    assertThat(solveExpression("2+1=?")).isEqualTo(3);
    assertThat(solveExpression("12+1=?")).isEqualTo(13);
    assertThat(solveExpression("-1000000+1000000=?")).isEqualTo(0);
  }

  @Test
  void shouldSolveFirstOperatorForAddition() {
    assertThat(solveExpression("?+1=2")).isEqualTo(1);
    assertThat(solveExpression("?+200000=200548")).isEqualTo(548);
  }

  @Test
  void shouldSolveSecondOperatorForAddition() {
    assertThat(solveExpression("323456+?=567821")).isEqualTo(244365);
  }

  @Test
  void shouldSoveResultForSubstraction() {
    assertThat(solveExpression("1-1=?")).isEqualTo(0);
    assertThat(solveExpression("-11-1=?")).isEqualTo(-12);
  }

  private int solveExpression(String expression) {
    final var bla = expression.split("=");
    final var left = bla[0];
    final var right = bla[1];

    final var operators = left.split("(?=[+-][0-9?])(?<=[0-9?])");

    final var operator = operators[1].charAt(0);

    final var isMissingInFirstOperator = operators[0].contains("?");
    if (isMissingInFirstOperator) {
      final var op2 = Integer.valueOf(operators[1].substring(1));
      final var result = Integer.valueOf(right);

      if (operator == '-') {
        return result + op2;
      } else {
        return result - op2;
      }
    }

    final var isMissingSecondOperator = operators[1].contains("?");
    if (isMissingSecondOperator) {
      final var op1 = Integer.parseInt(operators[0]);
      final var result = Integer.parseInt(right);

      if (operator == '-') {
        return Operation.ADD.apply(result, op1);
      } else {
        return Operation.SUBSTRACT.apply(result, op1);
      }
    }

    final var op1 = Integer.parseInt(operators[0]);
    final var op2 = Integer.parseInt(operators[1].substring(1));
    if (operator == '-') {
      return Operation.SUBSTRACT.apply(op1, op2);
    } else {
      return Operation.ADD.apply(op1, op2);
    }
  }

  enum Operation {
    ADD((x, y) -> x + y),
    SUBSTRACT((x, y) -> x - y),
    MULTIPLY((x, y) -> x * y),
    DIVIDE((x, y) -> x / y);

    private final IntBinaryOperator operator;

    Operation(IntBinaryOperator intBinaryOperator) {
      this.operator = intBinaryOperator;
    }

    public int apply(int x, int y) {
      return operator.applyAsInt(x, y);
    }
  }
}
