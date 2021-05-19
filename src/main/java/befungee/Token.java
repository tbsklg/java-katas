package befungee;

import com.sun.jdi.IntegerType;

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

    public static Token ofType(InterpreterType type) {
        return new Token(type);
    }

    public static Token withTypeAndValue(InterpreterType type, Integer value) {
        return new Token(type, value);
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
