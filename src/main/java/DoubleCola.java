public class DoubleCola {
    public static String whoIsNext(String[] names, int n) {
        return findName(names, n, 0, 0);
    }

    private static String findName(String[] names, int n, int low, int h) {
        if (n < names.length) return names[n - 1];

        int currentLow = low + 1;
        int high = names.length * (int) Math.pow(2, h) + low;

        if (n >= currentLow && n <= high) {
            final int position = (int) ((n - (currentLow - 1))/ Math.pow(2, h)) + 1;
            return names[position - 1];
        }

        return findName(names, n, high, ++h);
    }
}
