package findTheUnknownDigit;

public class ExpressionParser {
  private static final String BY_EQUATION = "=";
  private final String expression;

  public ExpressionParser(String expression) {
    this.expression = expression;
  }

  public static ExpressionParser from(String expression) {
    return new ExpressionParser(expression);
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
