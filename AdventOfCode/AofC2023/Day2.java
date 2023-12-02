import utils.*;

public class Day2 extends AbstractDay {
    public static void main(String[] args) {
        new Day2();
    }

    public Day2() {
        super(false, 2023);
    }

    @Override
    public void part1() {
        // testInput("""
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        // Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        // Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        // Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        // Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        // """);

        int sum = 0;

        for (String game : lines) {
            int id = Integer.parseInt(game.split(": ")[0].replace("Game ", ""));

            String[] sets = game.split(": ")[1].split("; ");

            boolean valid = true;

            for (String set : sets) {
                String[] colors = set.split(", ");

                for (String color : colors) {
                    if (color.contains("red") && Integer.parseInt(color.replace(" red", "")) > 12) {
                        valid = false;
                        break;
                    } else if (color.contains("green") && Integer.parseInt(color.replace(" green", "")) > 13) {
                        valid = false;
                        break;
                    } else if (color.contains("blue") && Integer.parseInt(color.replace(" blue", "")) > 14) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    break;
                }
            }

            if (valid) {
                sum += id;
            }
        }

        print("Result", sum);
    }

    @Override
    public void part2() {
        // testInput("""
        // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        // Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        // Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        // Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        // Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        // """);

        int sum = 0;

        for (String game : lines) {
            int id = Integer.parseInt(game.split(": ")[0].replace("Game ", ""));

            String[] sets = game.split(": ")[1].split("; ");

            int maxRed = -1, maxGreen = -1, maxBlue = -1;

            for (String set : sets) {
                String[] colors = set.split(", ");

                for (String color : colors) {
                    if (color.contains("red")) {
                        int num = Integer.parseInt(color.replace(" red", ""));

                        if (num > maxRed) {
                            maxRed = num;
                        }

                    } else if (color.contains("green")) {
                        int num = Integer.parseInt(color.replace(" green", ""));

                        if (num > maxGreen) {
                            maxGreen = num;
                        }

                    } else if (color.contains("blue")) {
                        int num = Integer.parseInt(color.replace(" blue", ""));

                        if (num > maxBlue) {
                            maxBlue = num;
                        }

                    }
                }
            }

            sum += maxRed * maxGreen * maxBlue;
        }

        print("Result", sum);

    }
}