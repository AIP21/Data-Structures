import utils.*;

public class Prob01 extends AbstractDay {
    public static void main(String[] args) {
        new Prob01();
    }

    public Prob01() {
        super(false);
    }

    @Override
    public void part1() {
        // testInput("91212129");

        int sum = 0;

        // get an array of characters in the input
        char[] chars = getLine(0).toCharArray();

        // loop through every character in the input
        for (int i = 0; i < chars.length; i++) {
            char thisCh = chars[i];

            // get the next character (if at end, next char is the first char)
            char nextCh = (i == chars.length - 1 ? chars[0] : chars[i + 1]);

            // check if this character matches the next character
            if (thisCh == nextCh) {
                // get the integer value of this char
                int charVal = Integer.parseInt(thisCh + "");

                // add to sum
                sum += charVal;
            }
        }

        // print output
        print("Sum", sum);
    }

    @Override
    public void part2() {
        // testInput("12131415");

        int sum = 0;

        // get an array of characters in the input
        char[] chars = getLine(0).toCharArray();

        // the distance to the element halfway across the list
        int halfwayDistance = chars.length / 2;

        // loop through every character in the input
        for (int i = 0; i < chars.length; i++) {
            char thisCh = chars[i];

            // get the character halfway across the list
            int nextIndex = i + halfwayDistance;

            // correct the index for end of array
            if (nextIndex >= chars.length) {
                // set to the amount remaining from the length
                nextIndex = (i + halfwayDistance) - chars.length;
            }

            char nextCh = chars[nextIndex];

            // check if this character matches the next character
            if (thisCh == nextCh) {
                // get the integer value of this char
                int charVal = Integer.parseInt(thisCh + "");

                // add to sum
                sum += charVal;
            }
        }

        // print output
        print("Sum", sum);
    }
}