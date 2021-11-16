package amazon;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result {

  /*
   * Complete the 'searchSuggestions' function below.
   *
   * The function is expected to return a 2D_STRING_ARRAY.
   * The function accepts following parameters:
   *  1. STRING_ARRAY repository
   *  2. STRING customerQuery
   */

  public static List<List<String>> searchSuggestions(List<String> repository, String customerQuery) {
    if (customerQuery.length() < 2) {
      return new ArrayList<>();
    }


    return Stream.iterate(2, n -> n + 1)
            .limit(customerQuery.length() - 1)
            .map(current -> repository.stream()
                    .map(String::toLowerCase)
                    .filter(s -> s.startsWith(customerQuery.toLowerCase().substring(0, current)))
            )
            .map(result -> result.collect(Collectors.toList()))
            .peek(result -> result.sort(Comparator.naturalOrder()))
            .map(result -> {
              if (result.size() > 3) {
                return result.subList(0, 3);
              }
              return result;
            })
            .collect(Collectors.toList());
  }
}

class Solution {
  public static void main(String[] args) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    int repositoryCount = Integer.parseInt(bufferedReader.readLine().trim());

    List<String> repository = IntStream.range(0, repositoryCount).mapToObj(i -> {
              try {
                return bufferedReader.readLine();
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            })
            .collect(toList());

    String customerQuery = bufferedReader.readLine();

    List<List<String>> result = Result.searchSuggestions(repository, customerQuery);

    result.stream()
            .map(
                    r -> r.stream()
                            .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
              try {
                bufferedWriter.write(e);
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
            });

    bufferedReader.close();
    bufferedWriter.close();
  }
}

