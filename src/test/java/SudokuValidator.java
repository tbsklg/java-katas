import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class SudokuValidator {

    @Test
    void shouldValidate() {
        int[][] sudoku = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        assertThat(check(sudoku)).isTrue();
    }

    private boolean check(int[][] sudoku) {
        return false;
    }

    @Test
    void shouldReturnTrueForDistinctNumbers() {
        assertThat(hasDistinctValues(new int[]{1})).isTrue();
        assertThat(hasDistinctValues(new int[]{1, 2})).isTrue();
        assertThat(hasDistinctValues(new int[]{1, 2, 3, 4, 5, 6})).isTrue();
    }

    @Test
    void shouldReturnTrueForDistinctNumbersInTwoDim() {
        assertThat(hasDistinctValues(new int[][]{{1, 2}})).isTrue();
        assertThat(hasDistinctValues(new int[][]{{1, 2, 3}, {4, 5, 6}})).isTrue();
    }

    @Test
    void shouldReturnFalseForNoDistinctNumbersInTwoDim() {
        assertThat(hasDistinctValues(new int[][]{{1, 1}})).isFalse();
        assertThat(hasDistinctValues(new int[][]{{1, 2, 3}, {3, 4, 5}})).isFalse();
    }

    @Test
    void shouldReturnFalseForNoDistinctNumbers() {
        assertThat(hasDistinctValues(new int[]{1, 1})).isFalse();
        assertThat(hasDistinctValues(new int[]{1, 2, 3, 4, 4})).isFalse();
    }

    private boolean hasDistinctValues(int[][] arr) {
        var uniques = new HashSet<Integer>();

        for (int[] values : arr) {
            for (int value : values) {
                if (uniques.contains(value)) {
                    return false;
                }
                uniques.add(value);
            }
        }

        return true;
    }

    private boolean hasDistinctValues(int[] arr) {
        var uniques = new HashSet<Integer>();

        for (var value : arr) {
            if (uniques.contains(value)) {
                return false;
            }
            uniques.add(value);
        }

        return true;
    }
}
