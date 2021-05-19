import befungee.Interpreter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BefungeInterpreterTest {

    private String interpret(String code) {
        return Interpreter.fromCode(code).interpret();
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
        assertThat(interpret("321.@")).isEqualTo("1");
    }

    @Test
    void shouldInterpretSubtraction() {
        assertThat(interpret("32-.@")).isEqualTo("1");
        assertThat(interpret("2-.@")).isEqualTo("-2");
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
    void shouldInterpretBackTick() {
        assertThat(interpret("45`.@")).isEqualTo("0");
        assertThat(interpret("43`.@")).isEqualTo("1");
    }

    @Test
    void shouldInterpretLogicalNot() {
        assertThat(interpret("320!.@")).isEqualTo("1");
        assertThat(interpret("321!.@")).isEqualTo("0");
    }

    @Test
    void shouldInterpretDivision() {
        assertThat(interpret("82/.@")).isEqualTo("4");
        assertThat(interpret("80/.@")).isEqualTo("0");
    }

    @Test
    void shouldInterpretBefungee() {
        assertThat(interpret(">987v>.v\nv456<  :\n>321 ^ _@")).isEqualTo("123456789");
    }

    @Test
    void shouldInterpretHelloWorld() {
        assertThat(interpret(">25*\"!dlroW olleH\":v\n                v:,_@\n                >  ^")).isEqualTo("Hello World!\n");
    }

    @Test
    void shouldInterpretFactorial() {
        assertThat(interpret("08>:1-:v v *_$.@ \n" +
                "  ^    _$>\\:^  ^    _$>\\:^")).isEqualTo("40320");
    }

    @Test
    void shouldInterpretQuine() {
        assertThat(interpret("01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@")).isEqualTo("01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@");
    }

    @Test
    void shouldInterpretSieve() {
        assertThat(interpret("2>:3g\" \"-!v\\  g30          <\n" +
                " |!`\"&\":+1_:.:03p>03g+:\"&\"`|\n" +
                " @               ^  p3\\\" \":<\n" +
                "2 2345678901234567890123456789012345678")).isEqualTo("23571113171923293137");
    }
}
