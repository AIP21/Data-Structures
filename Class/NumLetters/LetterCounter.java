package Class.NumLetters;

import utils.CE;

public class LetterCounter extends CE {
    // A list of the lengths of the names of the numbers from 1 to 19, and from 20
    // to 90 (in tens)
    private static final int[] unitNameLengths = {
            -1, // index 0 is unused
            3, // one
            3, // two
            5, // three
            4, // four
            4, // five
            3, // six
            5, // seven
            5, // eight
            4, // nine
            3, // ten
            6, // eleven
            6, // twelve
            8, // thirteen
            8, // fourteen
            7, // fifteen
            7, // sixteen
            9, // seventeen
            8, // eighteen
            8, // nineteen
    };

    // A list of the lengths of the names of the tens from 20 to 90
    private static int[] tensNameLengths = {
            -1, // index 0 is unused
            -1, // ten is unused
            6, // twenty
            6, // thirty
            5, // forty
            5, // fifty
            5, // sixty
            7, // seventy
            6, // eighty
            6, // ninety
    };

    public static void main(String[] args) {
        print(LetterCounter.numLetters(1000));
    }

    /**
     * Returns the number of letters used to write out all of the
     * words for the whole numbers in closed interval [1,N]
     * Precondition: 1 <= N <= 1000
     * Excludes spaces.
     * 
     * @param N The number to count the letters of
     * @return The number of letters used to write out all of the
     */
    public static int numLetters(int N) {
        // Create a variable for the sum
        //
        // If the number is less than 13,
        // then add to the sum the length of the
        // word value of the number.
        //
        // If the number is between 20 and 12,
        // then add to the sum the length of the teens
        // word of the number.
        //
        // Otherwise, if the number is between 99 and 20,
        // then add to the sum the length of the word
        // of the tens digit of the number,
        // and add to the sum the length of the word
        // of the ones digit of the number.
        //
        // Otherwise, if the number is greater than 99,
        // add to the sum the length of the word of the
        // hundreds digit + the length of the word "hundred and",
        // and add to the sum the value of this method
        // called for this number without the hundreds digit.
        //
        // Return the sum

        // Create a variable for the sum
        int sum = 0;

        // Go through every number from 1 to N
        for (int i = 1; i <= N; i++) {
            // Calculate the number of letters in the word for this number
            sum += getLengthOfNumberWord(i);
        }

        // Return the sum
        return sum;
    }

    /**
     * Return the number of letters used to write out the word for the number
     * 
     * @param N The number to count the letters of
     * @return The number of letters used to write out the word for the number
     */
    public static int getLengthOfNumberWord(int number) {
        if (number == 1000) {
            // Return the length of the word "one thousand"
            return 11;
        }

        int result = 0;

        // region Handle hundreds
        int hundreds = number / 100;

        // If the number is greater than 99
        if (hundreds > 0) {
            // Add to the sum the length of the word for the hundreds digit
            // and the length of the word "hundred"
            result += unitNameLengths[hundreds] + 7;

            // Remove the hundreds digit from the number
            number %= 100;

            // If the number is not a multiple of 100
            if (number > 0) {
                // Add to the sum the length of the word "and"
                result += 3;
            }
        }
        // endregion

        // region Handle tens and ones
        if (number > 0) {
            // If the number is less than 20
            if (number < 20) {
                // Add to the sum the length of the word for this number
                result += unitNameLengths[number];
            } else {
                // Add to the sum the length of the word for the tens digit
                int tensDigit = number / 10;
                int onesDigit = number % 10;

                // Add to the sum the length of the word for the tens digit
                result += tensNameLengths[tensDigit];

                // If the ones digit is not 0
                if (onesDigit > 0) {
                    // Add to the sum the length of the word for the ones digit
                    result += unitNameLengths[onesDigit];
                }
            }
        }
        // endregion

        // nine (4) hundred (7) and (3) ninety (6) -nine (4) = 26
        // nine hundred and ninety -nine
        // ninehundredandninety-eight
        // nine-hundredandninety-seven
        // nine-hundredandforty = 20
        // nine-hundred = 12
        // six-hundred = 11
        // one-hundredandsixty-six
        // seventy-one = 11

        return result;
    }
}