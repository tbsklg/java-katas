package kyufive;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class ValidateParenthesesTest {

    @Test
    public void sampleTest() {
        assertThat(ValidateParentheses.validParentheses("()")).isTrue();
        assertThat(ValidateParentheses.validParentheses("())")).isFalse();
        assertThat(ValidateParentheses.validParentheses("32423(sgsdg)")).isTrue();
        assertThat(ValidateParentheses.validParentheses("(dsgdsg))2432")).isFalse();
        assertThat(ValidateParentheses.validParentheses("adasdasfa")).isTrue();
        assertThat(ValidateParentheses.validParentheses("(((((((())))))))")).isTrue();
    }
}
