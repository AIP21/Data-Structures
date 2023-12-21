// REPLACE with an opening Javadoc comment describing the program

// ArrayList will be our underlying data structure, 
// essentially used as a stack ADT
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TowersOfHanoi {
  // the pegs for the Towers of Hanoi problem
  // are labeled A, B, and C
  private static final String HANOI_PEGS = "ABC";

  // maintain a count of the number of moves
  private static int count;

  // the game state for Towers of Hanoi puzzle will
  // be an ArrayList of ArrayLists of Integers. Each list
  // will represent a peg, and each disc will be represented
  // by an Integer, where the bigger the Integer the
  // bigger the disc.
  private static ArrayList<ArrayList<Integer>> gameState = new ArrayList<>();

  // ANSI Color codes
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  // runs the puzzle, moving all of the discs (numDiscs) from peg fromPeg
  // to peg toPeg in the most efficient manner (i.e. fewest number of moves).
  public static void runTowersOfHanoi(int numDiscs, String fromPeg, String toPeg) {
    // Print the title
    animateTitleIn();

    // Wait for a second
    waitFor(1);

    // region Initialization
    // Initialize count to zero
    count = 0;

    // Populate gameState with 3 initially empty lists
    for (int i = 0; i < 3; i++) {
      gameState.add(new ArrayList<Integer>());
    }

    // Determine int equivalents of from, to, and via
    // from their index in HANOI_PEGS
    int from = HANOI_PEGS.indexOf(fromPeg);
    int to = HANOI_PEGS.indexOf(toPeg);
    int via = 3 - from - to;

    // Load up the from-peg with discs
    for (int i = numDiscs; i > 0; i--) {
      gameState.get(from).add(i);
    }
    // endregion

    // Display initial game state
    // showHanoiPegs();
    visualizeHanoiPegs();

    // Make the first recursive call!
    moveTower(numDiscs - 1, from, to, via);

    // 7. report the result!
    System.out.println("Ta-da!! It took " + count + " moves.");
  }

  public static void showHanoiPegs() {
    for (int i = 0; i < 3; i++) {
      System.out.print("peg " + HANOI_PEGS.substring(i, i + 1) + ": ");
      System.out.println(gameState.get(i));
    }
    System.out.println();
  }

  public static void visualizeHanoiPegs() {
    boolean animating = true;

    int timer = 0;

    try {
      while (animating) {
        String line = "";

        for (int i = 0; i < timer; i++) {
          line += " ";
        }

        line += "*";

        inprint(line);

        TimeUnit.SECONDS.sleep(1);

        if (timer >= 5) {
          animating = false;
        } else {
          timer += 1;
        }
      }
    } catch (Exception e) {

    }

    for (int i = 0; i < 3; i++) {
      System.out.print("peg " + HANOI_PEGS.substring(i, i + 1) + ": ");
      System.out.println(gameState.get(i));
    }
    System.out.println();
  }

  // Classic Towers of Hanoi recursive solution
  public static void moveTower(int diskNum, int from, int to, int via) {
    // base case: can ALWAYS move the smallest disk
    if (diskNum == 0) {
      // System.out.println("move disk 0 to peg " + HANOI_PEGS.substring(to, to + 1));

      // Move from peg "from" to peg "to" if it is legal
      if (canMove(0, getTop(to))) {
        // Remove the top of peg "from"
        removeTop(from);

        // Add it to peg "to"
        addToTop(to, 0);
      }

      // increment count
      count++;

      // display the game state
      // showHanoiPegs();
      visualizeHanoiPegs();
    } else {
      // move the tower on top of diskNum to the spare peg
      moveTower(diskNum - 1, from, via, to);

      // then move the bigger disk to the destination peg
      // System.out.println("move disk " + diskNum + " to peg " +
      // HANOI_PEGS.substring(to, to + 1));

      // Move from peg "from" to peg "to" if it is legal
      if (canMove(diskNum, getTop(to))) {
        // Remove the top of peg "from"
        removeTop(from);

        // Add it to peg "to"
        addToTop(to, diskNum);
      }

      // increment count
      count++;

      // then move the tower from spare peg to target peg
      moveTower(diskNum - 1, via, to, from);

      // display the game state
      visualizeHanoiPegs();
    }
  }

  private static Integer getTop(int peg) {
    ArrayList<Integer> pegList = gameState.get(peg);

    // If the peg is empty, return null
    if (pegList.isEmpty()) {
      return null;
    }

    // Otherwise, return the top of the peg
    else {
      return pegList.get(pegList.size() - 1);
    }
  }

  private static Integer removeTop(int peg) {
    ArrayList<Integer> pegList = gameState.get(peg);

    // If the peg is empty, return null
    if (pegList.isEmpty()) {
      return null;
    }

    // Otherwise, return the top of the peg
    else {
      return pegList.remove(pegList.size() - 1);
    }
  }

  private static void addToTop(int peg, Integer toAdd) {
    ArrayList<Integer> pegList = gameState.get(peg);

    pegList.add(pegList.size(), toAdd);
  }

  private static boolean canMove(Integer a, Integer b) {
    // If A is null, we can't move A to B
    if (a == null) {
      return false;
    }
    // Otherwise ,if B is null, we can move A to B
    else if (b == null) {
      return true;
    }
    // Otherwise, if A is less than B,
    // then we can move A to B
    else {
      return a < b;
    }
  }

  // region Animations
  private static void animateTitleIn() {
    boolean animating = true;

    int lineDelay = 50; // ms
    int titleDelay = 50; // ms

    int lineHalfLength = 0;

    String[] titleLines = {
        ANSI_RED + "   ___ ____ _ _ _ ____ ____ ____    ____ ____    _  _ ____ _  _ ____ _ ",
        ANSI_RED + "    |  |  | | | | |___ |__/ [__     |  | |___    |__| |__| |\\ | |  | | ",
        ANSI_RED + "    |  |__| |_|_| |___ |  \\ ___]    |__| |       |  | |  | | \\| |__| | ",
    };

    while (animating) {
      // Clear the terminal
      clearTerminal();

      // Animate the top and bottom lines of the title
      // Each half of the line slides in from the left and right

      // Calculate the amount of empty space between the left and right lines
      int emptySpace = 74 - (lineHalfLength * 2);

      String line = "";

      // Create the left half line string
      for (int i = 0; i < lineHalfLength; i++) {
        line += "=";
      }

      // Create the empty space
      for (int i = 0; i < emptySpace; i++) {
        line += " ";
      }

      // Create the right half line string
      for (int i = 0; i < lineHalfLength; i++) {
        line += "=";
      }

      print(line);
      print("");
      print("");
      print("");
      print(line);

      waitFor(lineDelay);

      // Increment the line half length
      lineHalfLength++;

      if (lineHalfLength * 2 >= 75) {
        animating = false;
      }
    }

    animating = true;

    int titleLineHalfLength = 0;

    int titleLength = titleLines[0].length();

    while (animating) {
      // Clear the terminal
      clearTerminal();

      // Animate the top and bottom lines of the title
      // Each half of the line slides in from the left and right

      // Calculate the amount of empty space between the left and right lines
      int emptySpace = titleLength - 1 - (titleLineHalfLength * 2);

      String[] lines = { "", "", "" };

      // Create the left half line string
      lines[0] = titleLines[0].substring(0, titleLineHalfLength);
      lines[1] = titleLines[1].substring(0, titleLineHalfLength);
      lines[2] = titleLines[2].substring(0, titleLineHalfLength);

      // Create the empty space
      for (int i = 0; i < emptySpace; i++) {
        lines[0] += " ";
        lines[1] += " ";
        lines[2] += " ";
      }

      // Create the right half line string
      lines[0] += titleLines[0].substring(titleLineHalfLength + emptySpace);
      lines[1] += titleLines[1].substring(titleLineHalfLength + emptySpace);
      lines[2] += titleLines[2].substring(titleLineHalfLength + emptySpace);

      print("=========================================================================");
      print(lines[0]);
      print(lines[1]);
      print(lines[2]);
      print("\n=========================================================================");

      waitFor(titleDelay);

      // Increment the title line half length
      titleLineHalfLength++;

      if (titleLineHalfLength * 2 >= titleLength) {
        animating = false;
      }
    }
  }

  private static void clearTerminal() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private static void inprint(String s) {
    System.out.print(s);
  }

  private static void print(String s) {
    System.out.println(s);
  }

  private static void waitFor(long milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (Exception e) {
      print("Error waiting");
    }
  }
}