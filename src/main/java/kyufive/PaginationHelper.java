package kyufive;

import java.util.List;

public class PaginationHelper<I> {

  private final List<I> collection;
  private final int itemsPerPage;

  /**
   * The constructor takes in an array of items and a integer indicating how many
   * items fit within a single page
   */
  public PaginationHelper(List<I> collection, int itemsPerPage) {
    this.collection = collection;
    this.itemsPerPage = itemsPerPage;
  }

  /**
   * returns the number of items within the entire collection
   */
  public int itemCount() {
    return this.collection.size();
  }

  /**
   * returns the number of pages
   */
  public int pageCount() {
    return (int) Math.ceil((double) this.itemCount() / (double) this.itemsPerPage);
  }

  /**
   * returns the number of items on the current page. page_index is zero based.
   * this method should return -1 for pageIndex values that are out of range
   */
  public int pageItemCount(int pageIndex) {
    if (this.collection.isEmpty() || !this.isValidPageIndex(pageIndex)) return -1;

    final var remainingItems = this.itemCount() - this.itemsPerPage * pageIndex;
    return Math.min(remainingItems, this.itemsPerPage);
  }

  /**
   * determines what page an item is on. Zero based indexes
   * this method should return -1 for itemIndex values that are out of range
   */
  public int pageIndex(int itemIndex) {
    if (this.collection.isEmpty() || !this.isValidItemIndex(itemIndex)) return -1;

    return (int) Math.floor((double) itemIndex / (double) this.itemsPerPage);
  }

  private boolean isValidItemIndex(int itemIndex) {
    return itemIndex >= 0 && itemIndex < this.itemCount();
  }

  private boolean isValidPageIndex(int pageIndex) {
    return pageIndex >= 0 && pageIndex < this.pageCount();
  }
}
