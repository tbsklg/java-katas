package kyufive;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class ValidParenthesesTest {

    @Test
    public void sampleTest() {
        assertThat(ValidParentheses.validParentheses("()")).isTrue();
        assertThat(ValidParentheses.validParentheses("())")).isFalse();
        assertThat(ValidParentheses.validParentheses("32423(sgsdg)")).isTrue();
        assertThat(ValidParentheses.validParentheses("(dsgdsg))2432")).isFalse();
        assertThat(ValidParentheses.validParentheses("adasdasfa")).isTrue();
    }
}
