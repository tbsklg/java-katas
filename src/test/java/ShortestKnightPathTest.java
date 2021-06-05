import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.lang.Integer.*;
import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortestKnightPathTest {

  public static int knight(String start, String finish) {
    return 1;
  }

  class Board {
    
  }

  @Test
  void integrationTests() {
    assertThat(knight("a1", "c1")).isEqualTo(2);
    assertThat(knight("a1", "f1")).isEqualTo(3);
    assertThat(knight("a1", "f3")).isEqualTo(3);
    assertThat(knight("a1", "f4")).isEqualTo(4);
    assertThat(knight("a1", "f7")).isEqualTo(5);
  }


  @Test
  void shouldExtractCoordinates() {
    assertThat(Coordinates.from("a1").getX()).isEqualTo(1);
    assertThat(Coordinates.from("b1").getX()).isEqualTo(2);
    assertThat(Coordinates.from("b2").getY()).isEqualTo(2);
    assertThat(Coordinates.from("c8").getX()).isEqualTo(3);
    assertThat(Coordinates.from("h8").getX()).isEqualTo(8);
    assertThat(Coordinates.from("h8").getY()).isEqualTo(8);
  }

  @Test
  void shouldThrowException() {
    assertThatThrownBy(()->Coordinates.from("i1")).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(()->Coordinates.from("a9")).isInstanceOf(IllegalStateException.class);
  }

  private static class Coordinates {

    private final int x;
    private final int y;

    private static final Map<Character, Integer> rowToX = Map.of(
            'a', 1,
            'b', 2,
            'c', 3,
            'd', 4,
            'e', 5,
            'f', 6,
            'g', 7,
            'h', 8
    );

    private Coordinates(int x, int y) {
      this.x = x;
      this.y = y;
    }

    private static Coordinates from(String field) {
      assert field.length() == 2;

      char rowName = field.charAt(0);
      int y = field.charAt(1) - '0';

      validateField(rowName, y);

      int x = rowToX.get(rowName);
      return new Coordinates(x, y);
    }

    private static void validateField(char rowName, int y) {
      if (!rowToX.containsKey(rowName) || y > 8) {
        throw new IllegalStateException();
      }
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }
  }
}
