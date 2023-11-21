import utils.*;

public class Day7 extends AbstractDay {
    public static void main(String[] args) {
        new Day7();
    }

    public Day7() {
        super(false);
    }

    @Override
    public void part1() {
        testInput("""
                abba[mnop]qrst
                abcd[bddb]xyyx
                aaaa[qwer]tyui
                ioxxoj[asdfgh]zxcvbn
                """);

        int TLSCount = 0;

        // For each line in the input
        for (String line : lines) {
            boolean supportsTLS = false;

            boolean trackingBracket = false;

            // Go through every character in the line
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                // Check if we've begun to be in a bracket
                if (ch == '[') {
                    trackingBracket = true;
                    continue;
                } else if (ch == ']') {
                    trackingBracket = false;
                    continue;
                }

                // Logic for checking ABBAs
                char[] nextFourChars = new char[4];

                // Get the next four characters
                nextFourChars[0] = ch;
                if (i < line.length() - 1)
                    nextFourChars[1] = line.charAt(i + 1);
                else if (i < line.length() - 2)
                    nextFourChars[2] = line.charAt(i + 2);
                else if (i < line.length() - 3)
                    nextFourChars[3] = line.charAt(i + 3);

                // Perform an ABBA check
                boolean isABBA = checkABBA(nextFourChars);

                // If in a bracket and we have an ABBA, then its not TLS
                if (trackingBracket && isABBA) {
                    supportsTLS = false;
                    break;
                }
                // Otherwise if we are just ABBA this supports TLS, but don't break out
                // to keep checking the rest of the characters
                else if (isABBA) {
                    supportsTLS = true;
                }
            }

            // If this IP supports TLS, then increment the counter
            if (supportsTLS) {
                TLSCount++;
            }
        }

        print("Number of IPs that support TLS", TLSCount);
    }

    // Check if a set of four characters follows the ABBA rule
    private boolean checkABBA(char[] chars) {
        char a = chars[0];
        char b = chars[1];
        char c = chars[2];
        char d = chars[3];

        // If any character here is a bracket, then return false
        if (a == '[' || b == '[' || c == '[' || d == '[') {
            return false;
        } else if (a == ']' || b == ']' || c == ']' || d == ']') {
            return false;
        }

        // Return true if both a = d and b = c
        return a == d && b == c;
    }

    @Override
    public void part2() {

    }
}