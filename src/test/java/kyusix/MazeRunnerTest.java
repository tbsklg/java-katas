package kyusix;

import org.junit.jupiter.api.Test;

import static kyusix.MazeRunner.Maze.*;
import static kyusix.MazeRunner.mazeRunner;
import static org.assertj.core.api.Assertions.assertThat;

class MazeRunnerTest {

  private static final int[][] maze = {
          {1, 1, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 0, 0, 3},
          {1, 0, 1, 0, 1, 0, 1},
          {0, 0, 1, 0, 0, 0, 1},
          {1, 0, 1, 0, 1, 0, 1},
          {1, 0, 0, 0, 0, 0, 1},
          {1, 2, 1, 0, 1, 0, 1}
  };

  @Test
  void shouldReturnFinish() {
    assertThat(mazeRunner(maze, new char[]{'N', 'N', 'N', 'N', 'N', 'E', 'E', 'E', 'E', 'E'})).isEqualTo("Finish");
    assertThat(mazeRunner(maze, new char[]{'N', 'N', 'N', 'N', 'N', 'E', 'E', 'S', 'S', 'E', 'E', 'N', 'N', 'E'})).isEqualTo("Finish");
    assertThat(mazeRunner(maze, new char[]{'N', 'N', 'N', 'N', 'N', 'E', 'E', 'E', 'E', 'E', 'W', 'W'})).isEqualTo("Finish");
  }

  @Test
  void shouldReturnDead() {
    assertThat(mazeRunner(maze, new char[]{'N', 'N', 'N', 'W', 'W'})).isEqualTo("Dead");
    assertThat(mazeRunner(maze, new char[]{'N', 'N', 'N', 'N', 'N', 'E', 'E', 'S', 'S', 'S', 'S', 'S', 'S'})).isEqualTo("Dead");
  }

  @Test
  void shouldReturnLost() {
    assertThat(mazeRunner(maze, new char[]{'N', 'E', 'E', 'E', 'E'})).isEqualTo("Lost");
  }

  @Test
  void shouldFindStart() {
    assertThat(from(maze).start()).isEqualTo(MazeRunner.Position.from(6,1));
  }
}