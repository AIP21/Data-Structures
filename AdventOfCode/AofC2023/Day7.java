import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import utils.*;

public class Day7 extends AbstractDay {
    public static void main(String[] args) {
        new Day7();
    }

    public Day7() {
        super(false, 2023);
    }

    private class Hand implements Comparable {
        public String str;
        public int bid;

        public Hand(String cards, int bid) {
            this.str = cards;
            this.bid = bid;
        }

        public String typeRankStr() {
            // Get the number of each card type in this hand
            HashMap<Character, Integer> cardCount = new HashMap<>();

            int highestCount = 0;
            char highestCountChar = ' ';

            for (int i = 0; i < str.length(); i++) {
                int val = cardCount.getOrDefault(str.charAt(i), 0) + 1;

                cardCount.put(str.charAt(i), val);

                if (val > highestCount) {
                    highestCount = val;
                    highestCountChar = str.charAt(i);
                }
            }

            if (highestCount == 5) {
                return "Five of a kind";
            } else if (highestCount == 4) {
                return "Four of a kind";
            } else if (highestCount == 3) {
                String removed = str.replace(highestCountChar + "", "");

                if (removed.charAt(0) == removed.charAt(1)) {
                    return "Full house";
                } else {
                    return "Three of a kind";
                }
            } else if (highestCount == 2) {
                int instancesOfTwo = 0;

                for (Character card : cardCount.keySet()) {
                    int count = cardCount.get(card);

                    if (count == 2) {
                        instancesOfTwo++;
                    }
                }

                if (instancesOfTwo == 2) {
                    return "Two pair";
                } else if (instancesOfTwo == 1) {
                    return "One pair";
                }
            } else {
                return "High card";
            }

            return "None";
        }

        public int typeRank() {
            // Get the number of each card type in this hand
            HashMap<Character, Integer> cardCount = new HashMap<>();

            int highestCount = 0;
            char highestCountChar = ' ';

            for (int i = 0; i < str.length(); i++) {
                int val = cardCount.getOrDefault(str.charAt(i), 0) + 1;

                cardCount.put(str.charAt(i), val);

                if (val > highestCount) {
                    highestCount = val;
                    highestCountChar = str.charAt(i);
                }
            }

            if (highestCount == 5) {
                return 6;
            } else if (highestCount == 4) {
                return 5;
            } else if (highestCount == 3) {
                String removed = str.replace(highestCountChar + "", "");

                if (removed.charAt(0) == removed.charAt(1)) {
                    return 4;
                } else {
                    return 3;
                }
            } else if (highestCount == 2) {
                int instancesOfTwo = 0;

                for (Character card : cardCount.keySet()) {
                    int count = cardCount.get(card);

                    if (count == 2) {
                        instancesOfTwo++;
                    }
                }

                if (instancesOfTwo == 2) {
                    return 2;
                } else if (instancesOfTwo == 1) {
                    return 1;
                }
            } else {
                return 0;
            }

            return 0;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Hand) {
                Hand other = (Hand) o;

                // Compare ranks
                int thisRank = typeRank();
                int otherRank = other.typeRank();

                if (thisRank > otherRank) {
                    return 1;
                } else if (thisRank < otherRank) {
                    return -1;
                } else {
                    return compareCards(other);
                }
            }

            return 0;
        }

        public int compareCards(Hand other) {
            String thisStr = replaceChars(this.str);
            String otherStr = replaceChars(other.str);

            return thisStr.compareTo(otherStr);
        }

        private String replaceChars(String str) {
            str = str.replace("A", "M");
            str = str.replace("K", "L");
            str = str.replace("Q", "K");
            // str = str.replace("J", "J");
            str = str.replace("T", "I");

            str = str.replace("2", "A");
            str = str.replace("3", "B");
            str = str.replace("4", "C");
            str = str.replace("5", "D");
            str = str.replace("6", "E");
            str = str.replace("7", "F");
            str = str.replace("8", "G");
            str = str.replace("9", "H");

            return str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @Override
    public void part1() {
        // testInput("""
        // 32T3K 765
        // T55J5 684
        // KK677 28
        // KTJJT 220
        // QQQJA 483
        // """);

        ArrayList<Hand> hands = new ArrayList<Hand>();

        for (String line : lines) {
            Hand hand = new Hand(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
            // print(hand.str, hand.typeRank());

            hands.add(hand);

            // print(hand.str, hand.typeRankStr());
        }

        // Sort the list of hands
        Collections.sort(hands);

        // print(Utils.toString(hands));

        int totalWinnings = 0;

        for (int i = 0; i < hands.size(); i++) {
            totalWinnings += hands.get(i).bid * (i + 1);

            // print(hands.get(i).bid + " * " + (i + 1));
        }

        print("Total Winnings", totalWinnings);
    }

    private class Hand2 implements Comparable {
        public String str;
        public int bid;

        public Hand2(String cards, int bid) {
            this.str = cards;
            this.bid = bid;
        }

        public String typeRankStr() {
            int rank = typeRank();

            if (rank == 6) {
                return "Five of a kind";
            } else if (rank == 5) {
                return "Four of a kind";
            } else if (rank == 4) {
                return "Full house";
            } else if (rank == 3) {
                return "Three of a kind";
            } else if (rank == 2) {
                return "Two pair";
            } else if (rank == 1) {
                return "One pair";
            } else {
                return "High card";
            }
        }

        // public int typeRank() {
        // // Get the number of each card type in this hand
        // HashMap<Character, Integer> cardCount = new HashMap<>();

        // int highestCount = 0;
        // char highestCountChar = ' ';

        // for (int i = 0; i < str.length(); i++) {
        // int val = cardCount.getOrDefault(str.charAt(i), 0) + 1;

        // cardCount.put(str.charAt(i), val);

        // if (val > highestCount && str.charAt(i) != 'J') {
        // highestCount = val;
        // highestCountChar = str.charAt(i);
        // }
        // }

        // highestCount += cardCount.getOrDefault('J', 0);

        // if (highestCount >= 5) {
        // return 6;
        // } else if (highestCount == 4) {
        // return 5;
        // } else if (highestCount == 3) {
        // String removed = str.replace(highestCountChar + "", "");

        // if (removed.charAt(0) == removed.charAt(1) || removed.charAt(0) == 'J' ||
        // removed.charAt(1) == 'J') {
        // return 4;
        // } else {
        // return 3;
        // }
        // } else if (highestCount == 2) {
        // int instancesOfTwo = 0;

        // for (Character card : cardCount.keySet()) {
        // int count = cardCount.get(card);

        // if (count == 2) {
        // instancesOfTwo++;
        // }
        // }

        // instancesOfTwo += cardCount.getOrDefault('J', 0);

        // return instancesOfTwo;
        // } else {
        // return cardCount.getOrDefault('J', 0);
        // }

        // // return 0;
        // }

        public int typeRank() {
            Map<Character, Long> cardFrequency = str.chars()
                    .mapToObj(i -> (char) i)
                    .collect(groupingBy(identity(), counting()));

            Map<Long, Long> groupFrequency = cardFrequency.values()
                    .stream()
                    .collect(groupingBy(identity(), counting()));

            var wildCards = cardFrequency.getOrDefault('J', 0L);

            long singles = groupFrequency.getOrDefault(1L, -1L);
            long pairs = groupFrequency.getOrDefault(2L, -1L);
            long trios = groupFrequency.getOrDefault(3L, -1L);
            long quartets = groupFrequency.getOrDefault(4L, -1L);

            // high card
            if (singles == str.length()) {
                return wildCards > 0 ? 1 : 0;
            }
            // one pair
            if (pairs == 1 && singles == 3) {
                return wildCards > 0 ? 3 : 1;
            }
            // two pair
            if (pairs == 2 && singles == 1) {
                if (wildCards == 0)
                    return 2;
                return wildCards == 1 ? 4 : 5;
            }
            // three of a kind
            if (trios == 1 && singles == 2) {
                return wildCards > 0 ? 5 : 3;
            }
            // full house
            if (trios == 1 && pairs == 1) {
                return wildCards > 0 ? 6 : 4;
            }
            // four of a kind
            if (quartets == 1 && singles == 1) {
                return wildCards > 0 ? 6 : 5;
            }
            // five of a kind
            return 6;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Hand2) {
                Hand2 other = (Hand2) o;

                // Compare ranks
                int thisRank = typeRank();
                int otherRank = other.typeRank();

                if (thisRank > otherRank) {
                    return 1;
                } else if (thisRank < otherRank) {
                    return -1;
                } else {
                    return compareCards(other);
                }
            }

            return 0;
        }

        public int compareCards(Hand2 other) {
            String thisStr = replaceChars(this.str);
            String otherStr = replaceChars(other.str);

            return thisStr.compareTo(otherStr);
        }

        private String replaceChars(String str) {
            str = str.replace("A", "N");
            str = str.replace("K", "M");
            str = str.replace("Q", "L");
            str = str.replace("J", "A");
            str = str.replace("T", "J");

            str = str.replace("2", "B");
            str = str.replace("3", "C");
            str = str.replace("4", "D");
            str = str.replace("5", "E");
            str = str.replace("6", "F");
            str = str.replace("7", "G");
            str = str.replace("8", "H");
            str = str.replace("9", "I");

            return str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @Override
    public void part2() {
        // testInput("""
        // 32T3K 765
        // T55J5 684
        // KK677 28
        // KTJJT 220
        // QQQJA 483
        // """);

        // testInput("""
        // 37973 197
        // 555K2 816
        // 42422 45
        // 3AAAA 699
        // A77A4 530
        // K9QQK 785
        // 67K36 694
        // 933J4 320
        // QQ98Q 267
        // 22KK6 306
        // 2AKAK 77
        // 7775J 518
        // 3T586 952
        // 34J58 132
        // 3353T 773
        // TT353 480
        // 5777A 459
        // 79333 280
        // 7TK8T 242
        // K7A3Q 444
        // KK6Q4 575
        // T5AKA 256
        // 858TT 670
        // A7KA8 731
        // 67676 92
        // T966T 616
        // 9Q765 566
        // 23Q92 322
        // 356A6 957
        // 996Q9 687
        // 6398Q 578
        // 52552 107""");

        // testInput("""
        // JKKK2 10
        // QQQQ2 20
        // """);

        ArrayList<Hand2> hands = new ArrayList<>();

        for (String line : lines) {
            Hand2 hand = new Hand2(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
            // print(hand.str, hand.typeRank());

            hands.add(hand);

            // print(hand.str, hand.typeRankStr());
        }

        // Sort the list of hands
        Collections.sort(hands);

        // print(Utils.toString(hands));

        long totalWinnings = 0;

        for (int i = 0; i < hands.size(); i++) {
            totalWinnings += (long) hands.get(i).bid * (long) (i + 1);

            // print(hands.get(i).bid + " * " + (i + 1));
        }

        print("Total Winnings", totalWinnings);
    }

    private int getRelativeStrength(char card, boolean isPartTwo) {
        if (isPartTwo && card == 'J')
            card = '1';
        return switch (card) {
            case 'A' -> 62;
            case 'K' -> 61;
            case 'Q' -> 60;
            case 'J' -> 59;
            case 'T' -> 58;
            default -> card;
        };
    }

}