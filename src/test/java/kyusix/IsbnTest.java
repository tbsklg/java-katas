package kyufive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: Replace examples and use TDD by writing your own tests

class IsbnTest {

  @Test
  @DisplayName("Fixed tests")
  void test() {
    assertEquals("978-1-85326-158-9", ISBNConverter.isbnConverter("1-85326-158-0"));
    assertEquals("978-0-14-143951-8", ISBNConverter.isbnConverter("0-14-143951-3"));
    assertEquals("978-0-02-346450-8", ISBNConverter.isbnConverter("0-02-346450-X"));
    assertEquals("978-963-14-2164-4", ISBNConverter.isbnConverter("963-14-2164-3"));
    assertEquals("978-1-7982-0894-6", ISBNConverter.isbnConverter("1-7982-0894-6"));
    assertEquals("978-7-448590-33-1", ISBNConverter.isbnConverter("7-448590-33-X"));
  }
}
