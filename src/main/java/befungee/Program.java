package befungee;

import java.util.Arrays;
import java.util.Comparator;

import static befungee.ProgramDirection.RIGHT;

public class Program {

    private static final String BY_NEW_LINE = "\\n";

    private final Character[][] code;
    private int row = 0;
    private int column = 0;

    private ProgramDirection dir = RIGHT;

    private Program(String code) {
        this.code = create(code);
    }

    public static Program from(String code) {
        return new Program(code);
    }

    private static Character[][] create(String code) {
        var lines = code.split(BY_NEW_LINE);

        final var maxLineLength = Arrays.stream(lines)
                .map(String::length)
                .max(Comparator.naturalOrder())
                .orElse(0);

        var program = new Character[lines.length][maxLineLength];

        for (int i = 0; i < lines.length; i++) {
            final var chars = lines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                program[i][j] = chars[j];
            }
        }

        return program;
    }

    public void setDirection(ProgramDirection dir) {
        this.dir = dir;
    }

    public Character currentChar() {
        return code[row][column];
    }

    public int getCodeAt(int y, int x) {
        return code[y][x];
    }

    public void next() {
        if (this.dir == ProgramDirection.DOWN) {
            row++;
        } else if (dir == ProgramDirection.UP) {
            row--;
        } else if (dir == ProgramDirection.LEFT) {
            column--;
        } else {
            column++;
        }
    }

    public void change(int x, int y, int v) {
        code[y][x] = (char) v;
    }
}
