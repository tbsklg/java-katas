package befungee;

import helpers.Stack;

import java.util.Random;

import static befungee.InterpreterType.*;
import static java.lang.Character.*;
import static java.text.MessageFormat.format;

public class Interpreter {
    private static final Token DEFAULT_TOKEN = new Token(MOVE_RIGHT, null);
    private final Program program;

    private boolean isStringMode = false;

    private Interpreter(String code) {
        this.program = Program.from(code);
    }

    public static Interpreter fromCode(String code) {
        return new Interpreter(code);
    }

    public Token getNextToken() {
        var currentChar = program.currentChar();

        if (currentChar == '"') {
            this.isStringMode = !this.isStringMode;
            return new Token(STRING_MODE, null);
        }

        if (this.isStringMode) {
            return new Token(ASCII, (int) currentChar);
        }

        if (currentChar == '@') {
            return new Token(EOF, null);
        }

        if (isDigit(currentChar)) {
            return new Token(INTEGER, getNumericValue(currentChar));
        }

        if (currentChar == '+') {
            return new Token(ADDITION, null);
        }

        if (currentChar == '-') {
            return new Token(SUBSTRACTION, null);
        }

        if (currentChar == '*') {
            return new Token(MULTIPLICATION, null);
        }

        if (currentChar == '!') {
            return new Token(LOGICAL_NOT, null);
        }

        if (currentChar == '/') {
            return new Token(DIVISION, null);
        }

        if (currentChar == 'v') {
            return new Token(MOVE_DOWN, null);
        }

        if (currentChar == '<') {
            return new Token(MOVE_LEFT, null);
        }

        if (currentChar == '^') {
            return new Token(MOVE_UP, null);
        }

        if (currentChar == '>') {
            return new Token(MOVE_RIGHT, null);
        }

        if (currentChar == '%') {
            return new Token(MODULO, null);
        }

        if (currentChar == '?') {
            return new Token(randomDir(), null);
        }

        if (currentChar == '_') {
            return new Token(MOVE_RIGHT_OR_LEFT, null);
        }

        if (currentChar == '|') {
            return new Token(MOVE_UP_OR_DOWN, null);
        }

        if (currentChar == '\n') {
            return new Token(NEW_LINE, null);
        }

        if (currentChar == '.') {
            return new Token(POP_AND_PRINT_AS_INT, null);
        }

        if (currentChar == ',') {
            return new Token(POP_AND_PRINT_AS_ASCII, null);
        }

        if (currentChar == ':') {
            return new Token(DUPLICATE, null);
        }

        if (currentChar == '#') {
            return new Token(TRAMPOLINE, null);
        }

        if (currentChar == '`') {
            return new Token(BACKTICK, null);
        }

        if (currentChar == '\\') {
            return new Token(SWAP, null);
        }

        if (currentChar == '$') {
            return new Token(DISCARD, null);
        }

        if (currentChar == 'g') {
            return new Token(GET, null);
        }

        if (currentChar == 'p') {
            return new Token(PUT, null);
        }

        if (isWhitespace(currentChar)) {
            return new Token(WHITESPACE, null);
        }
        throw new IllegalStateException(format("No Token could be created for character {0}", currentChar));
    }

    private InterpreterType randomDir() {
        final var dirs = new InterpreterType[]{MOVE_DOWN, MOVE_UP, MOVE_LEFT, MOVE_RIGHT};
        final int pick = new Random().nextInt(dirs.length);
        return dirs[pick];
    }

    public String interpret() {
        final var stringBuilder = new StringBuilder();
        final var stack = new Stack(100);

        var currentToken = DEFAULT_TOKEN;
        while (currentToken.type != EOF) {
            currentToken = this.getNextToken();

            if (currentToken.type == ASCII) {
                stack.push(currentToken.value);
            }

            if (currentToken.type == INTEGER) {
                stack.push(currentToken.value);
            }

            if (currentToken.type == MOVE_UP) {
                program.setDirection(ProgramDirection.UP);
            }

            if (currentToken.type == MOVE_DOWN) {
                program.setDirection(ProgramDirection.DOWN);
            }

            if (currentToken.type == MOVE_LEFT) {
                program.setDirection(ProgramDirection.LEFT);
            }

            if (currentToken.type == MOVE_RIGHT) {
                program.setDirection(ProgramDirection.RIGHT);
            }

            if (currentToken.type == ADDITION) {
                var a = stack.pop();
                var b = stack.pop();
                stack.push(a + b);
            }

            if (currentToken.type == SUBSTRACTION) {
                var a = stack.pop();
                var b = stack.pop();
                stack.push(b - a);
            }

            if (currentToken.type == MULTIPLICATION) {
                var a = stack.pop();
                var b = stack.pop();
                stack.push(b * a);
            }

            if (currentToken.type == DIVISION) {
                var a = stack.pop();
                var b = stack.pop();

                if (a == 0) {
                    stack.push(0);
                } else {
                    stack.push(b / a);
                }
            }

            if (currentToken.type == MODULO) {
                var a = stack.pop();
                var b = stack.pop();

                if (a == 0) {
                    stack.push(0);
                } else {
                    stack.push(b % a);
                }
            }

            if (currentToken.type == MOVE_RIGHT_OR_LEFT) {
                var a = stack.pop();
                if (a == 0) {
                    program.setDirection(ProgramDirection.RIGHT);
                } else {
                    program.setDirection(ProgramDirection.LEFT);
                }
            }

            if (currentToken.type == MOVE_UP_OR_DOWN) {
                var a = stack.pop();
                if (a == 0) {
                    program.setDirection(ProgramDirection.DOWN);
                } else {
                    program.setDirection(ProgramDirection.UP);
                }
            }

            if (currentToken.type == DUPLICATE) {
                if (stack.isEmpty()) {
                    stack.push(0);
                } else {
                    var a = stack.pop();
                    stack.push(a);
                    stack.push(a);
                }
            }

            if (currentToken.type == POP_AND_PRINT_AS_INT) {
                stringBuilder.append(stack.pop());
            }

            if (currentToken.type == POP_AND_PRINT_AS_ASCII) {
                stringBuilder.append((char) stack.pop());
            }

            if (currentToken.type == LOGICAL_NOT) {
                var a = stack.pop();
                if (a == 0) {
                    stack.push(1);
                } else {
                    stack.push(0);
                }
            }

            if (currentToken.type == BACKTICK) {
                var a = stack.pop();
                var b = stack.pop();

                if (b > a) {
                    stack.push(1);
                } else {
                    stack.push(0);
                }
            }

            if (currentToken.type == SWAP) {
                var a = stack.pop();
                var b = stack.pop();

                stack.push(a);
                stack.push(b);
            }

            if (currentToken.type == DISCARD) {
                stack.pop();
            }

            if (currentToken.type == GET) {
                var y = stack.pop();
                var x = stack.pop();
                stack.push(program.getCodeAt(y, x));
            }

            if (currentToken.type == PUT) {
                var y = stack.pop();
                var x = stack.pop();
                var v = stack.pop();

                program.change(x, y, v);
            }

            if (currentToken.type == TRAMPOLINE) {
                program.next();
            }

            program.next();
        }

        return stringBuilder.toString();
    }
}
