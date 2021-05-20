import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VasyaClerkTest {

  VasyaClerk vasyaClerk;

  @BeforeEach()
  void setUp() {
    this.vasyaClerk = new VasyaClerk();
  }

  @Test
  void shouldCheckoutForOnePeopleInLine() {
    assertThat(vasyaClerk.tickets(new int[]{25})).isEqualTo(VasyaClerk.YES);
    assertThat(vasyaClerk.tickets(new int[]{50})).isEqualTo(VasyaClerk.NO);
  }

  @Test
  void shouldCheckoutForTwoPeopleInLine() {
    assertThat(vasyaClerk.tickets(new int[]{25, 25})).isEqualTo(VasyaClerk.YES);
    assertThat(vasyaClerk.tickets(new int[]{25, 50})).isEqualTo(VasyaClerk.YES);
    assertThat(vasyaClerk.tickets(new int[]{50, 25})).isEqualTo(VasyaClerk.NO);
    assertThat(vasyaClerk.tickets(new int[]{25, 50, 50})).isEqualTo(VasyaClerk.NO);
  }

  @Test
  void shouldCheckoutForFivePeopleInLine() {
    assertThat(vasyaClerk.tickets(new int[]{25, 25, 50, 50, 100})).isEqualTo(VasyaClerk.NO);
  }
}
