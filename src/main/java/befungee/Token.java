package befungee;

public class Token {
  public final InterpreterType type;
  public final Integer value;

  private Token(InterpreterType type, Integer value) {
    this.type = type;
    this.value = value;
  }

  private Token(InterpreterType type) {
    this.type = type;
    this.value = null;
  }

  public Token andValue(Integer value) {
    assert type != null;

    return new Token(this.type, value);
  }

  public static Token ofType(InterpreterType type) {
    return new Token(type);
  }

  @Override
  public String toString() {
    return "Token{" +
            "type=" + type +
            ", value='" + value + '\'' +
            '}';
  }
}
