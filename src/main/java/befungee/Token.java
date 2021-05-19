package befungee;

public class Token {
    public final InterpreterType type;
    public final Integer value;

    public Token(InterpreterType type, Integer value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
