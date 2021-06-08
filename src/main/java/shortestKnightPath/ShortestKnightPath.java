package shortestKnightPath;

import java.util.ArrayDeque;
import java.util.HashSet;

public class ShortestKnightPath {
  public static final Node INITIAL_NODE = Node.with(null, null);

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
}
