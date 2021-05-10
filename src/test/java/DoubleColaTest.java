import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleColaTest {

    @Test
    void shouldReturnNameForQueueWithFivePersons() {
        assertThat(DoubleCola.whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 1)).isEqualTo("Sheldon");
        assertThat(DoubleCola.whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 10)).isEqualTo("Penny");
        assertThat(DoubleCola.whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 63)).isEqualTo("Rajesh");
        assertThat(DoubleCola.whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 12079)).isEqualTo("Sheldon");
        assertThat(DoubleCola.whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 28643950)).isEqualTo("Leonard");
        assertThat(DoubleCola.whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 723070295)).isEqualTo("Sheldon");
    }
}
