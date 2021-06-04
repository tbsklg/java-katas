package findTheUnknownDigit;

public class Expression {
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
