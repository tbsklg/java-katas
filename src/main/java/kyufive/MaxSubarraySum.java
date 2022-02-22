package kyufive;

public class MaxSubarraySum {

  // see Kadane's algorithm
  public static int sequence(int[] arr) {
    int bestSum = 0;
    int currentSum = 0;

    for (int i : arr) {
      currentSum = Math.max(0, currentSum + i);
      bestSum = Math.max(bestSum, currentSum);
    }

    return bestSum;
  }
}
