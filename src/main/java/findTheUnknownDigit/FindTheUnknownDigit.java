package findTheUnknownDigit;

public class FindTheUnknownDigit {
  static final String RUNE = "?";

  public static int solveExpression(String plain) {
    final var expression = ExpressionParser.from(plain).parse();

    final var leftTerm = expression.getLeftTerm();
    final var rightTerm = expression.getRightTerm();
    final var right = expression.getRight();
    final var operation = expression.getOperation();

    final var start = isRuneOnFirstPosition(leftTerm, rightTerm, right) ? 1 : 0;

    for (int current = start; current <= 9; current++) {
      final var op1 = leftTerm.contains(RUNE) ? Integer.parseInt(leftTerm.replace(RUNE, String.valueOf(current))) : Integer.parseInt(leftTerm);
      final var op2 = rightTerm.contains(RUNE) ? Integer.parseInt(rightTerm.replace(RUNE, String.valueOf(current))) : Integer.parseInt(rightTerm);
      final var result = right.contains(RUNE) ? Integer.parseInt(right.replace(RUNE, String.valueOf(current))) : Integer.parseInt(right);

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

  static boolean isRuneOnFirstPosition(String leftTerm, String rightTerm, String right) {
    return leftTerm.indexOf(RUNE) == (leftTerm.charAt(0) == '-' ? 1 : 0) && leftTerm.length() > 1 ||
            rightTerm.indexOf(RUNE) == (rightTerm.charAt(0) == '-' ? 1 : 0) && rightTerm.length() > 1 ||
            right.indexOf(RUNE) == (right.charAt(0) == '-' ? 1 : 0) && right.length() > 1;
  }
}
