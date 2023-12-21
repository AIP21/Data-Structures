// runs the Towers of Hanoi puzzle program

public class HanoiRunner {
    public static void main(String[] args) {

        // run this by passing 3 command line arguments:
        // number-of-discs, fromPeg, toPeg
        // where
        // number-of-discs must by a positive integer while
        // fromPeg and toPeg are either A, B, or C
        // and
        // default case will be 3 discs from A to C
        int numberOfDiscs = 3;
        String fromPeg = "A", toPeg = "C";

        if (args.length == 3) {
            numberOfDiscs = Integer.parseInt(args[0]);
            fromPeg = args[1];
            toPeg = args[2];
        }

        // System.out.print("Moving the tower with " + numberOfDiscs + " discs");
        // System.out.println(" from peg " + fromPeg + " to peg " + toPeg);

        TowersOfHanoi.runTowersOfHanoi(numberOfDiscs, fromPeg, toPeg);

    }
}
