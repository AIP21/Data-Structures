import java.util.HashMap;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

import utils.*;

public class Day1 extends AbstractDay {
    public static void main(String[] args) {
        new Day1();
    }

    public Day1() {
        super(false, 2023);
    }

    @Override
    public void part1() {
        // testInput("""
        // 1abc2
        // pqr3stu8vwx
        // a1b2c3d4e5f
        // treb7uchet
        // """);

        int sum = 0;

        // go through every line
        for (String line : lines) {
            // get a list of characters
            char[] chars = line.toCharArray();

            String digits = "";

            // go through every character
            for (char ch : chars) {
                // if the character is a digit
                if (Utils.isDigit(ch)) {
                    digits += ch;
                }
            }

            String numString = "";
            numString += digits.charAt(0);
            numString += digits.charAt(digits.length() - 1);

            // get the number value of the string
            int num = Integer.parseInt(numString);

            // add to sum
            sum += num;
        }

        print("Result", sum);
    }

    @Override
    public void part2() {
        // testInput("""
        // two1nine
        // eightwothree
        // abcone2threexyz
        // xtwone3four
        // 4nineeightseven2
        // zoneight234
        // 7pqrstsixteen
        // """);

        int sum = 0;

        // go through every line
        for (String line : lines) {
            // replace the word digits with normal digits
            String replaced = replaceDigits(line);

            String digits = "";

            // go through every character
            for (char ch : replaced.toCharArray()) {
                // if the character is a digit
                if (Utils.isDigit(ch)) {
                    digits += ch;
                }
            }

            String numString = "";
            numString += digits.charAt(0);
            numString += digits.charAt(digits.length() - 1);

            // get the number value of the string
            int num = Integer.parseInt(numString);

            // add to sum
            sum += num;
        }

        print("Result", sum);
    }

    private String replaceDigits(String in) {
        HashMap<String, String> replacements = new HashMap<>() {
            {
                put("one", "o1e");
                put("two", "t2o");
                put("three", "t3e");
                put("four", "f4r");
                put("five", "f5e");
                put("six", "s6x");
                put("seven", "s7n");
                put("eight", "e8t");
                put("nine", "n9e");
            }
        };

        String result = in;

        for (String key : replacements.keySet()) {
            result = result.replace(key, replacements.get(key));
        }

        return result;
    }
}