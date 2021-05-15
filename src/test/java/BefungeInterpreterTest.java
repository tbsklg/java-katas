import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BefungeInterpreterTest {

    private String interpret(String code) {
        return new Interpreter(code).interpret();
    }

    private static class Interpreter {

        private final String code;

        private int position = 0;
        private Token currentToken = new Token(Type.MOVE_RIGHT, ">");

        public Interpreter(String code) {
            if (code.isBlank()) {
                throw new IllegalArgumentException("Code should not be blank");
            }

            this.code = code;
        }

        public Token getNextToken() {
            var currentChar = code.charAt(position);

            if (currentChar == '@') {
                position++;
                currentToken = new Token(Type.EOF, String.valueOf(currentChar));
                return currentToken;
            }

            if (Character.isDigit(currentChar)) {
                position++;
                currentToken = new Token(Type.INTEGER, String.valueOf(currentChar));
                return currentToken;
            }

            if (currentChar == '+') {
                position++;
                currentToken = new Token(Type.ADDITION, String.valueOf(currentChar));
                return currentToken;
            }

            if (currentChar == '-') {
                position++;
                currentToken = new Token(Type.SUBSTRACTION, String.valueOf(currentChar));
                return currentToken;
            }

            throw new IllegalStateException(format("No Token could be created for character {0}", currentChar));
        }

        public String interpret() {
            var stack = new Stack(100);
            while (this.currentToken.type != Type.EOF) {
                var currentToken = this.getNextToken();

                if (currentToken.type == Type.INTEGER) {
                    stack.push(parseInt(currentToken.value));
                }

                if (currentToken.type == Type.ADDITION) {
                    var a = stack.pop();
                    var b = stack.pop();
                    stack.push(a + b);
                }

                if (currentToken.type == Type.SUBSTRACTION) {
                    var a = stack.pop();
                    var b = stack.pop();
                    stack.push(b - a);
                }
            }

            final var stringBuilder = new StringBuilder();
            var counter = stack.size() - 1;

            for (int i = counter; i >= 0; i--) {
                stringBuilder.append(stack.pop());
            }

            return stringBuilder.toString();
        }
    }

    private enum Type {
        INTEGER,
        ADDITION,
        SUBSTRACTION,
        EOF,
        MOVE_RIGHT,
    }

    private static class Token {
        private final Type type;
        private final String value;

        public Token(Type type, String value) {
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

    private static class Stack {

        private final Integer[] s;
        private int N = 0;

        public Stack(int capacity) {
            this.s = new Integer[capacity];
        }

        public void push(int item) {
            s[N++] = item;
        }

        public int pop() {
            var item = s[--N];
            s[N] = null;
            return item;
        }

        public boolean isEmpty() {
            return N == 0;
        }

        public int size() {
            return N;
        }
    }


    @Test
    void shouldThrowExceptionForEmptyString() {
        assertThatThrownBy(() -> interpret("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("It should throw an excpetion if the code does not contain '@' as EOF key")
    void shouldThrowExceptionForCodeWithoutEOF() {
        assertThatThrownBy(() -> interpret("ffds")).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void shouldInterpretNumerics() {
        assertThat(interpret("321@")).isEqualTo("123");
    }

    @Test
    void shouldInterpretAddition() {
        assertThat(interpret("321+@")).isEqualTo("33");
    }

    @Test
    void shouldInterpretSubtraction() {
        assertThat(interpret("32-@")).isEqualTo("1");
    }
}
