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
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_BLACK = "\u001B[30m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_WHITE = "\u001B[37m";

  private static final String[] ANSI_COLORS = {
      ANSI_RESET,
      ANSI_BLACK,
      ANSI_RED,
      ANSI_GREEN,
      ANSI_YELLOW,
      ANSI_BLUE,
      ANSI_PURPLE,
      ANSI_CYAN,
      ANSI_WHITE,
  };

  // The total number of discs
  private static int totalDiscs;

  // Animation variables
  private static boolean doDiscAnimation = false;
  private static int discAnimNum = -1;
  private static int discAnimFrom = -1;
  private static int discAnimTo = -1;

  // runs the puzzle, moving all of the discs (numDiscs) from peg fromPeg
  // to peg toPeg in the most efficient manner (i.e. fewest number of moves).
  public static void runTowersOfHanoi(int numDiscs, String fromPeg, String toPeg) {
    // Print the title
    animateTitleIn();

    // Pause for half a second
    waitFor(500);

    // Remember the total number of discs in the system
    totalDiscs = numDiscs;

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
    print("Initial State");
    doDiscAnimation = false;
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
    boolean discAnimating = true;

    int discAnimationDelay = 100; // ms
    int discAnimationFrames = 25;

    // If we are not going to animate this time,
    // then just set the animation length to 0
    if (!doDiscAnimation) {
      discAnimationFrames = 0;
    }

    int frame = 0;

    while (discAnimating) {
      print(frame + "");

      // Do the animation
      animateDiscMove(discAnimNum, discAnimFrom, discAnimTo, frame);

      // Wait for a small time delay
      // (otherwise the animation would be too fast)
      waitFor(discAnimationDelay);

      // Clear terminal
      // clearTerminal();

      // End case for the animation
      if (frame >= discAnimationFrames) {
        discAnimating = false;
      }
      // If not done, increment frame counter
      else {
        frame++;
      }
    }
  }

  // Draw the towers with an animation of disc movement
  private static void animateDiscMove(int discAnimNum, int animPegFrom, int animPegTo, int animFrame) {
    // Add empty spacing above
    print("");

    // Print the move number
    if (count != 0) {
      print("\nMove " + count);
    }

    // Determine the animation direction
    int animDirection = animPegFrom - animPegTo < 0 ? -1 : 1;

    // Create a list of strings to store the image being drawn
    // The number of lines is the height of each peg
    // (number of discs + 4 for the top peg label and separator line,
    // top extra spacing and peg base layer)
    String[] lines = new String[totalDiscs + 4];

    // Populate the lines array
    for (int i = 0; i < lines.length; i++) {
      lines[i] = "";
    }

    // Figure out the spacing between discs
    int whitespace = 5;
    int pegWidth = totalDiscs * 2 + 2; // Maximum width of a peg base

    // Line to animate
    int lineBeingAnimated = -1;

    // Draw all the pegs and their discs
    // Go through every peg
    for (int peg = 0; peg < HANOI_PEGS.length(); peg++) {
      // Determine what this peg's whitespace should be
      // Should be one less for the first peg
      int pegWhitespace = peg == 0 ? whitespace - 1 : whitespace;

      // region Top part
      // Add the whitespace before the peg name
      for (int e = 0; e < pegWhitespace + totalDiscs; e++) {
        lines[0] += " ";
      }

      // Add the peg name
      lines[0] += HANOI_PEGS.charAt(peg);

      // Add the whitespace after the peg name
      for (int e = 0; e < totalDiscs + 1; e++) {
        lines[0] += " ";
      }

      // Add a separator line below the peg names
      for (int e = 0; e < pegWhitespace + pegWidth + 1; e++) {
        lines[1] += "─";
      }
      // endregion

      // region Peg level drawing
      // Go through every line and draw a disc if it exists there
      for (int l = lines.length - 2; l >= 2; l--) {
        // Get the disc size at this line
        int discNum = getDiscAt(peg, totalDiscs - l + 3);
        int discSize = discNum + 1;

        // region Whitespace and peg drawing
        // Add the extra whitespace before the peg
        // and before the disc starts
        for (int e = 0; e < pegWhitespace + (totalDiscs - discSize); e++) {
          lines[l] += " ";
        }

        // region Disc drawing
        // Make sure this is the disc being animated
        if (discNum == discAnimNum && doDiscAnimation == true) {
          lineBeingAnimated = l;

          lines[l] += "║";
        }
        // Otherwise, draw the disc normally
        else  {
          // Set the draw color to the disc color
          if (discNum != -1) {
            lines[l] += getDiscColor(discNum + 2);
          }

          // Draw the first half of the disc
          for (int e = 0; e < discSize; e++) {
            lines[l] += "█";
          }

          // Draw the peg (if no disc exists)
          if (discNum == -1) {
            lines[l] += "║";
          }
          // Otherwise draw the center piece of the disc
          else {
            lines[l] += "█";
          }

          // Draw the second half of the disc
          for (int e = 0; e < discSize; e++) {
            lines[l] += "█";
          }

          // Reset the line color
          lines[l] += ANSI_RESET;
        }
        // endregion

        // Draw the remaining empty space (NOT whitespace) after the disc ends
        for (int e = 0; e < totalDiscs - discSize + 1; e++) {
          lines[l] += " ";
        }
        // endregion
      }
      // endregion

      // region Bottom peg drawing
      // Draw the bottom line (the peg base)
      // Draw the whitespace first
      for (int c = 0; c < pegWhitespace - 1; c++) {
        lines[lines.length - 1] += " ";
      }

      // Draw the peg base
      for (int c = 0; c <= pegWidth; c++) {
        // Draw the central base
        if (c == pegWidth / 2) {
          lines[lines.length - 1] += "╩";
        }
        // Otherwise, draw the end part of the base
        else if (c == 0) {
          lines[lines.length - 1] += "╔";
        }
        // Otherwise, draw the start part of the base
        else if (c == pegWidth) {
          lines[lines.length - 1] += "╗";
        }
        // Otherwise, draw the rest of the base
        else {
          lines[lines.length - 1] += "═";
        }
      }
      // endregion
    }

    // region Animated disc drawing
    if (doDiscAnimation) {
      print("ANIM");
      // Get the disc size at this line
      int discSize = discAnimNum + 1;

      // Disc draw offset for animation
      int discOffset = animFrame * animDirection;

      // Calculate disc start and end indices
      int startIndex = interpolate(1 + whitespace + (totalDiscs - discSize), animPegFrom * pegWidth,
          animFrame / (float) animFrame);
      int endIndex = interpolate(1 + whitespace + (totalDiscs - discSize), animPegTo * pegWidth,
          animFrame / (float) animFrame) + discSize * 2 - 1;

      // Create a string builder for overwriting the line string
      StringBuilder builder = new StringBuilder(lines[lineBeingAnimated]);

      // Set the draw color to the disc color
      builder.insert((startIndex + discOffset) - 1, getDiscColor(discAnimNum + 2));

      // Draw the disc over the current string
      for (int i = startIndex + discOffset; i <= endIndex + discOffset; i++) {
        builder.setCharAt(i, '█');
      }

      // Reset the line color
      builder.insert(endIndex + 1, ANSI_RESET);

      print(builder.toString());

      // Replace the line string with the overwritten string
      lines[lineBeingAnimated] = builder.toString();
    }
    // endregion

    // Draw every line
    for (String line : lines) {
      print(line);
    }
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
      doDiscAnimation = true;
      discAnimNum = diskNum;
      discAnimFrom = from;
      discAnimTo = to;
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
      doDiscAnimation = true;
      discAnimNum = diskNum;
      discAnimFrom = from;
      discAnimTo = to;
      visualizeHanoiPegs();
    }
  }

  /**
   * Get the color of a given disc, as an ANSI color code
   * 
   * @param discNumber The number of the disc to get the color for
   * @return The ANSI color code for the given disc
   */
  private static String getDiscColor(int discNumber) {
    // Make sure the disc number is within
    // range of the color code array
    while (discNumber >= ANSI_COLORS.length) {
      discNumber -= ANSI_COLORS.length;
    }

    // Return the proper color for this disc
    return ANSI_COLORS[discNumber];
  }

  /**
   * Interpolate between two integers
   * 
   * @param a The first integer
   * @param b The second integer
   * @param f The interpolation factor. A value of 0.0 will return a, a value of
   *          1.0
   *          will return b
   * @return The interpolated integer
   */
  private static int interpolate(int a, int b, float f) {
    return (int) ((float) a * (1.0f - (float) f) + ((float) b * (float) f));
  }

  // region Disc Manipulation
  /**
   * Get the topmost disc in a given peg
   * 
   * @param peg The index of the peg you want the topmost disc from
   * @return The topmost disc in that peg
   */
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

  /**
   * Get the disc number at a given index at a given peg
   * 
   * @param peg   The peg to get the disc from, must be within the range of pegs
   * @param index The index to get the disc from
   * @return The disc at the index on the peg, or -1 if no disc exists at that
   *         index
   */
  private static Integer getDiscAt(int peg, int index) {
    // Default case to return -1
    if (index >= gameState.get(peg).size()) {
      return -1;
    } else {
      // Otherwise return the disc
      return gameState.get(peg).get(index);
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
  // endregion

  // region Animations
  private static void animateTitleIn() {
    boolean animating = true;

    int lineDelay = 25; // ms
    int titleDelay = 25; // ms

    int lineHalfLength = 0;

    String[] titleLines = {
        "   ___ ____ _ _ _ ____ ____ ____    ____ ____    _  _ ____ _  _ ____ _ ",
        "    |  |  | | | | |___ |__/ [__     |  | |___    |__| |__| |\\ | |  | | ",
        "    |  |__| |_|_| |___ |  \\ ___]    |__| |       |  | |  | | \\| |__| | ",
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

  /**
   * Wait for a given number of milliseconds
   * 
   * @param milliseconds The amount of time to wait for
   */
  private static void waitFor(long milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (Exception e) {
      print("Error waiting");
    }
  }
  // endregion

  // region Printing
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
  // endregion
}