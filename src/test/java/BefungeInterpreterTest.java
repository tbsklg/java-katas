import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static java.lang.Character.*;
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
            if (!code.contains("@")) {
                throw new IllegalStateException("No EOF available");
            }

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

        private boolean stringMode = false;

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

            if (isAlphabetic(currentChar) && currentChar != 'v') {
                return new Token(Type.ALPHABETIC, (int) currentChar);
            }

            if (isDigit(currentChar)) {
                return new Token(Type.INTEGER, getNumericValue(currentChar));
            }

            if (currentChar == '+') {
                return new Token(Type.ADDITION, null);
            }

            if (currentChar == '-') {
                return new Token(Type.SUBSTRACTION, null);
            }

            if (currentChar == '*') {
                return new Token(Type.MULTIPLICATION, null);
            }

            if (currentChar == '!') {
                return new Token(Type.LOGICAL_NOT, null);
            }

            if (currentChar == '/') {
                return new Token(Type.DIVISION, null);
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

            if (currentChar == '%') {
                return new Token(Type.MODULO, null);
            }

            if (currentChar == '?') {
                return new Token(randomDir(), null);
            }

            if (currentChar == '"') {
                return new Token(Type.STRING_MODE, null);
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

            if (currentChar == '#') {
                return new Token(Type.TRAMPOLINE, null);
            }

            if (isWhitespace(currentChar)) {
                return new Token(Type.WHITESPACE, null);
            }
            throw new IllegalStateException(format("No Token could be created for character {0}", currentChar));
        }

        private Type randomDir() {
            final var dirs = new Type[]{Type.MOVE_DOWN, Type.MOVE_LEFT, Type.MOVE_RIGHT, Type.MOVE_RIGHT};
            final int pick = new Random().nextInt(dirs.length);
            return dirs[pick];
        }

        public String interpret() {
            final var stringBuilder = new StringBuilder();
            final var stack = new Stack(100);

            var currentToken = DEFAULT_TOKEN;
            while (currentToken.type != Type.EOF) {
                currentToken = this.getNextToken();

                if (currentToken.type == Type.STRING_MODE) {
                    this.stringMode = !this.stringMode;
                }

                if (currentToken.type == Type.ALPHABETIC && this.stringMode) {
                    stack.push(currentToken.value);
                }

                if (currentToken.type == Type.INTEGER) {
                    stack.push(currentToken.value);
                }

                if (currentToken.type == Type.MOVE_UP) {
                    program.setDirection(ProgramDirection.UP);
                }

                if (currentToken.type == Type.MOVE_DOWN) {
                    program.setDirection(ProgramDirection.DOWN);
                }

                if (currentToken.type == Type.MOVE_LEFT) {
                    program.setDirection(ProgramDirection.LEFT);
                }

                if (currentToken.type == Type.MOVE_RIGHT) {
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

                if (currentToken.type == Type.MULTIPLICATION) {
                    var a = stack.pop();
                    var b = stack.pop();
                    stack.push(b * a);
                }

                if (currentToken.type == Type.DIVISION) {
                    var a = stack.pop();
                    var b = stack.pop();

                    if (a == 0) {
                        stack.push(0);
                    } else {
                        stack.push(b / a);
                    }
                }

                if (currentToken.type == Type.MODULO) {
                    var a = stack.pop();
                    var b = stack.pop();

                    if (a == 0) {
                        stack.push(0);
                    } else {
                        stack.push(b % a);
                    }
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

                if (currentToken.type == Type.LOGICAL_NOT) {
                    var a = stack.pop();
                    if (a == 0) {
                        stack.push(1);
                    } else {
                        stack.push(0);
                    }
                }

                program.next();
            }

            return stringBuilder.toString();
        }
    }

    private enum Type {
        ALPHABETIC,
        INTEGER,
        ADDITION,
        SUBSTRACTION,
        MULTIPLICATION,
        DIVISION,
        LOGICAL_NOT,
        MODULO,
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
        STRING_MODE,
        TRAMPOLINE,
    }

    private static class Token {
        private final Type type;
        private final Integer value;

        public Token(Type type, Integer value) {
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
    @DisplayName("It should throw an exception if the code does not contain '@' as EOF key")
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
    void shouldInterpretModulo() {
        assertThat(interpret("32%.@")).isEqualTo("1");
        assertThat(interpret("30%.@")).isEqualTo("0");
    }

    @Test
    void shouldInterpretMultiplication() {
        assertThat(interpret("32*.@")).isEqualTo("6");
    }

    @Test
    void shouldInterpretLogicalNot() {
        assertThat(interpret("320!.@")).isEqualTo("123");
        assertThat(interpret("321!.@")).isEqualTo("023");
    }

    @Test
    void shouldInterpretDivision() {
        assertThat(interpret("82/.@")).isEqualTo("4");
        assertThat(interpret("80/.@")).isEqualTo("0");
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

    @Test
    void shouldInterpretAlphabetics() {
        assertThat(interpret("\"hallo\".@")).isEqualTo("11110810897104");
    }
}
