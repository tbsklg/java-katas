package kyusix;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DecoderTest {

  @Test
  public void basicTests() {
    test("no encoding", new int[]{1, 2, 5, 7}, "1,2,5,7");
    test("2 identical numbers", new int[]{1, 2, 2, 3}, "1,2*2,3");
    test("4 identical numbers", new int[]{163, 163, 163, 163, 162, 168}, "163*4,162,168");
    test("3 consecutive numbers, ascending", new int[]{1, 3, 4, 5, 7}, "1,3-5,7");
    test("3 consecutive numbers, descending", new int[]{1, 5, 4, 3, 7}, "1,5-3,7");
    test("4 consecutive numbers with negative number, descending", new int[]{1, 1, 0, -1, -2, -3, 7}, "1,1--3,7");
    test("3 numbers with same interval, descending", new int[]{1, 10, 8, 6, 7}, "1,10-6/2,7");
    test("3 numbers with same interval, ascending", new int[]{1, 8, 11, 14, 17, 7}, "1,8-17/3,7");
    test("3 numbers with same interval with negative number, descending", new int[]{1, 1, -1, -3, -5, 7}, "1,1--5/2,7");
  }

  @Test
  void shouldThrowExceptionForUnsupportedDecoding() {
    final var toDecode = "1,8-17$3,7";
    assertThatThrownBy(() -> new Decoder().uncompress(toDecode)).isInstanceOf(UnsupportedOperationException.class);
  }

  private void test(String description, int[] raw, String encoded) {
    assertThat(new Decoder().uncompress(encoded)).describedAs(description).isEqualTo(raw);
  }
}