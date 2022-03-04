package kyufive;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.iterate;

public class Tour {

  public static int tour(String[] arrFriends, String[][] ftwns, Map<String, Double> h) {
    final var distancesForFriends =
            stream(arrFriends)
                    .map(f -> stream(ftwns)
                            .filter(t -> t[0].equals(f))
                            .findFirst()
                    )
                    .flatMap(Optional::stream)
                    .map(t -> t[1])
                    .map(t -> ofNullable(h.get(t)))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());

    final var distancesBetweenFriends =
            iterate(0, i -> i + 1)
                    .limit(distancesForFriends.size() - 1)
                    .map(i -> distance(distancesForFriends.get(i), distancesForFriends.get(i + 1)))
                    .reduce(0.0, Double::sum);

    return (int) floor(
            distancesForFriends.get(0)
                    + distancesBetweenFriends
                    + distancesForFriends.get(distancesForFriends.size() - 1)
    );
  }

  private static Double distance(Double x, Double y) {
    return sqrt(pow(y, 2) - pow(x, 2));
  }
}
