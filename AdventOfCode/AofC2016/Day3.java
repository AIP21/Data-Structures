import utils.AbstractDay;
import utils.Utils;

public class Day3 extends AbstractDay {
    public static void main(String[] args) {
        new Day3();
    }

    public Day3() {
        super(false);
    }

    @Override
    public void part1() {
        // testInput("""
        // 5 10 25
        // 7 10 5
        // """);

        int possible = 0;

        for (int i = 0; i < lines.size(); i++) {
            Integer[] sides = intParser.parseLine(i);

            // If the triangle is possible, increment the counter by one
            if (isTriangle(sides[0], sides[1], sides[2])) {
                possible++;
            }
        }

        print("Possible triangles", possible);
        print("Total triangles", lines.size());
    }

    @Override
    public void part2() {
        // testInput("""
        // 101 301 501
        // 102 302 502
        // 103 303 503
        // 201 401 601
        // 202 402 602
        // 203 403 603
        // """);

        int possible = 0;

        // Iterate by three rows at a time
        for (int i = 0; i < lines.size(); i += 3) {
            // Get the next three rows
            Integer[] row1 = intParser.parseLine(i);
            Integer[] row2 = intParser.parseLine(i + 1);
            Integer[] row3 = intParser.parseLine(i + 2);

            // Get three triangles from the three rows
            int[] triangle1 = new int[] { row1[0], row2[0], row3[0] };
            int[] triangle2 = new int[] { row1[1], row2[1], row3[1] };
            int[] triangle3 = new int[] { row1[2], row2[2], row3[2] };

            // print(Utils.toString(triangle1));
            // print(Utils.toString(triangle2));
            // print(Utils.toString(triangle3));

            // If the first triangle is possible, increment the counter by one
            if (isTriangle(triangle1[0], triangle1[1], triangle1[2])) {
                possible++;
            }

            // If the second triangle is possible, increment the counter by one
            if (isTriangle(triangle2[0], triangle2[1], triangle2[2])) {
                possible++;
            }

            // If the third triangle is possible, increment the counter by one
            if (isTriangle(triangle3[0], triangle3[1], triangle3[2])) {
                possible++;
            }
        }

        print("Possible triangles", possible);
        print("Total triangles", lines.size());
    }

    // The sum of any two sides must be larger than the remaining side
    private boolean isTriangle(int a, int b, int c) {
        return !(a + b <= c || a + c <= b || b + c <= a);
    }
}