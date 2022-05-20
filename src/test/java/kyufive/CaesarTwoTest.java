package kyufive;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaesarTwoTest {

  @Test
  public void test1() {
    String u = "I should have known that you would have a perfect answer for me!!!";
    List<String> v = Arrays.asList("ijJ tipvme ibw", "f lopxo uibu z", "pv xpvme ibwf ", "b qfsgfdu botx", "fs gps nf!!!");
    assertEquals(v, CaesarTwo.encodeStr(u, 1));
  }

  @Test
  public void test2() {
    String u = "O CAPTAIN! my Captain! our fearful trip is done;";
    List<String> v = Arrays.asList("opP DBQUBJ", "O! nz Dbqu", "bjo! pvs g", "fbsgvm usj", "q jt epof;");
    assertEquals(v, CaesarTwo.encodeStr(u, 1));
  }

  @Test
  void shouldDecode() {
    List<String> v = Arrays.asList("opP DBQUBJ", "O! nz Dbqu", "bjo! pvs g", "fbsgvm usj", "q jt epof;");
    assertEquals("O CAPTAIN! my Captain! our fearful trip is done;", CaesarTwo.decode(v));
  }

  @Test
  void shouldShiftUp() {
    assertEquals('b', CaesarTwo.shiftUp('a', 1));
    assertEquals('a', CaesarTwo.shiftUp('z', 1));
  }

  @Test
  void shouldShiftDown() {
    assertEquals('z', CaesarTwo.shiftDown('a', 1));
    assertEquals('y', CaesarTwo.shiftDown('z', 1));
  }

  @Test
  void shouldCalculatePrefix() {
    assertEquals("hi", CaesarTwo.prefix(1, "Hello"));
    assertEquals("hk", CaesarTwo.prefix(3, "Hello"));
  }

  @Test
  void shouldRotate() {
    assertEquals(1, CaesarTwo.rotate("ijHello"));
    assertEquals(25, CaesarTwo.rotate("azHello"));
    assertEquals(1, CaesarTwo.rotate("zaHello"));
  }

  @Test
  void shouldChunk() {
    final var result = List.of("Hell", "o ho", "w ar", "e yo", "u?");
    assertEquals(result, CaesarTwo.chunk("Hello how are you?"));
  }
}
