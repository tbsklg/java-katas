import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class SquashTheBugTest {

    public static int findLongest(final String str) {
        return Arrays.stream(str.split(" "))
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    @Test
    public void shouldReturnLengthOfLongestWord() {
        assertThat(findLongest("A")).isEqualTo(1);
        assertThat(findLongest("The")).isEqualTo(3);
        assertThat(findLongest("The quick")).isEqualTo(5);
        assertThat(findLongest("The quick white fox jumped around the massive dog")).isEqualTo(7);
        assertThat(findLongest("Take me to tinseltown with you")).isEqualTo(10);
        assertThat(findLongest("Sausage chops")).isEqualTo(7);
        assertThat(findLongest("Wind your body and wiggle your belly")).isEqualTo(6);
        assertThat(findLongest("Lets all go on holiday")).isEqualTo(7);
    }
}
