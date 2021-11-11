package kyuseven;

import java.util.ArrayList;

/*
https://www.codewars.com/kata/5650ab06d11d675371000003/solutions/java
 */
public class InParts {
  public static String splitInParts(String s, int partLength) {
    final var str = new ArrayList<String>();

    var index = 0;
    while (index < s.length()) {
      str.add(s.substring(index, Math.min(index + partLength, s.length())));
      index += partLength;
    }

    return String.join(" ", str);

//    StringBuilder sb = new StringBuilder(s);
//    for (int i = partLength++; i < sb.length(); i += partLength){
//      sb.insert(i, " ");
//    }
//    return sb.toString();
  }
}
