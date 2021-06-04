import findTheUnknownDigit.FindTheUnknownDigit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FindTheUnknownDigitTest {

  @Test
  void shouldSolveForONLYRightMissing() {
    assertThat(FindTheUnknownDigit.solveExpression("1+1=?")).isEqualTo(2);
    assertThat(FindTheUnknownDigit.solveExpression("2+1=?")).isEqualTo(3);
    assertThat(FindTheUnknownDigit.solveExpression("1-1=?")).isEqualTo(0);
    assertThat(FindTheUnknownDigit.solveExpression("-11-1=?")).isEqualTo(-1);
    assertThat(FindTheUnknownDigit.solveExpression("2*5=?0")).isEqualTo(1);
    assertThat(FindTheUnknownDigit.solveExpression("19--45=5?")).isEqualTo(-1);
  }

  @Test
  void shouldSolveForLeftTermAndRightTermMissing() {
    assertThat(FindTheUnknownDigit.solveExpression("1?+1?=26")).isEqualTo(3);
  }

  @Test
  void shouldSolveForLeftTermAndRightMissing() {
    assertThat(FindTheUnknownDigit.solveExpression("?*11=??")).isEqualTo(2);
    assertThat(FindTheUnknownDigit.solveExpression("??*1=??")).isEqualTo(2);
    assertThat(FindTheUnknownDigit.solveExpression("-5?*-1=5?")).isEqualTo(0);
    assertThat(FindTheUnknownDigit.solveExpression("?0+3=?3")).isEqualTo(1);
    assertThat(FindTheUnknownDigit.solveExpression("?+1=?")).isEqualTo(-1);
    assertThat(FindTheUnknownDigit.solveExpression("3?+71=1?1")).isEqualTo(0);
    assertThat(FindTheUnknownDigit.solveExpression("-?56373--9216=-?47157")).isEqualTo(8);
  }

  @Test
  void shouldSolveForRightTermAndRightMissing() {
    assertThat(FindTheUnknownDigit.solveExpression("123*45?=5?088")).isEqualTo(6);
  }

  @Test
  void shouldSolveForLeftTermRightTermAndRightMissing() {
    assertThat(FindTheUnknownDigit.solveExpression("??*??=302?")).isEqualTo(5);
    assertThat(FindTheUnknownDigit.solveExpression("??+??=??")).isEqualTo(-1);
    assertThat(FindTheUnknownDigit.solveExpression("?+?=?")).isEqualTo(0);
    assertThat(FindTheUnknownDigit.solveExpression("123?45*?=?")).isEqualTo(0);
  }
}
