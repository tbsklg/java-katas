import org.junit.jupiter.api.Test;

import java.util.function.IntBinaryOperator;

import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.*;
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

  public static final String BY_EQUATION = "=";
  private static final String RUNE = "?";

  @Test
  void shouldSolveRightExpressionForAddition() {
    assertThat(solveExpression("1+1=?")).isEqualTo(2);
    assertThat(solveExpression("2+1=?")).isEqualTo(3);
    assertThat(solveExpression("12+1=?")).isEqualTo(13);
    assertThat(solveExpression("-1000000+1000000=?")).isEqualTo(0);
  }

  @Test
  void shouldSolveFirstTermForAddition() {
    assertThat(solveExpression("?+1=2")).isEqualTo(1);
    assertThat(solveExpression("?+200000=200548")).isEqualTo(548);
    assertThat(solveExpression("?+-200000=200548")).isEqualTo(400548);
  }

  @Test
  void shouldSolveSecondTermForAddition() {
    assertThat(solveExpression("323456+?=567821")).isEqualTo(244365);
  }

  @Test
  void shouldSolveRightExpressionForSubstraction() {
    assertThat(solveExpression("1-1=?")).isEqualTo(0);
    assertThat(solveExpression("-11-1=?")).isEqualTo(-12);
  }

  @Test
  void shouldSolveRightExpressionForMultiplication() {
    assertThat(solveExpression("1*5=?")).isEqualTo(5);
    assertThat(solveExpression("12345*-1=?")).isEqualTo(-12345);
  }

  private int solveExpression(String expression) {
    final var expressions = expression.split(BY_EQUATION);
    // <left> = <right>
    final var left = expressions[0];
    final var right = expressions[1];

    // i.e. 123*-1 => [123, *-1]
    final var terms = left.split("(?<=[0-9?])(?=[*+-])");
    // <leftTerm> <operator> <rightTerm> = <result>
    final var leftTerm = terms[0];
    final var rightTerm = terms[1];
    final var operator = rightTerm.charAt(0);

    if (leftTerm.contains(RUNE)) {
      final var op2 = parseInt(rightTerm.substring(1));
      final var result = parseInt(right);

      return Operation.inverse(Operation.from(operator)).apply(result, op2);
    }

    if (rightTerm.contains(RUNE)) {
      final var op1 = parseInt(leftTerm);
      final var result = parseInt(right);

      return Operation.inverse(Operation.from(operator)).apply(result, op1);
    }

    final var op1 = parseInt(leftTerm);
    final var op2 = parseInt(rightTerm.substring(1));
    return Operation.from(operator).apply(op1, op2);
  }

  enum Operation {
    PLUS((x, y) -> x + y),
    MINUS((x, y) -> x - y),
    TIMES((x, y) -> x * y),
    DIVIDE((x, y) -> x / y);

    private final IntBinaryOperator operator;

    Operation(IntBinaryOperator intBinaryOperator) {
      this.operator = intBinaryOperator;
    }

    public int apply(int x, int y) {
      return operator.applyAsInt(x, y);
    }

    public static Operation inverse(Operation operation) {
      switch (operation) {
        case PLUS:
          return Operation.MINUS;
        case MINUS:
          return Operation.PLUS;
        case TIMES:
          return Operation.DIVIDE;
        case DIVIDE:
          return Operation.TIMES;

        default:
          throw new AssertionError(format("Unknown operation: {0}", operation));
      }
    }

    public static Operation from(Character character) {
      switch (character) {
        case '-':
          return Operation.MINUS;
        case '+':
          return Operation.PLUS;
        case '*':
          return Operation.TIMES;

        default:
          throw new AssertionError(format("Unknown character {0}", character));
      }
    }
  }
}
