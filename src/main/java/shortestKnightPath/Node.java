package shortestKnightPath;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
  private final Coordinates coordinates;

  private final Node previous;
  private final Set<Node> neighbours = new HashSet<>();

  private Node(Coordinates coordinates, Node previous) {
    this.previous = previous;
    this.coordinates = coordinates;
  }

  public static Node with(Coordinates coordinates, Node previous) {
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
