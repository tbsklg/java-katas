package kyusix;

import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

public class Deletion {

  public static int[] deleteNth(int[] elements, int maxOccurrences) {
    return Delete.nth(maxOccurrences).from(elements);
  }

  static class Delete {
    private final int maxOccurences;

    private Delete(int maxOccurences) {
      this.maxOccurences = maxOccurences;
    }

    public static Delete nth(int maxOccurences) {
      return new Delete(maxOccurences);
    }

    public int[] from(int[] elements) {
      return from(elements, new int[]{});
    }

    private int[] from(int[] elements, int[] result) {
      if (elements.length == 0) return result;

      final var head = elements[0];
      final var occurrences = Arrays.stream(result).filter(x -> x == head).toArray().length;

      final var furtherElements = copyOfRange(elements, 1, elements.length);

      if (occurrences < maxOccurences) {
        final int[] nextResult = append(result, head);
        return from(furtherElements, nextResult);
      }

      return from(furtherElements, result);
    }

    private int[] append(int[] result, int head) {
      final var nextResult = new int[result.length + 1];
      System.arraycopy(result, 0, nextResult, 0, result.length);
      nextResult[result.length] = head;

      return nextResult;
    }
  }
}
