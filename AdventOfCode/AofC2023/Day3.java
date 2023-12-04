import java.util.ArrayList;
import java.util.HashMap;

import utils.*;

public class Day3 extends AbstractDay {
    public static void main(String[] args) {
        new Day3();
    }

    public Day3() {
        super(false, 2023);
    }

    @Override
    public void part1() {
        // testInput("""
        // 467..114..
        // ...*......
        // ..35..633.
        // ......#...
        // 617*......
        // .....+.58.
        // ..592.....
        // ......755.
        // ...$.*....
        // .664.598..
        // """);

        long sum = 0;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            char[] chars = line.toCharArray();

            boolean curIsPartNum = false;

            String curNumber = "";

            for (int x = 0; x < chars.length; x++) {
                char ch = chars[x];

                if (Utils.isDigit(ch)) {
                    curNumber += ch;

                    if (!curIsPartNum) {
                        curIsPartNum = checkAround(x, y);
                    }
                } else {
                    if (curNumber != "") {
                        // end of number
                        int num = Integer.parseInt(curNumber);

                        if (curIsPartNum) {
                            sum += num;

                            // print("part num", curNumber);
                            // print("pos", x + ", " + y);
                        } else {
                            // print("not a part num", curNumber);
                            // print("pos", x + ", " + y);
                        }
                    }

                    curNumber = "";
                    curIsPartNum = false;
                }
            }

            if (curNumber != "") {
                // end of number
                int num = Integer.parseInt(curNumber);

                if (curIsPartNum) {
                    sum += num;

                    // print("part num", curNumber);
                    // print("pos", x + ", " + y);
                } else {
                    // print("not a part num", curNumber);
                    // print("pos", "end" + ", " + y);
                }
            }
        }

        print("Result", sum);
    }

    private boolean checkAround(int x, int y) {
        char[] aboveLine = lines.get(Math.max(0, y - 1)).toCharArray();
        char[] curLine = lines.get(y).toCharArray();
        char[] belowLine = lines.get(Math.min(lines.size() - 1, y + 1)).toCharArray();

        char aboveLeft = aboveLine[Math.max(0, x - 1)];
        char above = aboveLine[x];
        char aboveRight = aboveLine[Math.min(aboveLine.length - 1, x + 1)];

        char left = curLine[Math.max(0, x - 1)];
        char right = curLine[Math.min(curLine.length - 1, x + 1)];

        char belowLeft = belowLine[Math.max(0, x - 1)];
        char below = belowLine[x];
        char belowRight = belowLine[Math.min(belowLine.length - 1, x + 1)];

        if (isSpecialChar(aboveLeft) || isSpecialChar(above) || isSpecialChar(aboveRight) || isSpecialChar(left)
                || isSpecialChar(right) || isSpecialChar(belowLeft) || isSpecialChar(below)
                || isSpecialChar(belowRight)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isSpecialChar(char ch) {
        return (!Utils.isDigit(ch) && ch != '.');
    }

    @Override
    public void part2() {
        // testInput("""
        // 467..114..
        // ...*......
        // ..35..633.
        // ......#...
        // 617*......
        // .....+.58.
        // ..592.....
        // ......755.
        // ...$.*....
        // .664.598..
        // """);

        long sum = 0;

        HashMap<Vec2, ArrayList<Integer>> starPartCount = new HashMap<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            char[] chars = line.toCharArray();

            ArrayList<Vec2> belongsToStars = new ArrayList<>();

            boolean curIsPartNum = false;

            String curNumber = "";

            for (int x = 0; x < chars.length; x++) {
                char ch = chars[x];

                if (Utils.isDigit(ch)) {
                    curNumber += ch;

                    if (!curIsPartNum) {
                        char[] aboveLine = lines.get(Math.max(0, y - 1)).toCharArray();
                        char[] curLine = lines.get(y).toCharArray();
                        char[] belowLine = lines.get(Math.min(lines.size() - 1, y + 1)).toCharArray();

                        char aboveLeft = aboveLine[Math.max(0, x - 1)];
                        char above = aboveLine[x];
                        char aboveRight = aboveLine[Math.min(aboveLine.length - 1, x + 1)];

                        char left = curLine[Math.max(0, x - 1)];
                        char right = curLine[Math.min(curLine.length - 1, x + 1)];

                        char belowLeft = belowLine[Math.max(0, x - 1)];
                        char below = belowLine[x];
                        char belowRight = belowLine[Math.min(belowLine.length - 1, x + 1)];

                        if (isSpecialChar(aboveLeft) || isSpecialChar(above) || isSpecialChar(aboveRight)
                                || isSpecialChar(left)
                                || isSpecialChar(right) || isSpecialChar(belowLeft) || isSpecialChar(below)
                                || isSpecialChar(belowRight)) {
                            curIsPartNum = true;

                            if (aboveLeft == '*') {
                                Vec2 pos = new Vec2(Math.max(0, x - 1),
                                        Math.max(0, y - 1));

                                belongsToStars.add(pos);

                                // print(curNumber, "AL");
                            }
                            if (above == '*') {
                                Vec2 pos = new Vec2(x, Math.max(0, y - 1));
                                belongsToStars.add(pos);

                                // print(curNumber, "A");
                            }
                            if (aboveRight == '*') {
                                Vec2 pos = new Vec2(
                                        Math.min(aboveLine.length - 1, x + 1), Math.max(0, y - 1));
                                belongsToStars.add(pos);

                                // print(curNumber, "AR");
                            }

                            if (left == '*') {
                                Vec2 pos = new Vec2(Math.max(0, x - 1), y);
                                belongsToStars.add(pos);

                                // print(curNumber, "L");
                            }
                            if (right == '*') {
                                Vec2 pos = new Vec2(
                                        Math.min(curLine.length - 1, x + 1), y);
                                belongsToStars.add(pos);

                                // print(curNumber, "R");
                            }

                            if (belowLeft == '*') {
                                Vec2 pos = new Vec2(Math.max(0, x - 1), Math.min(lines.size() - 1, y + 1));
                                belongsToStars.add(pos);

                                // print(curNumber, "BL");
                            }
                            if (below == '*') {
                                Vec2 pos = new Vec2(x, Math.min(lines.size() - 1, y + 1));
                                belongsToStars.add(pos);

                                // print(curNumber, "B");
                            }
                            if (belowRight == '*') {
                                Vec2 pos = new Vec2(Math.min(belowLine.length - 1, x + 1),
                                        Math.min(lines.size() - 1, y + 1));
                                belongsToStars.add(pos);

                                // print(curNumber, "BR");
                            }
                        }
                    }
                } else {
                    if (curNumber != "") {
                        // end of number
                        int num = Integer.parseInt(curNumber);

                        if (curIsPartNum) {
                            for (Vec2 star : belongsToStars) {
                                if (!starPartCount.containsKey(star)) {
                                    starPartCount.put(star, new ArrayList<Integer>());
                                }

                                starPartCount.getOrDefault(star, new ArrayList<Integer>()).add(num);
                            }

                            // print(curNumber, belongsToStars);

                            // print("part num", curNumber);
                            // print("pos", x + ", " + y);
                        } else {
                            // print("not a part num", curNumber);
                            // print("pos", "end" + ", " + y);
                        }
                    }

                    curNumber = "";
                    curIsPartNum = false;
                    belongsToStars = new ArrayList<>();
                }
            }

            if (curNumber != "") {
                // end of number
                int num = Integer.parseInt(curNumber);

                if (curIsPartNum) {
                    for (Vec2 star : belongsToStars) {
                        if (!starPartCount.containsKey(star)) {
                            starPartCount.put(star, new ArrayList<Integer>());
                        }

                        starPartCount.getOrDefault(star, new ArrayList<Integer>()).add(num);
                    }

                    // print(curNumber, belongsToStars);

                    // print("part num", curNumber);
                    // print("pos", x + ", " + y);
                } else {
                    // print("not a part num", curNumber);
                    // print("pos", "end" + ", " + y);
                }
            }
        }

        print(starPartCount);

        for (Vec2 star : starPartCount.keySet()) {
            if (starPartCount.get(star).size() == 2) {
                int gearRatio = starPartCount.get(star).get(0) * starPartCount.get(star).get(1);

                sum += gearRatio;
            }
        }

        print("Result", sum);
    }
}