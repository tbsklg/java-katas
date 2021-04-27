import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleColaTest {

    public static String whoIsNext(String[] names, int n) {
        return findName(names, n, 0, 0);
    }

    private static String findName(String[] names, int n, int low, int h) {
        if (n < names.length) return names[n - 1];

        int currentLow = low + 1;
        int high = names.length * (int) Math.pow(2, h) + low;

        if (n >= currentLow && n <= high) {
            final int position = (int) ((n - (currentLow - 1))/ Math.pow(2, h)) + 1;
            return names[position - 1];
        }

        return findName(names, n, high, ++h);
    }

    @Test
    void shouldReturnNameForQueueWithOnePerson() {
        assertThat(whoIsNext(new String[]{"John"}, 1)).isEqualTo("John");
    }

    @Test
    void shouldReturnNameForQueueWithTwoPersons() {
        assertThat(whoIsNext(new String[]{"John", "Pete"}, 1)).isEqualTo("John");
        assertThat(whoIsNext(new String[]{"John", "Pete"}, 2)).isEqualTo("Pete");
        assertThat(whoIsNext(new String[]{"John", "Pete"}, 3)).isEqualTo("John");
        assertThat(whoIsNext(new String[]{"John", "Pete"}, 4)).isEqualTo("John");
    }

    @Test
    void shouldReturnNameForQueueWithFivePersons() {
        assertThat(whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 1)).isEqualTo("Sheldon");
        assertThat(whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 10)).isEqualTo("Penny");
        assertThat(whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 63)).isEqualTo("Rajesh");
        assertThat(whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 12079)).isEqualTo("Sheldon");
        assertThat(whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 28643950)).isEqualTo("Leonard");
        assertThat(whoIsNext(new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard"}, 723070295)).isEqualTo("Sheldon");
    }
}
