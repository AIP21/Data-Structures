// package AdventOfCode;

import utils.*;

public class Day2 extends AbstractDay {
    public static void main(String[] args) {
        new Day2();
    }

    public Day2() {
        super(false);
    }

    @Override
    public void part1() {
        testInput("""
                5 1 9 5
                7 5 3
                2 4 6 8
                """);

        int checksum = 0;

        // go through every line in the input
        for (int i = 0; i < lines.size(); i++) {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            // get every number in the line
            Integer[] numbers = intParser.parseLine(i);

            // get the min and max values
            for (int value : numbers) {
                if (value < min) {
                    min = value;
                }

                if (value > max) {
                    max = value;
                }
            }

            // get the difference between the min and max values
            int difference = max - min;

            // add the difference to the checksum
            checksum += difference;
        }

        // print the result
        print("Checksum", checksum);
    }

    @Override
    public void part2() {

    }
}