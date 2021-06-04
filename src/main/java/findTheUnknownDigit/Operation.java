package findTheUnknownDigit;

import java.util.function.IntBinaryOperator;

import static java.text.MessageFormat.format;

public enum Operation {
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
