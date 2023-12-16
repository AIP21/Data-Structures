import java.util.ArrayList;

import utils.*;

public class Day9 extends AbstractDay {
    public static void main(String[] args) {
        new Day9();
    }

    public Day9() {
        super(false, 2023);
    }

    @Override
    public void part1() {
        testInput("""
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
                """);

        for (String line : lines) {
            ArrayList<Integer> vals = new ArrayList<>();
            Integer[] vl = intParser.parseString(line);

            for (int v : vl) {
                vals.add(v);
            }

            int steps = nextValue(vals, 1);

            print(steps);
        }
    }

    private int nextValue(ArrayList<Integer> vals, int steps) {
        ArrayList<Integer> differences = new ArrayList<>();
        int diffSum = 0;

        for (int i = 0; i < vals.size() - 1; i++) {
            int diff = vals.get(i + 1) - vals.get(i);
            diffSum += diff;

            differences.add(diff);
        }

        if (diffSum == 0) {
            return steps;
        } else {
            return nextValue(differences, steps + 1);
        }
    }

    @Override
    public void part2() {

    }
}