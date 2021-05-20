import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RemovedNumbers {

  public static List<long[]> removeNb(long n) {
    var sum = n * (n + 1) / 2;
    var result = new ArrayList<long[]>();

    long row = 1;
    long column = n;

    while (column > row) {
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
