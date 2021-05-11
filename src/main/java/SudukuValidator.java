import java.util.HashSet;

public class SudukuValidator {
    public static boolean check(int[][] sudoku) {
        for (int i = 0; i < 9; i++) {
            final var hasValidRows = hasValidRows(i, sudoku);
            final var hasValidColumns = hasValidColumns(i, sudoku);

            if (!hasValidRows || !hasValidColumns) {
                return false;
            }
        }

        return hasValidSubSquares(sudoku);
    }

    static boolean hasValidSubSquares(int[][] sudoku) {
        for (int col = 0; col < 9; col += 3) {
            for (int row = 0; row < 9; row += 3) {
                var uniques = new HashSet<Integer>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        final var field = sudoku[r][c];
                        if (validateField(field) || uniques.contains(field)) {
                            return false;
                        }

                        uniques.add(field);
                    }
                }
            }
        }
        return true;
    }

    static boolean hasValidRows(int row, int[][] sudoku) {
        final var uniques = new HashSet<Integer>();

        for (int i = 0; i < 9; i++) {
            final var field = sudoku[row][i];
            if (validateField(field) || uniques.contains(field)) {
                return false;
            }

            uniques.add(field);
        }

        return true;
    }

    static boolean hasValidColumns(int col, int[][] sudoku) {
        final var uniques = new HashSet<Integer>();

        for (int i = 0; i < 9; i++) {
            var field = sudoku[i][col];
            if (validateField(field) || uniques.contains(field)) {
                return false;
            }

            uniques.add(field);
        }

        return true;
    }

    static boolean validateField(int field) {
        return field <= 0 || field > 9;
    }
}
