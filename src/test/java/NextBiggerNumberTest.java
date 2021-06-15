import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NextBiggerNumberTest {

  @Test
  void shouldCalculateForSingleDigitNumber() {
    assertThat(NextBiggerNumber.nextBiggerNumber(1)).isEqualTo(-1);
  }

  @Test
  void shouldCalculateForTwoDigitNumber() {
    assertThat(NextBiggerNumber.nextBiggerNumber(12)).isEqualTo(21);
    assertThat(NextBiggerNumber.nextBiggerNumber(21)).isEqualTo(-1);
    assertThat(NextBiggerNumber.nextBiggerNumber(33)).isEqualTo(-1);
    assertThat(NextBiggerNumber.nextBiggerNumber(14)).isEqualTo(41);
  }

  @Test
  void shouldCalculateForThreeDigetNumber() {
    assertThat(NextBiggerNumber.nextBiggerNumber(101)).isEqualTo(110);
    assertThat(NextBiggerNumber.nextBiggerNumber(110)).isEqualTo(-1);
    assertThat(NextBiggerNumber.nextBiggerNumber(120)).isEqualTo(201);
    assertThat(NextBiggerNumber.nextBiggerNumber(102)).isEqualTo(120);
    assertThat(NextBiggerNumber.nextBiggerNumber(513)).isEqualTo(531);
    assertThat(NextBiggerNumber.nextBiggerNumber(414)).isEqualTo(441);
    assertThat(NextBiggerNumber.nextBiggerNumber(144)).isEqualTo(414);
  }

  @Test
  void shouldCalculateForFourDigetNumber() {
    assertThat(NextBiggerNumber.nextBiggerNumber(2017)).isEqualTo(2071);
  }

  @Test
  void shouldCalculateForFiveDigetNumber() {
    assertThat(NextBiggerNumber.nextBiggerNumber(10990)).isEqualTo(19009);
  }

  @Test
  void shouldCalculateForBigNumber() {
    assertThat(NextBiggerNumber.nextBiggerNumber(1090879862)).isEqualTo(1090882679);
    assertThat(NextBiggerNumber.nextBiggerNumber(1805583884)).isEqualTo(1805584388);
  }
}
