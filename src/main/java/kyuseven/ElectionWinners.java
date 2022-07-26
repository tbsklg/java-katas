package kyuseven;

import static java.util.Arrays.stream;

public class ElectionWinners {

  static int find(final int[] votes, final int k) {
    if (k == 0) return findWinnersFromVotes(votes);

    return findWinnersFromVotesAndAdditionalVotes(votes, k);
  }

  private static int findWinnersFromVotes(int[] votes) {
    final var maximumVote = stream(votes).max().orElse(0);
    final var winners =  stream(votes).filter(v -> v == maximumVote).count();

    return winners == 1 ? 1 : 0;
  }

  private static int findWinnersFromVotesAndAdditionalVotes(int[] votes, int k) {
    final var maximumVote = stream(votes).max().orElse(0);

    return (int) stream(votes).filter(v -> v + k > maximumVote).count();
  }
}
