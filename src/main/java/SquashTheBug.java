import java.util.Arrays;

public class SquashTheBug {
    public static int findLongest(final String str) {
        return Arrays.stream(str.split(" "))
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }
}
