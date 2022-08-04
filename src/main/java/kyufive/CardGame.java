package kyufive;

public class CardGame {

  public static long cardGame(long cards) {
    return play(cards, 0);
  }

  private static long play(long cards, long aliceTaken) {
    if (cards == 0) return aliceTaken;

    final var alice = alice(cards);
    final var bob = bob(alice.remainingCards);

    return play(bob.remainingCards, alice.takenCards + aliceTaken);
  }

  private static Move alice(long cards) {
    return strategy(cards);
  }
  private static Move bob(long cards) {
    return strategy(cards);
  }

  private static Move strategy(long cards) {
    if (cards == 0) return Move.with(0,0);

    final var even = cards % 2 == 0;
    final var nextOdd = (cards - cards / 2) % 2 != 0;

    if (even && (nextOdd || cards == 4)) {
      return Move.with(cards / 2, cards / 2);
    }

    return Move.with(1, cards - 1);
  }

  record Move(long takenCards, long remainingCards) {
    public static Move with(long takenCards, long remainingCards) {
      return new Move(takenCards, remainingCards);
    }
  }
}
