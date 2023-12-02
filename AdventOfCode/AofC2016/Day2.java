import utils.AbstractDay;
import utils.Point;
import utils.Utils;

public class Day2 extends AbstractDay {

    public static void main(String[] args) {
        new Day2();
    }

    public Day2() {
        super(false, 2016);
    }

    @Override
    public void part1() {
        // testInput("""
        // ULL
        // RRDDD
        // LURDL
        // UUUUD
        // """);

        /*
         * 
         * 1 2 3 | 2
         * 4 5 6 | 1
         * 7 8 9 | 0
         * - - -
         * 0 1 2
         */

        Point startingButton = new Point(1, 1);
        Point pos = new Point(1, 1);

        StringBuilder result = new StringBuilder();

        // Go through every line of commands
        for (String line : lines) {
            print("Line", line);

            // Update the pos to match the starting position
            pos.x = startingButton.x;
            pos.y = startingButton.y;

            char[] chars = line.toCharArray();

            // Go through every move
            for (char move : chars) {
                // print("Move", move);

                Point lastPos = new Point(pos.x, pos.y);

                // Logic for moving positions
                switch (move) {
                    case 'U': // Up
                        pos.y--;
                        break;
                    case 'D': // Down
                        pos.y++;
                        break;
                    case 'L': // Left
                        pos.x--;
                        break;
                    case 'R': // Right
                        pos.x++;
                        break;
                }

                // If invalid, ignore
                if (pos.x > 2 || pos.x < 0 || pos.y > 2 || pos.y < 0) {
                    // print("Move ignored at", pos);
                    pos.x = lastPos.x;
                    pos.y = lastPos.y;
                }
            }

            // Update the starting point for the next line
            startingButton.x = pos.x;
            startingButton.y = pos.y;

            // Append that button's value to the result chain
            result.append(buttonString(pos));

            print("End of line pos", pos);
            print("End of line result", result);
            print("");
        }

        print("Bathroom code", result.toString());
    }

    @Override
    public void part2() {
        // testInput("""
        // ULL
        // RRDDD
        // LURDL
        // UUUUD
        // """);

        shouldPrint(false);

        /*
         * 4 | . . 1 . .
         * 3 | . 2 3 4 .
         * 2 | 5 6 7 8 9
         * 1 | . A B C .
         * 0 | . . D . .
         * . + - - - - -
         * . . 0 1 2 3 4
         */

        Point startingButton = new Point(0, 2);
        Point pos = new Point(0, 2);
        // print("Distance from " + new Point(0, 3) + " to (2, 2)", new Point(0,
        // 3).distance(2.0, 2.0));

        StringBuilder result = new StringBuilder();

        // Go through every line of commands
        for (String line : lines) {
            print("Line", line);

            // Update the pos to match the starting position
            pos.x = startingButton.x;
            pos.y = startingButton.y;

            char[] chars = line.toCharArray();

            // Go through every move
            for (char move : chars) {
                print("Move", move);

                Point lastPos = new Point(pos.x, pos.y);

                // Logic for moving positions
                switch (move) {
                    case 'U': // Up
                        pos.y++;
                        break;
                    case 'D': // Down
                        pos.y--;
                        break;
                    case 'L': // Left
                        pos.x--;
                        break;
                    case 'R': // Right
                        pos.x++;
                        break;
                }

                // If invalid, ignore
                // The point is invalid if it is more than 2 units
                // away from the center of the keypad (2, 2)
                if (Math.abs(pos.distance(2.0, 2.0)) > 2.0) {
                    print("Move ignored to", pos);
                    pos.x = lastPos.x;
                    pos.y = lastPos.y;
                } else {
                    print("Move valid to", pos);
                }
            }

            // Update the starting point for the next line
            startingButton.x = pos.x;
            startingButton.y = pos.y;

            // Append that button's value to the result chain
            result.append(buttonStringPart2(pos));

            print("End of line pos", pos);
            print("End of line result", result);
            print("");
        }

        shouldPrint(true);

        print("Bathroom code", result.toString());
    }

    // Return the string value of the button at that index
    private String buttonString(Point pos) {
        // Convert the position to an index for easier mapping
        int index = Utils.indexFromCoord(pos.x, pos.y, 3);

        // Return the index + 1 (which is the value) as a string
        return String.valueOf(index + 1);
    }

    // Return the string value of the button at that index (part 2)
    private String buttonStringPart2(Point pos) {
        // Ugly brute force mapping with switch statements
        switch (pos.y) {
            case 4:
                return "1";
            case 3:
                switch (pos.x) {
                    case 1:
                        return "2";
                    case 2:
                        return "3";
                    case 3:
                        return "4";
                }
                break;
            case 2:
                switch (pos.x) {
                    case 0:
                        return "5";
                    case 1:
                        return "6";
                    case 2:
                        return "7";
                    case 3:
                        return "8";
                    case 4:
                        return "9";
                }
                break;
            case 1:
                switch (pos.x) {
                    case 1:
                        return "A";
                    case 2:
                        return "B";
                    case 3:
                        return "C";
                }
                break;
            case 0:
                return "D";
        }

        // We should not reach here, but just in case we do return null
        return null;
    }
}