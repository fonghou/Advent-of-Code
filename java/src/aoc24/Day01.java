package aoc24;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01 {

  public static void main(String[] args) throws IOException {
    Path inputPath = Path.of("resources", "aoc24", "day01.txt");
    try {
      long totalDistance = calculateTotalDistance(inputPath);
      System.out.println("Total distance: " + totalDistance);
    } catch (InvalidInputFormatException e) {
      System.err.println("Error processing input: " + e.getMessage());
      System.exit(1);
    }
  }

  public static long calculateTotalDistance(Path inputPath) throws IOException, InvalidInputFormatException {
    List<int[]> pairs = new ArrayList<>();
    try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split("\\s+");
        if (parts.length == 2) {
          try {
            int departure = Integer.parseInt(parts[0]);
            int arrival = Integer.parseInt(parts[1]);
            pairs.add(new int[] { departure, arrival });
          } catch (NumberFormatException e) {
            throw new InvalidInputFormatException("Invalid number format in line: " + line);
          }
        } else {
          throw new InvalidInputFormatException("Each line must contain exactly two numbers separated by whitespace.");
        }
      }
    }

    if (pairs.isEmpty()) {
      throw new InvalidInputFormatException("Input file is empty or contains no valid lines.");
    }

    int[] departures = new int[pairs.size()];
    int[] arrivals = new int[pairs.size()];
    for (int i = 0; i < pairs.size(); i++) {
      departures[i] = pairs.get(i)[0];
      arrivals[i] = pairs.get(i)[1];
    }
    Arrays.sort(departures);
    Arrays.sort(arrivals);

    if (departures.length != arrivals.length) {
      throw new InvalidInputFormatException("Unequal number of departure and arrival times.");
    }

    long totalDistance = 0;
    for (int i = 0; i < departures.length; i++) {
      totalDistance += Math.abs(departures[i] - arrivals[i]);
    }

    return totalDistance;
  }

  static class InvalidInputFormatException extends Exception {

    public InvalidInputFormatException(String message) {
      super(message);
    }
  }
}
