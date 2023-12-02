import java.util.ArrayList;

import utils.AbstractDay;
import utils.MathHelper;
import utils.Pair;
import utils.Point;
import utils.Utils;

public class Day1 extends AbstractDay {
    public static void main(String[] args) {
        new Day1();
    }

    public Day1() {
        super(false, 2016);
    }

    @Override
    public void part1() {
        // input = "L5, L4, L3, L2, L1";

        String[] split = input.split(", ");

        int x = 0;
        int y = 0;

        int dir = 0; // up = 0, right = 1, down = 2, left = 3;

        for (String str : split) {
            char turn = str.charAt(0);
            int move = Integer.parseInt(str.substring(1));

            if (turn == 'R') {
                if (dir == 3) {
                    dir = 0;
                } else {
                    dir++;
                }
            } else if (turn == 'L') {
                if (dir == 0) {
                    dir = 3;
                } else {
                    dir--;
                }
            }

            if (dir == 0) {
                y += move;
            } else if (dir == 1) {
                x += move;
            } else if (dir == 2) {
                y -= move;
            } else if (dir == 3) {
                x -= move;
            }

            // print(turn + " " + move);
            // print("X " + x + ", Y " + y + " R " + dir);
        }

        // print("X", x);
        // print("Y", y);
        print("Distance", MathHelper.manhattanDistance(x, y));
    }

    @Override
    public void part2() {
        // input = "R8, R4, R4, R8";

        String[] split = input.split(", ");

        int x = 0;
        int y = 0;
        ArrayList<Point> visited = new ArrayList<>();

        int dir = 0; // up = 0, right = 1, down = 2, left = 3;

        Point doubleVisit = null;

        for (String str : split) {
            char turn = str.charAt(0);
            int move = Integer.parseInt(str.substring(1));

            if (turn == 'R') {
                if (dir == 3) {
                    dir = 0;
                } else {
                    dir++;
                }
            } else if (turn == 'L') {
                if (dir == 0) {
                    dir = 3;
                } else {
                    dir--;
                }
            }

            boolean done = false;

            if (visited.contains(new Point(x, y))) {
                doubleVisit = new Point(x, y);
                break;
            } else {
                // add all points in between
                int tempX = x;
                int tempY = y;

                visited.add(new Point(x, y));

                for (int i = 0; i < move - 1; i++) {
                    if (dir == 0) {
                        tempY += 1;
                    } else if (dir == 1) {
                        tempX += 1;
                    } else if (dir == 2) {
                        tempY -= 1;
                    } else if (dir == 3) {
                        tempX -= 1;
                    }

                    if (visited.contains(new Point(tempX, tempY))) {
                        doubleVisit = new Point(tempX, tempY);
                        done = true;
                        break;
                    } else {
                        visited.add(new Point(tempX, tempY));
                    }
                }

                if (done) {
                    break;
                }

                if (dir == 0) {
                    y += move;
                } else if (dir == 1) {
                    x += move;
                } else if (dir == 2) {
                    y -= move;
                } else if (dir == 3) {
                    x -= move;
                }

                // print(turn + " " + move);
                // print("X " + x + ", Y " + y + " R " + dir);

                // print(Utils.toString(visited));
            }
        }

        print("X", x);
        print("Y", y);
        print("Distance", MathHelper.manhattanDistance(doubleVisit.getFirst(), doubleVisit.getSecond()));
    }
}