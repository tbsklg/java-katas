import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class RemovedNumbersTest {

  @Test
  void shouldReturnEmptyList() {
    assertThat(RemovedNumbers.removeNb(1)).isEmpty();
    assertThat(RemovedNumbers.removeNb(2)).isEmpty();
    assertThat(RemovedNumbers.removeNb(20)).isEmpty();
  }

  @Test
  void shouldReturnPossibleNumbers() {
    assertThat(RemovedNumbers.removeNb(10)).containsAll(Arrays.asList(new long[]{6, 7}, new long[]{7, 6}));
    assertThat(RemovedNumbers.removeNb(26)).containsAll(Arrays.asList(new long[]{15, 21}, new long[]{21, 15}));
    assertThat(RemovedNumbers.removeNb(1000003)).containsAll(Arrays.asList(new long[]{550320L, 908566L}, new long[]{908566L, 550320L}, new long[]{559756L, 893250L}, new long[]{893250L, 559756L}));
  }


}
