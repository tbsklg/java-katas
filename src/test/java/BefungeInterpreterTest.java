import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BefungeInterpreterTest {

    private String interpret(String code) {
        return Interpreter.fromCode(code).interpret();
    }

    private enum ProgramDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    private static class Program {

        private static final String SPLIT_BY = "\\n";

        private final Character[][] code;
        private int row = 0;
        private int column = 0;

        private ProgramDirection dir = ProgramDirection.RIGHT;

        private Program(String code) {
            this.code = create(code);
        }

        public static Program from(String code) {
            return new Program(code);
        }

        private static Character[][] create(String code) {
            var lines = code.split(SPLIT_BY);

            final var maxLineLength = Arrays.stream(lines)
                    .map(String::length)
                    .max(Comparator.naturalOrder())
                    .orElse(0);

            var program = new Character[lines.length][maxLineLength];

            for (int i = 0; i < lines.length; i++) {
                final var chars = lines[i].toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    program[i][j] = chars[j];
                }
            }

            return program;
        }

        public void setDirection(ProgramDirection dir) {
            this.dir = dir;
        }

        public Character currentChar() {
            return code[row][column];
        }

        public void next() {
            if (this.dir == ProgramDirection.DOWN) {
                row++;
            } else if (dir == ProgramDirection.UP) {
                row--;
            } else if (dir == ProgramDirection.LEFT) {
                column--;
            } else {
                column++;
            }
        }
    }

    private static class Interpreter {
        private static final Token DEFAULT_TOKEN = new Token(Type.MOVE_RIGHT, null);
        private final Program program;

        private Interpreter(String code) {
            if (code.isBlank()) {
                throw new IllegalArgumentException("Code should not be blank");
            }
            this.program = Program.from(code);
        }

        public static Interpreter fromCode(String code) {
            return new Interpreter(code);
        }

        public Token getNextToken() {
            var currentChar = program.currentChar();

            if (currentChar == '@') {
                return new Token(Type.EOF, null);
            }

            if (isDigit(currentChar)) {
                return new Token(Type.INTEGER, String.valueOf(currentChar));
            }

            if (currentChar == '+') {
                return new Token(Type.ADDITION, String.valueOf(currentChar));
            }

            if (currentChar == '-') {
                return new Token(Type.SUBSTRACTION, String.valueOf(currentChar));
            }

            if (currentChar == 'v') {
                return new Token(Type.MOVE_DOWN, null);
            }

            if (currentChar == '<') {
                return new Token(Type.MOVE_LEFT, null);
            }

            if (currentChar == '^') {
                return new Token(Type.MOVE_UP, null);
            }

            if (currentChar == '>') {
                return new Token(Type.MOVE_RIGHT, null);
            }

            if (currentChar == '_') {
                return new Token(Type.MOVE_RIGHT_OR_LEFT, null);
            }

            if (currentChar == '|') {
                return new Token(Type.MOVE_UP_OR_DOWN, null);
            }

            if (currentChar == '\n') {
                return new Token(Type.NEW_LINE, null);
            }

            if (currentChar == '.') {
                return new Token(Type.POP_AND_PRINT, null);
            }

            if (currentChar == ':') {
                return new Token(Type.PEEK, null);
            }

            if (isWhitespace(currentChar)) {
                return new Token(Type.WHITESPACE, null);
            }
            throw new IllegalStateException(format("No Token could be created for character {0}", currentChar));
        }

        public String interpret() {
            final var stringBuilder = new StringBuilder();
            final var stack = new Stack(100);

            var currentToken = DEFAULT_TOKEN;
            while (currentToken.type != Type.EOF) {
                currentToken = this.getNextToken();

                if (currentToken.type == Type.INTEGER) {
                    stack.push(parseInt(currentToken.value));
                }

                if (currentToken.type == Type.MOVE_UP){
                    program.setDirection(ProgramDirection.UP);
                }

                if (currentToken.type == Type.MOVE_DOWN){
                    program.setDirection(ProgramDirection.DOWN);
                }

                if (currentToken.type == Type.MOVE_LEFT) {
                    program.setDirection(ProgramDirection.LEFT);
                }

                if (currentToken.type == Type.MOVE_RIGHT){
                    program.setDirection(ProgramDirection.RIGHT);
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

                if (currentToken.type == Type.MOVE_RIGHT_OR_LEFT) {
                    var a = stack.pop();
                    if (a == 0) {
                        program.setDirection(ProgramDirection.RIGHT);
                    } else {
                        program.setDirection(ProgramDirection.LEFT);
                    }
                }

                if (currentToken.type == Type.MOVE_UP_OR_DOWN) {
                    var a = stack.pop();
                    if (a == 0) {
                        program.setDirection(ProgramDirection.UP);
                    } else {
                        program.setDirection(ProgramDirection.DOWN);
                    }
                }

                if (currentToken.type == Type.PEEK) {
                    var a = stack.peek();
                    if (a == 0) {
                        stack.push(a);
                    }
                }

                if (currentToken.type == Type.POP_AND_PRINT) {
                    while (!stack.isEmpty()) {
                        stringBuilder.append(stack.pop());
                    }
                }

                program.next();
            }

            return stringBuilder.toString();
        }
    }

    private enum Type {
        INTEGER,
        ADDITION,
        SUBSTRACTION,
        EOF,
        MOVE_UP,
        MOVE_DOWN,
        MOVE_RIGHT,
        MOVE_LEFT,
        WHITESPACE,
        NEW_LINE,
        MOVE_RIGHT_OR_LEFT,
        MOVE_UP_OR_DOWN,
        POP_AND_PRINT,
        PEEK,
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

        public int peek() {
            if (N == 0) {
                return 0;
            }

            return s[N - 1];
        }

        public boolean isEmpty() {
            return N == 0;
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
        assertThat(interpret("321.@")).isEqualTo("123");
    }

    @Test
    void shouldSkipWhitespaces() {
        assertThat(interpret("321     .@")).isEqualTo("123");
    }

    @Test
    void shouldInterpretAddition() {
        assertThat(interpret("321+.@")).isEqualTo("33");
    }

    @Test
    void shouldInterpretSubtraction() {
        assertThat(interpret("32-.@")).isEqualTo("1");
    }

    @Test
    void shouldMoveDown() {
        assertThat(interpret("321v\n   .\n   @")).isEqualTo("123");
    }

    @Test
    void shouldMoveLeft() {
        assertThat(interpret("654v\nv.3<\n@")).isEqualTo("3456");
    }

    @Test
    void shouldMoveRight() {
        assertThat(interpret("654v\nv23<\n>87.@")).isEqualTo("7823456");
    }

    @Test
    void shouldMoveUp() {
        assertThat(interpret("654v@\nv23<.\n>876^")).isEqualTo("67823456");
    }

    @Test
    void shouldInterpretBefungee() {
        assertThat(interpret(">987v>.v\nv456<  :\n>321 ^ _@")).isEqualTo("123456789");
    }
}
