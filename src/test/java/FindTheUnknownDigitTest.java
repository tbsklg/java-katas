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

  @Test
  void shouldSolveForONLYRightMissing() {
    assertThat(solveExpression("1+1=?")).isEqualTo(2);
    assertThat(solveExpression("2+1=?")).isEqualTo(3);
    assertThat(solveExpression("1-1=?")).isEqualTo(0);
    assertThat(solveExpression("-11-1=?")).isEqualTo(-1);
    assertThat(solveExpression("2*5=?0")).isEqualTo(1);
    assertThat(solveExpression("19--45=5?")).isEqualTo(-1);
  }

  @Test
  void shouldSolveForLeftTermAndRightTermMissing() {
    assertThat(solveExpression("1?+1?=26")).isEqualTo(3);
  }

  @Test
  void shouldSolveForLeftTermAndRightMissing() {
    assertThat(solveExpression("?*11=??")).isEqualTo(2);
    assertThat(solveExpression("??*1=??")).isEqualTo(2);
    assertThat(solveExpression("-5?*-1=5?")).isEqualTo(0);
    assertThat(solveExpression("?0+3=?3")).isEqualTo(1);
    assertThat(solveExpression("?+1=?")).isEqualTo(-1);
    assertThat(solveExpression("3?+71=1?1")).isEqualTo(0);
    assertThat(solveExpression("-?56373--9216=-?47157")).isEqualTo(8);
  }

  @Test
  void shouldSolveForRightTermAndRightMissing() {
    assertThat(solveExpression("123*45?=5?088")).isEqualTo(6);
  }

  @Test
  void shouldSolveForLeftTermRightTermAndRightMissing() {
    assertThat(solveExpression("??*??=302?")).isEqualTo(5);
    assertThat(solveExpression("??+??=??")).isEqualTo(-1);
    assertThat(solveExpression("?+?=?")).isEqualTo(0);
    assertThat(solveExpression("123?45*?=?")).isEqualTo(0);
  }

  private static final String RUNE = "?";

  public static int solveExpression(String plain) {
    final var expression = Parser.from(plain).parse();

    final var leftTerm = expression.getLeftTerm();
    final var rightTerm = expression.getRightTerm();
    final var right = expression.getRight();
    final var operation = expression.getOperation();

    final var start = isRuneOnFirstPosition(leftTerm, rightTerm, right) ? 1 : 0;

    for (int current = start; current <= 9; current++) {
      final var op1 = leftTerm.contains(RUNE) ? parseInt(leftTerm.replace(RUNE, String.valueOf(current))) : parseInt(leftTerm);
      final var op2 = rightTerm.contains(RUNE) ? parseInt(rightTerm.replace(RUNE, String.valueOf(current))) : parseInt(rightTerm);
      final var result = right.contains(RUNE) ? parseInt(right.replace(RUNE, String.valueOf(current))) : parseInt(right);

      if (operation.apply(op1, op2) == result) {
        if (!expression.contains(current)) {
          return current;
        }
      }

      if (current == 9) {
        return -1;
      }
    }

    return -1;
  }

  private static class Parser {
    private static final String BY_EQUATION = "=";
    private final String expression;

    public Parser(String expression) {
      this.expression = expression;
    }

    public static Parser from(String expression) {
      return new Parser(expression);
    }

    public Expression parse() {
      final var expressions = expression.split(BY_EQUATION);
      // <left> = <right>
      final var left = expressions[0];
      final var right = expressions[1];

      // i.e. 123*-1 => [123, *-1]
      final var terms = left.split("(?<=[0-9?])(?=[*+-])");
      // <leftTerm> <operator> <rightTerm> = <result>
      final var leftTerm = terms[0];
      final var rightTerm = terms[1].substring(1);
      final var operation = Operation.from(terms[1].charAt(0));

      return new Expression.Builder()
              .withLeftTerm(leftTerm)
              .withRightTerm(rightTerm)
              .withRight(right)
              .withOperation(operation)
              .createExpression();
    }
  }

  private static class Expression {
    String leftTerm;
    String rightTerm;
    String right;
    Operation operation;

    public static class Builder {

      private String leftTerm;
      private String rightTerm;
      private String right;
      private Operation operation;

      public Builder withLeftTerm(String leftTerm) {
        this.leftTerm = leftTerm;
        return this;
      }

      public Builder withRightTerm(String rightTerm) {
        this.rightTerm = rightTerm;
        return this;
      }

      public Builder withRight(String right) {
        this.right = right;
        return this;
      }

      public Builder withOperation(Operation operation) {
        this.operation = operation;
        return this;
      }

      public Expression createExpression() {
        return new Expression(leftTerm, rightTerm, right, operation);
      }
    }

    private Expression(String leftTerm, String rightTerm, String right, Operation operation) {
      this.leftTerm = leftTerm;
      this.rightTerm = rightTerm;
      this.right = right;
      this.operation = operation;
    }

    public String getLeftTerm() {
      return leftTerm;
    }

    public String getRightTerm() {
      return rightTerm;
    }

    public String getRight() {
      return right;
    }

    public Operation getOperation() {
      return operation;
    }

    public boolean contains(int digit) {
      final var digitAsString = String.valueOf(digit);

      return leftTerm.contains(digitAsString) ||
              rightTerm.contains(digitAsString) ||
              right.contains(digitAsString);
    }
  }

  private static boolean isRuneOnFirstPosition(String leftTerm, String rightTerm, String right) {
    return leftTerm.indexOf(RUNE) == (leftTerm.charAt(0) == '-' ? 1 : 0) && leftTerm.length() > 1 ||
            rightTerm.indexOf(RUNE) == (rightTerm.charAt(0) == '-' ? 1 : 0) && rightTerm.length() > 1 ||
            right.indexOf(RUNE) == (right.charAt(0) == '-' ? 1 : 0) && right.length() > 1;
  }

  enum Operation {
    PLUS((x, y) -> x + y),
    MINUS((x, y) -> x - y),
    TIMES((x, y) -> x * y);

    private final IntBinaryOperator operator;

    Operation(IntBinaryOperator intBinaryOperator) {
      this.operator = intBinaryOperator;
    }

    public int apply(int x, int y) {
      return operator.applyAsInt(x, y);
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
