package kyufive;

import org.junit.jupiter.api.Test;

import static kyufive.DirReduction.dirReduc;
import static org.assertj.core.api.Assertions.assertThat;

class DirectionReductionTest {

  @Test
  public void testSimpleDirReduc() {
    assertThat(dirReduc(new String[]{"NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST"}))
            .isEqualTo(new String[]{"WEST"});
    assertThat(
            dirReduc(new String[]{"NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH"}))
            .isEqualTo(new String[]{});
    assertThat(dirReduc(new String[]{"NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST", "NORTH"}))
            .isEqualTo(new String[]{"WEST", "NORTH"});
  }
}