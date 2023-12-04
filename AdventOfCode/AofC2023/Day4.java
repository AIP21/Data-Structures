import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import utils.*;

public class Day4 extends AbstractDay {
    public static void main(String[] args) {
        new Day4();
    }

    public Day4() {
        super(false, 2023);
    }

    @Override
    public void part1() {
        // testInput("""
        // Card 1: 41 48 83 86 17 | 83 86 6 31 17 9 48 53
        // Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        // Card 3: 1 21 53 59 44 | 69 82 63 72 16 21 14 1
        // Card 4: 41 92 73 84 69 | 59 84 76 51 58 5 54 83
        // Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        // Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        // """);

        int sum = 0;

        for (int i = 0; i < lines.size(); i++) {
            String cardName = lines.get(i).substring(lines.get(i).indexOf(":") + 1).replace("|", "-");

            // print(cardName);

            String[] split = cardName.split("-");

            // print(split[0]);
            // print(split[1]);

            ArrayList<Integer> winningNums = new ArrayList<>(Arrays.asList(intParser.parseString(split[0])));

            Integer[] myNums = intParser.parseString(split[1]);

            // print("Winning", winningNums);

            int score = 0;
            int matches = 0;

            for (int num : myNums) {
                if (winningNums.contains(num)) {
                    score = (int) Math.pow(2, matches++);
                }
            }

            // print("Score", score);
            sum += score;
        }

        print("Result", sum);
    }

    @Override
    public void part2() {
        testInput("""
                Card 1: 41 48 83 86 17 | 83 86 6 31 17 9 48 53
                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                Card 3: 1 21 53 59 44 | 69 82 63 72 16 21 14 1
                Card 4: 41 92 73 84 69 | 59 84 76 51 58 5 54 83
                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                """);

        ArrayList<String> cards = new ArrayList<>();

        for (String line : lines) {
            cards.add(line.replace("|", "-"));
        }

        int i = 0;

        while (i < cards.size()) {
            String[] split = cards.get(i).substring(cards.get(i).indexOf(":") + 1).split("-");

            ArrayList<Integer> winningNums = new ArrayList<>(Arrays.asList(intParser.parseString(split[0])));
            Integer[] myNums = intParser.parseString(split[1]);

            int insertionIndex = i;

            HashMap<Integer, String> copies = new HashMap<>();

            for (int num : myNums) {
                if (winningNums.contains(num)) {
                    copies.put(++insertionIndex, cards.get(insertionIndex));
                }
            }

            print("Copies", copies);

            int e = 0;

            for (Integer key : copies.keySet()) {
                // int intKey = (int) key;

                cards.add(insertionIndex++, copies.get(key));

                e++;
            }

            print("Cards", cards);

            i++;

            if (i == 2) {
                break;
            }
        }

        print("Result", cards.size());
    }
}