package kyufive;

public class HumanReadableTime {

  public static String makeReadable(int seconds) {
    return Time.from(seconds).format();
  }

  private static class Time {

    private final int hh;
    private final int mm;
    private final int ss;

    public Time(int hh, int mm, int ss) {
      this.hh = hh;
      this.mm = mm;
      this.ss = ss;
    }

    public static Time from(int seconds) {
      return new Time(hh(seconds), mm(seconds), ss(seconds));
    }

    private static int hh(int seconds) {
      return seconds / 3600;
    }

    private static int mm(int seconds) {
      return seconds % 3600 / 60;
    }

    private static int ss(int seconds) {
      return seconds % 60;
    }

    public String format() {
      return String.format("%02d:%02d:%02d", this.hh, this.mm, this.ss);
    }
  }
}
