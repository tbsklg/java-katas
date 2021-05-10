public class HumanReadableTime {
    public static String makeReadable(int seconds) {
        int hh = seconds / 3600;
        int mm = seconds % 3600 / 60;
        int ss = seconds % 60;

        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }
}
