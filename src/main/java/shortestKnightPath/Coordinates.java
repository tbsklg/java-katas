package shortestKnightPath;

import java.util.Map;
import java.util.Objects;

public class Coordinates {
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

  public static Coordinates from(String coordinates) {
    assert coordinates.length() == 2;

    char rowName = coordinates.charAt(0);
    int y = coordinates.charAt(1) - '0';

    validate(rowName, y);

    int x = rowToX.get(rowName);
    return new Coordinates(x, y);
  }

  public static Coordinates from(int x, int y) {
    return new Coordinates(x, y);
  }

  private static void validate(char rowName, int y) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coordinates that = (Coordinates) o;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Coordinates{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
