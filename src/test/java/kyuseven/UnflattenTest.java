package kyuseven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class UnflattenTest {

  @Test
  public void basicTests() {

    int[] input = new int[]{3, 5, 2, 1};
    Object[] expected = new Object[]{new int[]{3, 5, 2}, 1};
    Object[] result = Unflatten.unflatten(input);
    assertArrayEquals(expected, result);

    input = new int[]{1, 4, 5, 2, 1, 2, 4, 5, 2, 6, 2, 3, 3};
    expected = new Object[]{1, new int[]{4, 5, 2, 1}, 2, new int[]{4, 5, 2, 6}, 2, new int[]{3, 3}};
    assertArrayEquals(expected, Unflatten.unflatten(input));
  }
}