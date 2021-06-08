import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShortestKnightPathTest {

  private static final Node INITIAL_NODE = Node.with(null, null);

  public static int knight(String start, String finish) {
    final var queue = new ArrayDeque<Node>();
    final var startNode = Node.with(Coordinates.from(start), INITIAL_NODE);
    final var targetNode = Node.with(Coordinates.from(finish), null);

    queue.add(startNode);

    final var visited = new HashSet<Node>();

    var distance = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      final var currentNode = queue.remove();

      if (currentNode.equals(targetNode)) {
        var distanceFromCurrentNode = 0;
        var tmpNode = currentNode;

        while (tmpNode.getPrevious() != INITIAL_NODE) {
          distanceFromCurrentNode++;
          tmpNode = tmpNode.getPrevious();
        }

        if (distanceFromCurrentNode < distance) {
          distance = distanceFromCurrentNode;
        }
      } else {
        if (!visited.contains(currentNode)) {
          visited.add(currentNode);
          queue.addAll(currentNode.getNeighbours());
        }
      }
    }

    return distance;
  }

  private static class Node {
    private final Coordinates coordinates;

    private final Node previous;
    private final Set<Node> neighbours = new HashSet<>();

    private Node(Coordinates coordinates, Node previous) {
      this.previous = previous;
      this.coordinates = coordinates;
    }

    private static Node with(Coordinates coordinates, Node previous) {
      return new Node(coordinates, previous);
    }

    public Set<Node> getNeighbours() {
      final var currentX = this.getCoordinates().getX();
      final var currentY = this.getCoordinates().getY();

      addNeighbour(Node.with(Coordinates.from(currentX + 1, currentY + 2), this));
      addNeighbour(Node.with(Coordinates.from(currentX - 1, currentY + 2), this));

      addNeighbour(Node.with(Coordinates.from(currentX + 1, currentY - 2), this));
      addNeighbour(Node.with(Coordinates.from(currentX - 1, currentY - 2), this));

      addNeighbour(Node.with(Coordinates.from(currentX - 2, currentY + 1), this));
      addNeighbour(Node.with(Coordinates.from(currentX - 2, currentY - 1), this));

      addNeighbour(Node.with(Coordinates.from(currentX + 2, currentY + 1), this));
      addNeighbour(Node.with(Coordinates.from(currentX + 2, currentY - 1), this));

      return neighbours;
    }

    private void addNeighbour(Node node) {
      if (hasValidCoordinates(node.getCoordinates())) {
        neighbours.add(node);
      }
    }

    private boolean hasValidCoordinates(Coordinates coordinates) {
      final int x = coordinates.getX();
      final int y = coordinates.getY();

      return !(x < 1) && !(x > 8) && !(y < 1) && !(y > 8);
    }

    public Coordinates getCoordinates() {
      return coordinates;
    }

    public Node getPrevious() {
      return previous;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node node = (Node) o;
      return Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
      return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
      return "Node{" +
              "coordinates=" + coordinates +
              '}';
    }
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


  @Test
  void integrationTests() {
    assertThat(knight("a1", "c1")).isEqualTo(2);
    assertThat(knight("a1", "f1")).isEqualTo(3);
    assertThat(knight("a1", "f3")).isEqualTo(3);
    assertThat(knight("a1", "f4")).isEqualTo(4);
    assertThat(knight("a1", "f7")).isEqualTo(5);
    assertThat(knight("f3", "h2")).isEqualTo(1);
  }

  @Test
  void shouldCalculateCoordinates() {
    assertThat(Coordinates.from("a1").getX()).isEqualTo(1);
    assertThat(Coordinates.from("b1").getX()).isEqualTo(2);
    assertThat(Coordinates.from("b2").getY()).isEqualTo(2);
    assertThat(Coordinates.from("c8").getX()).isEqualTo(3);
    assertThat(Coordinates.from("h8").getX()).isEqualTo(8);
    assertThat(Coordinates.from("h8").getY()).isEqualTo(8);
  }

  @Test
  void shouldThrowException() {
    assertThatThrownBy(() -> Coordinates.from("i1")).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> Coordinates.from("a9")).isInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> Coordinates.from("a92")).isInstanceOf(AssertionError.class);
  }

  @Test
  void shouldGetNeighboarsForLowerLeftEdge() {
    final var node = Node.with(Coordinates.from("a1"), null);
    final var firstNeighbour = Node.with(Coordinates.from("b3"), null);
    final var secondNeighbour = Node.with(Coordinates.from("c2"), null);

    assertThat(node.getNeighbours())
            .containsExactlyInAnyOrder(firstNeighbour, secondNeighbour);
  }

  @Test
  void shouldGetNeighboarsForLowerRightEdge() {
    final var node = Node.with(Coordinates.from("h1"), null);
    final var firstNeighbour = Node.with(Coordinates.from("f2"), null);
    final var secondNeighbour = Node.with(Coordinates.from("g3"), null);

    assertThat(node.getNeighbours())
            .containsExactlyInAnyOrder(firstNeighbour, secondNeighbour);
  }

  @Test
  void shouldGetNeighboarsForUpperRightEdge() {
    final var node = Node.with(Coordinates.from("h8"), null);
    final var firstNeighbour = Node.with(Coordinates.from("f7"), null);
    final var secondNeighbour = Node.with(Coordinates.from("g6"), null);

    assertThat(node.getNeighbours())
            .containsExactlyInAnyOrder(firstNeighbour, secondNeighbour);
  }

  @Test
  void shouldGetNeighboarsForUpperLeftEdge() {
    final var node = Node.with(Coordinates.from("a8"), null);
    final var firstNeighbour = Node.with(Coordinates.from("c7"), null);
    final var secondNeighbour = Node.with(Coordinates.from("b6"), null);

    assertThat(node.getNeighbours())
            .containsExactlyInAnyOrder(firstNeighbour, secondNeighbour);
  }

  @Test
  void shouldGetAllNeighbours() {
    final var node = Node.with(Coordinates.from("d5"), null);

    final var first = Node.with(Coordinates.from("c7"), node);
    final var second = Node.with(Coordinates.from("e7"), node);

    final var third = Node.with(Coordinates.from("b6"), node);
    final var fourth = Node.with(Coordinates.from("b4"), node);

    final var fifth = Node.with(Coordinates.from("c3"), node);
    final var sixth = Node.with(Coordinates.from("e3"), node);

    final var seventh = Node.with(Coordinates.from("f4"), node);
    final var eighth = Node.with(Coordinates.from("f6"), node);

    assertThat(node.getNeighbours())
            .containsExactlyInAnyOrder(first, second, third, fourth, fifth, sixth, seventh, eighth);
  }

}

