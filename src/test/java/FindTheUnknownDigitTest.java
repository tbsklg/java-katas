import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.function.IntBinaryOperator;

import static java.lang.Integer.parseInt;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * [number][op][number]=[number]
 * <p>
 * range of a number: -1000000 to 1000000
 * number never starts with 0
 * ? represents digit rune, never an operator
 * rune is not any of other given digits
 * if more than one rune matches, give the lowest one
 * expression contains 1-n ?s
 * expression has exactly one operator
 * <p>
 * returns -1 if no digit works
 */
public class FindTheUnknownDigitTest {

    public static final String BY_EQUATION = "=";
    private static final String RUNE = "?";

    @Test
    void shouldSolveForONLYRightMissing() {
        assertThat(solveExpression("1+1=?")).isEqualTo(2);
        assertThat(solveExpression("2+1=?")).isEqualTo(3);
        assertThat(solveExpression("1-1=?")).isEqualTo(0);
        assertThat(solveExpression("-11-1=?")).isEqualTo(-1);
        assertThat(solveExpression("2*5=?0")).isEqualTo(1);
        assertThat(solveExpression("19--45=5?")).isEqualTo(-1);
    }

    @Test
    void shouldSolveForLeftTermAndRightTermMissing() {
        assertThat(solveExpression("1?+1?=26")).isEqualTo(3);
    }

    @Test
    void shouldSolveForLeftTermAndRightMissing() {
        assertThat(solveExpression("?*11=??")).isEqualTo(2);
        assertThat(solveExpression("??*1=??")).isEqualTo(2);
        assertThat(solveExpression("-5?*-1=5?")).isEqualTo(0);
        assertThat(solveExpression("?0+3=?3")).isEqualTo(1);
        assertThat(solveExpression("?+1=?")).isEqualTo(-1);
        assertThat(solveExpression("3?+71=1?1")).isEqualTo(0);
        assertThat(solveExpression("-?56373--9216=-?47157")).isEqualTo(8);
    }

    @Test
    void shouldSolveForRightTermAndRightMissing() {
        assertThat(solveExpression("123*45?=5?088")).isEqualTo(6);
    }

    @Test
    void shouldSolveForLeftTermRightTermAndRightMissing() {
        assertThat(solveExpression("??*??=302?")).isEqualTo(5);
        assertThat(solveExpression("??+??=??")).isEqualTo(-1);
        assertThat(solveExpression("?+?=?")).isEqualTo(0);
        assertThat(solveExpression("123?45*?=?")).isEqualTo(0);
    }

    private int solveExpression(String expression) {
        final var expressions = expression.split(BY_EQUATION);
        // <left> = <right>
        final var left = expressions[0];
        final var right = expressions[1];

        // i.e. 123*-1 => [123, *-1]
        final var terms = left.split("(?<=[0-9?])(?=[*+-])");
        // <leftTerm> <operator> <rightTerm> = <result>
        final var leftTerm = terms[0];
        final var rightTerm = terms[1].substring(1);
        final var operator = terms[1].charAt(0);

        return solve(expression, leftTerm, rightTerm, right, Operation.from(operator));
    }

    private int solve(String expression, String leftTerm, String rightTerm, String right, Operation operator) {
        var start = isRuneOnFirstPosition(leftTerm, rightTerm, right) ? 1 : 0;

        for (int rune = start; rune <= 9; rune++) {
            final var op1 = leftTerm.contains("?") ? parseInt(leftTerm.replace("?", String.valueOf(rune))) : parseInt(leftTerm);
            final var op2 = rightTerm.contains("?") ? parseInt(rightTerm.replace("?", String.valueOf(rune))) : parseInt(rightTerm);
            final var result = right.contains("?") ? parseInt(right.replace("?", String.valueOf(rune))) : parseInt(right);

            if (operator.apply(op1, op2) == result) {
                if (!expression.contains(String.valueOf(rune))) {
                    return rune;
                }
            }

            if (rune == 9) {
                return -1;
            }
        }
        return -1;
    }

    private boolean isRuneOnFirstPosition(String leftTerm, String rightTerm, String right) {
        return leftTerm.indexOf(RUNE) == (leftTerm.charAt(0) == '-' ? 1 : 0) && leftTerm.length() > 1 ||
                rightTerm.indexOf(RUNE) == (rightTerm.charAt(0) == '-' ? 1 : 0) && rightTerm.length() > 1 ||
                right.indexOf(RUNE) == (right.charAt(0) == '-' ? 1 : 0) && right.length() > 1;
    }

    enum Operation {
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
}
