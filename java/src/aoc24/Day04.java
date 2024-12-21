package aoc24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day04 {

    public static void main(String[] args) throws IOException {
        Path inputPath = Path.of("resources", "aoc24", "day04.txt");
        List<String> input = Files.readAllLines(inputPath);

        long part1Result = countOccurrences(input);
        System.out.println("Part 1: " + part1Result);

        long part2Result = countXMASShapes(input);
        System.out.println("Part 2: " + part2Result);
    }

    public static long countOccurrences(List<String> grid) {
        int rows = grid.size();
        int cols = grid.get(0).length();
        String word = "XMAS";
        int wordLen = word.length();
        long count = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                count += checkDirections(grid, row, col, word, wordLen);
            }
        }

        return count;
    }

    private static int checkDirections(List<String> grid, int row, int col, String word, int wordLen) {
        int count = 0;
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}}; // right, down, diag-down-right, diag-down-left

        for (int[] dir : directions) {
            int dirRow = dir[0];
            int dirCol = dir[1];
            String forward = extractString(grid, row, col, wordLen, dirRow, dirCol);
            String backward = new StringBuilder(forward).reverse().toString();
            if (forward.equals(word)) count++;
            if (backward.equals(word)) count++;
        }
        return count;
    }

    private static String extractString(List<String> grid, int row, int col, int wordLen, int dirRow, int dirCol) {
        StringBuilder sb = new StringBuilder();
        int rows = grid.size();
        int cols = grid.get(0).length();

        for (int i = 0; i < wordLen; i++) {
            int newRow = row + i * dirRow;
            int newCol = col + i * dirCol;

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                sb.append(grid.get(newRow).charAt(newCol));
            } else {
                return "";
            }
        }
        return sb.toString();
    }

    public static long countXMASShapes(List<String> grid) {
        int rows = grid.size();
        int cols = grid.get(0).length();
        long count = 0;

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (isXMASShape(grid, row, col)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean isXMASShape(List<String> grid, int row, int col) {
        String word = "MAS";
        int wordLen = word.length();

        // Check for X-shape
        if (grid.get(row).charAt(col) != 'A') return false;

        // Check top-left
        String topLeft = extractString(grid, row - 1, col - 1, wordLen, 0, -1);
        if (!topLeft.equals(word) && !topLeft.equals(new StringBuilder(word).reverse().toString())) return false;

        // Check top-right
        String topRight = extractString(grid, row - 1, col + 1, wordLen, 0, 1);
        if (!topRight.equals(word) && !topRight.equals(new StringBuilder(word).reverse().toString())) return false;

        // Check bottom-left
        String bottomLeft = extractString(grid, row + 1, col - 1, wordLen, 0, -1);
        if (!bottomLeft.equals(word) && !bottomLeft.equals(new StringBuilder(word).reverse().toString())) return false;

        // Check bottom-right
        String bottomRight = extractString(grid, row + 1, col + 1, wordLen, 0, 1);
        if (!bottomRight.equals(word) && !bottomRight.equals(new StringBuilder(word).reverse().toString())) return false;

        return true;
    }
}
