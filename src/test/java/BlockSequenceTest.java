import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class BlockSequenceTest {

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 1",
            "3, 2",
            "4, 1",
            "5, 2",
            "6, 3",
            "10, 4",
            "15, 5",
            "45, 9",
            "46, 1",
            "47, 2",
            "55, 1",
            "56, 0",
            "66, 1",
            "67, 0",
            "68, 1",
            "69, 1",
            "70, 1",
            "99, 2",
            "100, 1",
            "190, 1",
            "2100, 2",
            "3100, 2",
            "123456, 6",
            "123456789, 3",
            "999999999999999999, 4",
    })
    void shouldCalculate(long position, int expected) {
        assertThat(calculate(position)).isEqualTo(expected);
    }

    private int calculate(long position) {
//        long offset = calculateOffset(position);

        return calculateNthDigit(position, 1, 9, 1);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1",
            "9,9",
            "10,1",
            "11,0",
            "41, 5",
            "188, 9",
            "189, 9",
            "190, 1", //100
            "191, 0", //101
            "192, 0", //102
            "354, 4",
            "2886, 8",
            "2887, 9",
            "2888, 9",
            "2889, 9",
            "2890, 1", //1000
            "2891, 0",
            "38886, 9", //9999
            "38887, 9", //9999
            "38888, 9", //9999
            "38889, 9", //9999
            "38890, 1", //10000
            "38891, 0",
            "38892, 0",
            "38893, 0",
            "38894, 0",
            "488889, 9", //99999
            "488890, 1", //100000
            "5888889, 9", //999999
            "1000000000000000000, 7",
    })
    void shouldReturnNthDigit(long position, int expected) {
        assertThat(calculateNthDigit(position, 1, 9, 1)).isEqualTo(expected);
    }

    private int calculateNthDigit(long position, long low, long high, int precision) {
        // calculate offset
        boolean isBlockSequence = position >= low && position <= high;

        if (isBlockSequence) {

            long result = position - low + 1;

            if (position < 10) return (int) position;

            if (result % precision == 0) {
                result = result / (precision) % 10 - 1;

                if (result < 0) return 9;

                return (int) result;
            }

            if (result % precision == 1) {
                return (int) (result / (precision * Math.pow(10, precision - 1))) + 1;
            }

            return (int) (result / (precision * Math.pow(10, precision - result % precision))) % 10;
        } else {
            int nextPrecision = precision + 1;
            long nextLow = high + 1;
            long nextHigh = (long) Math.pow(10, precision) * nextPrecision * 9 + high;

            return calculateNthDigit(position, nextLow, nextHigh, nextPrecision);
        }
    }

}
