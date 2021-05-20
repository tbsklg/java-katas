import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoopInspectorTest {

  @Test
  void shouldBuildCycle() {
    assertThat(LoopInspector.loopSize(LoopInspector.Node.createFrom(2, 3))).isEqualTo(3);
  }
}
