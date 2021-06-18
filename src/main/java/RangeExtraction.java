import java.util.StringJoiner;

public class RangeExtraction {
  static String rangeExtraction(int[] arr) {
    final var result = new StringJoiner(",");

    int i1 = 0;
    int i2 = 0;

    while (i1 < arr.length) {
      do {
        i2++;
      } while (i2 < arr.length && arr[i2 - 1] + 1 == arr[i2]);
      if (i2 - i1 > 2) {
        final var minus = new StringJoiner("-");
        minus.add(String.valueOf(arr[i1]));
        minus.add(String.valueOf(arr[i2 - 1]));
        result.merge(minus);
        i1 = i2;
      } else {
        for (; i1 < i2; i1++)
          result.add(String.valueOf(arr[i1]));
      }
    }

    return result.toString();
  }
}
