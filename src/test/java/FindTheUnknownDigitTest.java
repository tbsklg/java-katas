import org.junit.jupiter.api.Test;

import java.util.function.IntBinaryOperator;

import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * [number][op][number]=[number]
 * <p>
 * range of a number: -1000000 to 1000000
 * number never starts with 0
 * ? represents digit rune, never an operator
 * rune is not any of other given digits
 * if more than one rune matches, give the lowest one
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
  }

  @Test
  void shouldSolveAdditionWithMultipleRunes() {
    assertThat(solveExpression("?0+3=?3")).isEqualTo(1);
    assertThat(solveExpression("?+1=?")).isEqualTo(-1);
    assertThat(solveExpression("?+?=?")).isEqualTo(0);
    assertThat(solveExpression("??+??=??")).isEqualTo(-1);
    assertThat(solveExpression("3?+71=1?1")).isEqualTo(0);
    assertThat(solveExpression("1?+1?=26")).isEqualTo(3);
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
  void shouldSolveSubstraction() {
    assertThat(solveExpression("1-1=?")).isEqualTo(0);
    assertThat(solveExpression("-11-1=?")).isEqualTo(-1);
    assertThat(solveExpression("19--45=5?")).isEqualTo(-1);
    assertThat(solveExpression("-?56373--9216=-?47157")).isEqualTo(8);
  }

  @Test
  void shouldSolveMultiplication() {
    assertThat(solveExpression("2*5=?0")).isEqualTo(1);
  }

  @Test
  void shouldSolveMultiplicationWithMultipleRunes() {
    assertThat(solveExpression("-5?*-1=5?")).isEqualTo(0);
    assertThat(solveExpression("??*??=302?")).isEqualTo(5);
    assertThat(solveExpression("123*45?=5?088")).isEqualTo(6);
    assertThat(solveExpression("??*1=??")).isEqualTo(2);
    assertThat(solveExpression("??*??=302?")).isEqualTo(5);
    assertThat(solveExpression("?*11=??")).isEqualTo(2);
    assertThat(solveExpression("123?45*?=?")).isEqualTo(0);
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
    final var rightTerm = terms[1].substring(1);
    final var operator = terms[1].charAt(0);

    final var isRuneOnFirstPosition = isRuneOnFirstPosition(right, leftTerm, rightTerm);

    if (leftTerm.contains(RUNE) && rightTerm.contains(RUNE) && right.contains(RUNE)) {
      var start = isRuneOnFirstPosition && leftTerm.length() > 1 && rightTerm.length() > 1 && right.length() > 1 ? 1 : 0;

      for (int rune = start; rune <= 9; rune++) {
        final var result = parseInt(right.replace("?", String.valueOf(rune)));
        final var op1 = parseInt(leftTerm.replace("?", String.valueOf(rune)));
        final var op2 = parseInt(rightTerm.replace("?", String.valueOf(rune)));

        if (Operation.from(operator).apply(op1, op2) == result) {
          if (!expression.contains(String.valueOf(rune))) {
            return rune;
          }
        }

        if (rune == 9) {
          return -1;
        }
      }
    }

    if (leftTerm.contains(RUNE) && rightTerm.contains(RUNE)) {
      var start = isRuneOnFirstPosition && rightTerm.length() > 1 && leftTerm.length() > 1 ? 1 : 0;

      for (int rune = start; rune <= 9; rune++) {
        final var op1 = parseInt(rightTerm.replace("?", String.valueOf(rune)));
        final var op2 = parseInt(leftTerm.replace("?", String.valueOf(rune)));

        if (Operation.from(operator).apply(op1, op2) == parseInt(right)) {
          if (!expression.contains(String.valueOf(rune))) {
            return rune;
          }
        }

        if (rune == 9) {
          return -1;
        }
      }
    }

    if (leftTerm.contains(RUNE) && right.contains(RUNE)) {
      var start = isRuneOnFirstPosition && right.length() > 1 ? 1 : 0;

      for (int rune = start; rune <= 9; rune++) {
        final var result = parseInt(right.replace("?", String.valueOf(rune)));
        final var op1 = parseInt(leftTerm.replace("?", String.valueOf(rune)));
        final var op2 = parseInt(rightTerm);

        if (Operation.from(operator).apply(op1, op2) == result) {
          if (!expression.contains(String.valueOf(rune))) {
            return rune;
          }
        }

        if (rune == 9) {
          return -1;
        }
      }
    }

    if (rightTerm.contains(RUNE) && right.contains(RUNE)) {
      var start = isRuneOnFirstPosition && rightTerm.length() > 1 && right.length() > 1 ? 1 : 0;

      for (int rune = start; rune <= 9; rune++) {
        final var result = parseInt(right.replace("?", String.valueOf(rune)));
        final var op1 = parseInt(leftTerm);
        final var op2 = parseInt(rightTerm.replace("?", String.valueOf(rune)));

        if (Operation.from(operator).apply(op1, op2) == result) {
          if (!expression.contains(String.valueOf(rune))) {
            return rune;
          }
        }

        if (rune == 9) {
          return -1;
        }
      }
    }

    if (leftTerm.contains(RUNE)) {
      final var result = parseInt(right);
      final var op2 = parseInt(rightTerm);

      return Operation.inverse(Operation.from(operator)).apply(result, op2);
    }

    if (rightTerm.contains(RUNE)) {
      final var op1 = parseInt(leftTerm);
      final var result = parseInt(right);

      return Operation.inverse(Operation.from(operator)).apply(result, op1);
    }

    if (right.contains(RUNE)) {
      var start = isRuneOnFirstPosition && right.length() > 1 ? 1 : 0;

      for (int rune = start; rune <= 9; rune++) {
        final var op1 = parseInt(leftTerm);
        final var op2 = parseInt(rightTerm);
        final var result = parseInt(right.replace("?", String.valueOf(rune)));

        if (Operation.from(operator).apply(op1, op2) == result) {
          if (!expression.contains(String.valueOf(rune))) {
            return rune;
          }
        }

        if (rune == 9) {
          return -1;
        }
      }
    }

    final var op1 = parseInt(leftTerm);
    final var op2 = parseInt(rightTerm);
    return Operation.from(operator).apply(op1, op2);
  }

  private boolean isRuneOnFirstPosition(String right, String leftTerm, String rightTerm) {
    return leftTerm.indexOf('?') == (leftTerm.charAt(0) == '-' ? 1 : 0) ||
            rightTerm.indexOf('?') == (rightTerm.charAt(0) == '-' ? 1 : 0) ||
            right.indexOf('?') == (right.charAt(0) == '-' ? 1 : 0);
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
