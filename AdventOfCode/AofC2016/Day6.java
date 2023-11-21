import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utils.AbstractDay;

public class Day6 extends AbstractDay {
    public static void main(String[] args) {
        new Day6();
    }

    public Day6() {
        super(false);
    }

    @Override
    public void part1() {
        // Figure out the length of the message
        int messageLength = lines.get(0).length();

        // Store the occurences of each character in each column
        ArrayList<HashMap<Character, Integer>> columns = new ArrayList<>();

        // Populate the column list
        for (int i = 0; i < messageLength; i++) {
            HashMap<Character, Integer> columnCharCounts = new HashMap<>();

            columns.add(columnCharCounts);
        }

        // Go through every line in the input
        for (String line : lines) {
            // Go through every character in the message
            for (int i = 0; i < messageLength; i++) {
                char ch = line.charAt(i);

                HashMap<Character, Integer> columnCharCounts = columns.get(i);

                // If the char here is not already being tracked
                if (!columnCharCounts.containsKey(ch)) {
                    columnCharCounts.put(ch, 1);
                } else {
                    // Alrady tracking the char
                    int currentCount = columnCharCounts.get(ch);

                    // Update the tracked occurence value
                    columnCharCounts.put(ch, currentCount + 1);
                }
            }
        }

        // Find the most common character in every column
        String repairedMessage = "";

        // Go through every column
        for (int i = 0; i < messageLength; i++) {
            HashMap<Character, Integer> columnCharCounts = columns.get(i);

            // Get the character with the largest count in the map
            char mostCommonCharacter = Collections.max(columnCharCounts.entrySet(), Map.Entry.comparingByValue())
                    .getKey();

            // Set the character in the repaired message to this most common character
            repairedMessage += mostCommonCharacter;
        }

        print("Repaired message", repairedMessage);
    }

    @Override
    public void part2() {
        // testInput("""
        // eedadn
        // drvtee
        // eandsr
        // raavrd
        // atevrs
        // tsrnev
        // sdttsa
        // rasrtv
        // nssdts
        // ntnada
        // svetve
        // tesnvt
        // vntsnd
        // vrdear
        // dvrsen
        // enarar
        // """);

        // Figure out the length of the message
        int messageLength = lines.get(0).length();

        // Store the occurences of each character in each column
        ArrayList<HashMap<Character, Integer>> columns = new ArrayList<>();

        // Populate the column list
        for (int i = 0; i < messageLength; i++) {
            HashMap<Character, Integer> columnCharCounts = new HashMap<>();

            columns.add(columnCharCounts);
        }

        // Go through every line in the input
        for (String line : lines) {
            // Go through every character in the message
            for (int i = 0; i < messageLength; i++) {
                char ch = line.charAt(i);

                HashMap<Character, Integer> columnCharCounts = columns.get(i);

                // If the char here is not already being tracked
                if (!columnCharCounts.containsKey(ch)) {
                    columnCharCounts.put(ch, 1);
                } else {
                    // Alrady tracking the char
                    int currentCount = columnCharCounts.get(ch);

                    // Update the tracked occurence value
                    columnCharCounts.put(ch, currentCount + 1);
                }
            }
        }

        // Find the least common character in every column
        String repairedMessage = "";

        // Go through every column
        for (int i = 0; i < messageLength; i++) {
            HashMap<Character, Integer> columnCharCounts = columns.get(i);

            // Get the character with the lowest count in the map
            char leastCommonCharacter = Collections.min(columnCharCounts.entrySet(), Map.Entry.comparingByValue())
                    .getKey();

            // Set the character in the repaired message to this least common character
            repairedMessage += leastCommonCharacter;
        }

        print("Repaired message", repairedMessage);


        // what... why was part 2 so easy lol
    }
}