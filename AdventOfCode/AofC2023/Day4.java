import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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

    // Card class to contain the data for cards in part 2
    class Card {
        public int id;
        public ArrayList<Integer> winningNums = new ArrayList<>();
        public Integer[] myNums = {};

        public String stringValue;

        public int matches;

        public Card(String input) {
            input = input.replace("|", "-");

            stringValue = input;

            id = Integer.parseInt(input.substring(0, input.indexOf(":")).replace("Card", "").replace(" ", ""));

            String[] split = input.substring(input.indexOf(":") + 1).split("-");

            winningNums = new ArrayList<>(Arrays.asList(intParser.parseString(split[0])));
            myNums = intParser.parseString(split[1]);

            for (int num : myNums) {
                if (winningNums.contains(num)) {
                    matches++;
                }
            }
        }

        public Card copy() {
            return new Card(stringValue);
        }

        @Override
        public String toString() {
            return "(" + id + ")";
        }
    }

    @Override
    public void part2() {
        // testInput("""
        // Card 1: 41 48 83 86 17 | 83 86 6 31 17 9 48 53
        // Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        // Card 3: 1 21 53 59 44 | 69 82 63 72 16 21 14 1
        // Card 4: 41 92 73 84 69 | 59 84 76 51 58 5 54 83
        // Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        // Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        // """);

        // A list of the cards
        ArrayList<Card> cards = new ArrayList<>();

        // Contains the sorted set of the number of instances of each card. <Card ID,
        // instances>
        LinkedHashMap<Integer, Integer> cardAmounts = new LinkedHashMap<>();

        // Parse the cards from the input and set up their instance counts
        for (String line : lines) {
            // Create a new card from the input line
            Card card = new Card(line);

            // Add the card to the list of cards
            cards.add(card);

            // Set up the instance count of the card
            cardAmounts.put(card.id, 1);
        }

        print("Amounts", cardAmounts);
        print("Cards", cards);
        print("");

        // Go through every card
        for (Card card : cards) {
            // get the current number of instances of the original card
            int curAmount = cardAmounts.getOrDefault(card.id, 0);

            // For every card that will be added (looped by card id),
            // increase the card count for the card about to be added.
            // The count is increased by the number of times that card would be copied
            for (int e = card.id + 1; e < card.id + card.matches + 1; e++) {
                // Get the current count for the card being added
                int thisCardAmount = cardAmounts.getOrDefault(e, 0);

                // Increase its amount by however many times it would be copied
                // down the chain of iteration.
                thisCardAmount += curAmount;

                // Set the instances of this duplicated card
                cardAmounts.put(e, thisCardAmount);
            }

            print("Amounts", cardAmounts);
            print("Cards", cards);
        }

        int sum = 0;

        // Sum up the instances of each card
        for (Integer id : cardAmounts.keySet()) {
            sum += cardAmounts.get(id);
        }

        print("Result", sum);
    }
}