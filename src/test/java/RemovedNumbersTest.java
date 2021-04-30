import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * A friend of mine takes the sequence of all numbers from 1 to n (where n > 0).
 * Within that sequence, he chooses two numbers, a and b.
 * He says that the product of a and b should be equal to the sum of all numbers in the sequence, excluding a and b.
 * Given a number n, could you tell me the numbers he excluded from the sequence?
 * The function takes the parameter: n (n is always strictly greater than 0) and returns an array
 * <p>
 * It happens that there are several possible (a, b). The function returns an empty array (or an empty string) if no possible numbers are found which will prove that my friend has not told the truth! (Go: in this case return nil).
 */
public class RemovedNumbersTest {

    @Test
    void shouldReturnEmptyList() {
        assertThat(removeNb(1)).isEmpty();
        assertThat(removeNb(2)).isEmpty();
        assertThat(removeNb(20)).isEmpty();
    }

    @Test
    void shouldReturnPossibleNumbers() {
        assertThat(removeNb(10)).containsAll(Arrays.asList(new long[]{6, 7}, new long[]{7, 6}));
        assertThat(removeNb(26)).containsAll(Arrays.asList(new long[]{15, 21}, new long[]{21, 15}));
        assertThat(removeNb(1000003)).containsAll(Arrays.asList(new long[]{550320L, 908566L}, new long[]{908566L, 550320L}, new long[]{559756L, 893250L}, new long[]{893250L, 559756L}));
    }

    private List<long[]> removeNb(long n) {
        var sum = n * (n + 1) / 2;
        var result = new ArrayList<long[]>();

        long row = 1;
        long column = n;

        while(column > row) {
            var remaining = (sum - (row + column)) - row * column;

            if (remaining == 0) {
                result.add(new long[]{row, column});
                result.add(new long[]{column, row});
            }

            if (remaining > 0) {
                row++;
            } else {
                column--;
            }
        }

        result.sort(Comparator.comparingLong(o -> o[0]));
        return result;
    }


}
