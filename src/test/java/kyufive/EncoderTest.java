package kyufive;

import org.junit.jupiter.api.Test;

import java.util.List;

import static kyufive.Encoder.MyList;
import static kyufive.Encoder.Pair;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EncoderTest {

  @Test
  public void identicalNumbers() {
    test("2 identical numbers", new int[]{1, 2, 2, 3}, "1,2*2,3");
    test("2 identical numbers", new int[]{1, 2, 2, 2, 3, 3}, "1,2*3,3*2");
    test("3 numbers with same interval, descending", new int[]{1, 10, 8, 6, 7}, "1,10-6/2,7");
  }

  @Test
  public void consecutiveNumbers() {
    test("3 consecutive numbers, ascending", new int[]{1, 3, 4, 5, 7}, "1,3-5,7");
    test("3 consecutive numbers, descending", new int[]{1, 5, 4, 3, 7}, "1,5-3,7");
  }

  private void test(String description, int[] raw, String encoded) {
    assertEquals(encoded, new Encoder().compress(raw), description);
  }

  @Test
  void randomTests() {
    test("3 consecutive numbers, ascending", new int[]{120, 104, 101, 98, 114, 123, 78, 76, 82, 123, 123, 135, 142, 68, 63, 63, 63, 70, 69, 68, 67, 101, 22, 22, 22, 22, 22, 43, 117, 118, 119}, "120,104-98/3,114,123,78,76,82,123*2,135,142,68,63*3,70-67,101,22*5,43,117-119");
    test("3 consecutive numbers, ascending", new int[]{81, 80, 79, 78, 77, 170, 170, 55, 53, 51, 49, 128, 125, 122, 24, 27, 24, 23, 22, 21, 176, 69, 81, 126, 28, 27, 26, 11, 31, 8, 53, 27, 189, 189, 189, 189, 189, 112, 112, 11, 5, 6, 7, 8, 9}, "81-77,170*2,55-49/2,128-122/3,24,27,24-21,176,69,81,126,28-26,11,31,8,53,27,189*5,112*2,11,5-9");
  }

  @Test
  void shouldProvideBasicListOperations() {
    Encoder.MyList<Integer> l = MyList.from(List.of(1, 2, 3, 4));

    assertThat(l.head()).isEqualTo(1);
    assertThat(l.last()).isEqualTo(4);
    assertThat(l.append(3)).isEqualTo(MyList.from(List.of(1, 2, 3, 4, 3)));
    assertThat(l.prepend(3)).isEqualTo(MyList.from(List.of(3, 1, 2, 3, 4)));
    assertThat(l.init()).isEqualTo(MyList.from(List.of(1, 2, 3)));
    assertThat(l.tail()).isEqualTo(MyList.from(List.of(2, 3, 4)));
    assertThat(l.length()).isEqualTo(4);
  }

  @Test
  void shouldZipToLists() {
    final var from = MyList.from(List.of(1, 2, 3, 4));
    final var other = MyList.from(List.of(5, 4, 3, 2));

    assertThat(from.zip(other)).isEqualTo(MyList.from(List.of(Pair.with(1, 5), Pair.with(2, 4), Pair.with(3, 3), Pair.with(4, 2))));

    final var shorter = MyList.from(List.of(5, 4));
    assertThat(from.zip(shorter)).isEqualTo(MyList.from(List.of(Pair.with(1, 5), Pair.with(2, 4))));

    final var longer = MyList.from(List.of(6, 5, 4, 3, 2));
    assertThat(from.zip(longer)).isEqualTo(MyList.from(List.of(Pair.with(1, 6), Pair.with(2, 5), Pair.with(3, 4), Pair.with(4, 3))));
  }

  @Test
  void shouldAppendAll() {
    final var from = MyList.from(List.of(1, 2, 3, 4));
    final var other = MyList.from(List.of(5, 4, 3, 2));
    assertThat(from.appendAll(other)).isEqualTo(MyList.from(List.of(1, 2, 3, 4, 5, 4, 3, 2)));
  }
}
