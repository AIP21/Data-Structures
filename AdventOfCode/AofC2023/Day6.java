import utils.*;

public class Day6 extends AbstractDay {
    public static void main(String[] args) {
        new Day6();
    }

    public Day6() {
        super(false, 2023);
    }

    @Override
    public void part1() {
        testInput(
                "Time:        42     89     91     89\n" +
                        "Distance:   308   1170   1291   1467");

        Integer[] times = intParser.parseString(lines.get(0).replace("Time: ", ""));
        Integer[] distances = intParser.parseString(lines.get(1).replace("Distance: ", ""));

        int product = 1;

        print(Utils.toString(times));
        print(Utils.toString(distances));

        for (int i = 0; i < times.length; i++) {
            int aboveRecord = 0;

            for (int s = 0; s <= times[i]; s++) {
                int dist = (times[i] - s) * s;

                if (dist > distances[i]) {
                    aboveRecord++;
                    // print("above", dist);
                } else {
                    // print("not above", dist);
                }
            }

            product *= aboveRecord;
            // print("num above", aboveRecord);
        }

        print("Result", product);
    }

    @Override
    public void part2() {
        testInput("Time:        42     89     91     89\n" +
                "Distance:   308   1170   1291   1467");
        // testInput("Time: 7 15 30\n"+
        // "Distance: 9 40 200");

        long time = Long.parseLong(lines.get(0).replace("Time: ", "").replace(" ", ""));
        long distance = Long.parseLong(lines.get(1).replace("Distance: ", "").replace(" ", ""));

        print("Time", time);
        print("Dist", distance);

        long aboveRecord = 0;

        for (long s = 0; s <= time; s++) {
            long dist = (time - s) * s;

            if (dist > distance) {
                aboveRecord++;
                // print("above", dist);
            } else {
                // print("not above", dist);
            }

            if (s % time == 10) {
                print("index", s);
            }
        }

        print("Result", aboveRecord);
    }
}