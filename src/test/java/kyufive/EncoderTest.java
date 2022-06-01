package kyufive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncoderTest {

  @Test
  public void identicalNumbers() {
    test("2 identical numbers", new int[]{1, 2, 2, 3}, "1,2*2,3");
    test("2 identical numbers", new int[]{1, 2, 2, 2, 3, 3}, "1,2*3,3*2");
//    test("3 numbers with same interval, descending", new int[]{1, 10, 8, 6, 7}, "1,10-6/2,7");
  }

  @Test
  public void consecutiveNumbers() {
    test("3 consecutive numbers, ascending", new int[]{1, 3, 4, 5, 7}, "1,3-5,7");
    test("3 consecutive numbers, descending", new int[]{1, 5, 4, 3, 7}, "1,5-3,7");
  }

  private void test(String description, int[] raw, String encoded) {
    assertEquals(encoded, new Encoder().compress(raw), description);
  }
}
