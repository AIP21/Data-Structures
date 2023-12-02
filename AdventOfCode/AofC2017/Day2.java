import utils.*;

public class Day2 extends AbstractDay {
    public static void main(String[] args) {
        new Day2();
    }

    public Day2() {
        super(false, 2017);
    }

    @Override
    public void part1() {
        // testInput("""
        // 5 1 9 5
        // 7 5 3
        // 2 4 6 8
        // """);

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
        // testInput("""
        // 5 9 2 8
        // 9 4 7 3
        // 3 8 6 5
        // """);

        int sum = 0;

        // go through every line in the input
        for (int i = 0; i < lines.size(); i++) {
            double resultOfLine = -1;

            // get every number in the line
            Integer[] numbers = intParser.parseLine(i);

            // find the two numbers that evenly divide into each other
            for (int a = 0; a < numbers.length; a++) {
                int aVal = numbers[a];

                // check if this divides evenly with any other number
                for (int b = 0; b < numbers.length; b++) {
                    int bVal = numbers[b];

                    // don't check ourself
                    if (a != b) {
                        // if a and b divide evenly
                        if (aVal % bVal == 0) {
                            // set the result and exit the loop
                            resultOfLine = aVal / bVal;

                            // print("found result", resultOfLine + ", " + (aVal % bVal));
                            // print("a", aVal);
                            // print("b", bVal);
                            break;
                        }
                    }
                }

                // if we found the result,
                // then exit the loop
                if (resultOfLine != -1) {
                    break;
                }
            }

            // add the result to the sum
            sum += resultOfLine;
        }

        // print the result
        print("Sum", sum);
    }
}