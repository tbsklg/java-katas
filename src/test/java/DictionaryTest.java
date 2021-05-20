import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Find the most similar word based on a dictionary.
 * This algorithm is based on the Levenshtein distance.
 *
 * @see <a href="https://www.codewars.com/kata/5259510fc76e59579e0009d4">https://www.codewars.com/kata/5259510fc76e59579e0009d4</a>
 */
public class DictionaryTest {

  @Test
  void shouldCalculateMostSimilar() {
    final var dict = new Dictionary(new String[]{"cherry", "pineapple", "melon", "strawberry", "raspberry"});
    assertThat(dict.findMostSimilar("strawberry")).isEqualTo("strawberry");
    assertThat(dict.findMostSimilar("berry")).isEqualTo("cherry");
  }

  @Test
  void shouldCalculateMostSimilarForDifferentDict() {
    final var dict = new Dictionary(new String[]{"javascript", "java", "ruby", "php", "python", "coffeescript"});
    assertThat(dict.findMostSimilar("heaven")).isEqualTo("java");
    assertThat(dict.findMostSimilar("javascript")).isEqualTo("javascript");
  }
}
