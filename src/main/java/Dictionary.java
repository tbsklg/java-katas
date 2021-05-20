public class Dictionary {
  private final String[] words;

  public Dictionary(String[] words) {
    this.words = words;
  }

  public String findMostSimilar(String to) {
    var currentLDistance = to.length();
    var currentWord = to;

    for (String word : words) {
      var lDistance = lDistance(word.toCharArray(), to.toCharArray());

      if (lDistance < currentLDistance) {
        currentLDistance = lDistance;
        currentWord = word;
      }
    }

    return currentWord;
  }

  private int lDistance(char[] a, char[] b) {
    int[][] d = new int[a.length + 1][b.length + 1];

    for (int i = 0; i < a.length + 1; i++) {
      d[i][0] = i;
    }

    for (int j = 0; j < b.length + 1; j++) {
      d[0][j] = j;
    }

    for (int i = 1; i < a.length + 1; i++) {
      for (int j = 1; j < b.length + 1; j++) {
        int d1 = d[i - 1][j] + 1;
        int d2 = d[i][j - 1] + 1;
        int d3 = d[i - 1][j - 1];
        if (a[i - 1] != b[j - 1]) {
          d3 += 1;
        }
        d[i][j] = Math.min(Math.min(d1, d2), d3);
      }
    }
    return d[a.length][b.length];
  }
}
