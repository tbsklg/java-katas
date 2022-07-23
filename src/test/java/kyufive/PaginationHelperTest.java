package kyufive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationHelperTest {

  @Nested
  class ItemCountTest {

    @Test
    void shouldCalculateItemsForNumbers() {
      final var paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 1);

      assertThat(paginationHelper.itemCount()).isEqualTo(5);
    }

    @Test
    void shouldCalculateItemsForOtherCollections() {
      final var paginationHelper = new PaginationHelper<>(List.of('A', 'B', 'C', 'D', 'E', 'F'), 1);

      assertThat(paginationHelper.itemCount()).isEqualTo(6);
    }
  }

  @Nested
  class PageCountTest {

    @Test
    void shouldCalculatePageCountForFiveItemsPerPage() {
      final var paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5);

      assertThat(paginationHelper.pageCount()).isEqualTo(1);
    }

    @Test
    void shouldCalculatePageCountForThreeItemsPerPage() {
      final var paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3);

      assertThat(paginationHelper.pageCount()).isEqualTo(2);
    }

    @Test
    void shouldCalculatePageCountForTwoItemsPerPage() {
      final var paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 2);

      assertThat(paginationHelper.pageCount()).isEqualTo(3);
    }

    @Test
    void shouldCalculatePageCountForOneItemPerPage() {
      final var paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 1);

      assertThat(paginationHelper.pageCount()).isEqualTo(5);
    }
  }

  @Nested
  class PageItemCountTest {

    @Nested
    class WhenFiveItemsPerPageTest {

      PaginationHelper<Integer> paginationHelper;

      @BeforeEach
      void setUp() {
        paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5);
      }

      @Test
      void shouldCalculatePageItemCountAtStartPage() {
        assertThat(paginationHelper.pageItemCount(0)).isEqualTo(5);
      }

      @Test
      void shouldCalculatePageItemCountAtSecondPage() {
        assertThat(paginationHelper.pageItemCount(1)).isEqualTo(-1);
      }
    }

    @Nested
    class WhenThreeItemsPerPageTest {

      PaginationHelper<Integer> paginationHelper;

      @BeforeEach
      void setUp() {
        paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 3);
      }

      @Test
      void shouldCalculatePageItemCountAtStartPage() {
        assertThat(paginationHelper.pageItemCount(0)).isEqualTo(3);
      }

      @Test
      void shouldCalculatePageItemCountAtSecondPage() {
        assertThat(paginationHelper.pageItemCount(1)).isEqualTo(2);
      }
    }
  }

  @Nested
  class PageIndexCountTest {

    @Nested
    class WhenFiveItemsPerPageTest {

      PaginationHelper<Integer> paginationHelper;

      @BeforeEach
      void setUp() {
        paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 5);
      }

      @Test
      void shouldCalculatePageIndexAtStartPage() {
        assertThat(paginationHelper.pageIndex(0)).isEqualTo(0);
      }

      @Test
      void shouldCalculatePageIndexAtSecondPage() {
        assertThat(paginationHelper.pageIndex(3)).isEqualTo(0);
      }
    }

    @Nested
    class WhenTwoItemsPerPageTest {

      PaginationHelper<Integer> paginationHelper;

      @BeforeEach
      void setUp() {
        paginationHelper = new PaginationHelper<>(List.of(1, 2, 3, 4, 5), 2);
      }

      @Test
      void shouldCalculatePageItemCountAtStartPage() {
        assertThat(paginationHelper.pageIndex(3)).isEqualTo(1);
      }

      @Test
      void shouldCalculatePageItemCountAtSecondPage() {
        assertThat(paginationHelper.pageIndex(8)).isEqualTo(-1);
      }
    }
  }
}
