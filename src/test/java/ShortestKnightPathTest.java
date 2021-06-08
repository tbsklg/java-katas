import org.junit.jupiter.api.Test;
import shortestKnightPath.Coordinates;
import shortestKnightPath.Node;
import shortestKnightPath.ShortestKnightPath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ShortestKnightPathTest {
  
  @Test
  void integrationTests() {
    assertThat(ShortestKnightPath.knight("a1", "c1")).isEqualTo(2);
    assertThat(ShortestKnightPath.knight("a1", "f1")).isEqualTo(3);
    assertThat(ShortestKnightPath.knight("a1", "f3")).isEqualTo(3);
    assertThat(ShortestKnightPath.knight("a1", "f4")).isEqualTo(4);
    assertThat(ShortestKnightPath.knight("a1", "f7")).isEqualTo(5);
    assertThat(ShortestKnightPath.knight("f3", "h2")).isEqualTo(1);
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

