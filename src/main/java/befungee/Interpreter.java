package befungee;

import helpers.Stack;

import java.util.Random;

import static befungee.InterpreterType.*;
import static java.lang.Character.*;
import static java.text.MessageFormat.format;

public class Interpreter {
    private static final Token DEFAULT_TOKEN = Token.ofType(MOVE_RIGHT);
    private final Program program;

    private boolean isStringMode = false;

    private Interpreter(String code) {
        this.program = Program.from(code);
    }

    public static Interpreter fromCode(String code) {
        return new Interpreter(code);
    }

    public Token tokenFrom(Character currentChar) {
        if (currentChar == '"') {
            this.isStringMode = !this.isStringMode;
            return Token.ofType(STRING_MODE);
        }

        if (this.isStringMode) {
            return Token.ofType(ASCII).andValue((int) currentChar);
        }

        if (currentChar == '@') {
            return Token.ofType(EOF);
        }

        if (isDigit(currentChar)) {
            return Token.ofType(INTEGER).andValue(getNumericValue(currentChar));
        }

        if (currentChar == '+') {
            return Token.ofType(ADDITION);
        }

        if (currentChar == '-') {
            return Token.ofType(SUBSTRACTION);
        }

        if (currentChar == '*') {
            return Token.ofType(MULTIPLICATION);
        }

        if (currentChar == '!') {
            return Token.ofType(LOGICAL_NOT);
        }

        if (currentChar == '/') {
            return Token.ofType(DIVISION);
        }

        if (currentChar == 'v') {
            return Token.ofType(MOVE_DOWN);
        }

        if (currentChar == '<') {
            return Token.ofType(MOVE_LEFT);
        }

        if (currentChar == '^') {
            return Token.ofType(MOVE_UP);
        }

        if (currentChar == '>') {
            return Token.ofType(MOVE_RIGHT);
        }

        if (currentChar == '%') {
            return Token.ofType(MODULO);
        }

        if (currentChar == '?') {
            return Token.ofType(randomDir());
        }

        if (currentChar == '_') {
            return Token.ofType(MOVE_RIGHT_OR_LEFT);
        }

        if (currentChar == '|') {
            return Token.ofType(MOVE_UP_OR_DOWN);
        }

        if (currentChar == '\n') {
            return Token.ofType(NEW_LINE);
        }

        if (currentChar == '.') {
            return Token.ofType(POP_AND_PRINT_AS_INT);
        }

        if (currentChar == ',') {
            return Token.ofType(POP_AND_PRINT_AS_ASCII);
        }

        if (currentChar == ':') {
            return Token.ofType(DUPLICATE);
        }

        if (currentChar == '#') {
            return Token.ofType(TRAMPOLINE);
        }

        if (currentChar == '`') {
            return Token.ofType(BACKTICK);
        }

        if (currentChar == '\\') {
            return Token.ofType(SWAP);
        }

        if (currentChar == '$') {
            return Token.ofType(DISCARD);
        }

        if (currentChar == 'g') {
            return Token.ofType(GET);
        }

        if (currentChar == 'p') {
            return Token.ofType(PUT);
        }

        if (isWhitespace(currentChar)) {
            return Token.ofType(WHITESPACE);
        }
        throw new IllegalStateException(format("No Token could be created for character {0}", currentChar));
    }

    private InterpreterType randomDir() {
        final var dirs = new InterpreterType[]{MOVE_DOWN, MOVE_UP, MOVE_LEFT, MOVE_RIGHT};
        final var pick = new Random().nextInt(dirs.length);
        return dirs[pick];
    }

    public String interpret() {
        final var stringBuilder = new StringBuilder();
        final var stack = new Stack(100);

        while (true) {
            final var currentToken = this.tokenFrom(program.currentChar());

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

            if (currentToken.type == EOF) {
                break;
            }

            program.next();
        }

        return stringBuilder.toString();
    }
}
