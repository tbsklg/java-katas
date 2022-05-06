package kyusix;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Arrays.copyOfRange;
import static kyusix.MazeRunner.Direction.*;
import static kyusix.MazeRunner.Point.*;

public class MazeRunner {

  public static String mazeRunner(int[][] maze, char[] directions) {
    Maze labyrinth = Maze.from(maze);
    return Runner.in(labyrinth).navigate(directions);
  }

  static class Runner {
    private final Maze maze;

    private Runner(Maze maze) {
      this.maze = maze;
    }

    public static Runner in(Maze maze) {
      return new Runner(maze);
    }

    public String navigate(char[] directions) {
      return navigate(maze.start(), directions);
    }

    private String navigate(Position current, char[] directions) {
      if (this.maze.position(current) == FINISH) return FINISH.value();
      if (this.maze.position(current) == DEAD) return DEAD.value();
      if (directions.length == 0) return LOST.value();

      final var currentDirection = Direction.from(directions[0]);
      final var nextCoordinate = Move.from(current).towards(currentDirection);

      final var furtherDirections = copyOfRange(directions, 1, directions.length);

      return navigate(nextCoordinate, furtherDirections);
    }
  }

  static class Move {
    private final Position position;
    private final Map<Direction, Function<Position, Position>> directions = Map.of(
            NORTH, c -> Position.from(c.row() - 1, c.column()),
            SOUTH, c -> Position.from(c.row() + 1, c.column()),
            WEST, c -> Position.from(c.row(), c.column() - 1),
            EAST, c -> Position.from(c.row(), c.column() + 1)
    );

    private Move(Position position) {
      this.position = position;
    }

    public static Move from(Position current) {
      return new Move(current);
    }

    public Position towards(Direction direction) {
      return directions.get(direction).apply(this.position);
    }
  }

  static class Maze {
    private final int[][] board;
    private final int dimension;

    private Maze(int[][] board) {
      this.board = board;
      this.dimension = this.board.length;
    }

    public static Maze from(int[][] raw) {
      return new Maze(raw);
    }

    public Position start() {
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] == 2) {
            return Position.from(i, j);
          }
        }
      }

      throw new RuntimeException("No start no fun!");
    }

    public Point position(Position position) {
      if (dead(position)) return DEAD;
      if (finish(position)) return FINISH;

      return LOST;
    }

    private boolean dead(Position position) {
      return this.outside(position) || this.wall(position);
    }

    private boolean outside(Position position) {
      return position.row < 0 || position.row >= dimension
              || position.column < 0 || position.column >= dimension;
    }

    private boolean wall(Position position) {
      return this.board[position.row][position.column] == 1;
    }

    private boolean finish(Position position) {
      return this.board[position.row][position.column] == 3;
    }
  }

  enum Point {
    DEAD("Dead"), FINISH("Finish"), LOST("Lost");

    private final String value;

    Point(String raw) {
      this.value = raw;
    }

    public String value() {
      return this.value;
    }
  }

  enum Direction {
    NORTH, WEST, SOUTH, EAST;

    private static final Map<Character, Direction> valueToDirection = Map.of(
            'N', NORTH,
            'S', SOUTH,
            'W', WEST,
            'E', EAST
    );

    public static Direction from(char raw) {
      return valueToDirection.get(raw);
    }
  }

  static class Position {
    private final int row;
    private final int column;

    private Position(int row, int column) {
      this.row = row;
      this.column = column;
    }

    public static Position from(int row, int column) {
      return new Position(row, column);
    }

    public int row() {
      return this.row;
    }

    public int column() {
      return this.column;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Position that = (Position) o;
      return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, column);
    }

    @Override
    public String toString() {
      return "Coordinate{" +
              "row=" + row +
              ", column=" + column +
              '}';
    }
  }
}
