// REPLACE with an opening Javadoc comment describing the program

// ArrayList will be our underlying data structure, 
// essentially used as a stack ADT
import java.util.ArrayList;
import java.util.Scanner;
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
      ANSI_RED,
      ANSI_GREEN,
      ANSI_YELLOW,
      ANSI_BLUE,
      ANSI_PURPLE,
      ANSI_CYAN,
      ANSI_BLACK,
      ANSI_WHITE,
  };

  // The total number of discs
  private static int totalDiscs;

  private static boolean printInfo = false;

  // Title animation variables
  private static final int TITLE_ANIM_LINE_SPEED = 5; // ms
  private static int TITLE_ANIM_TEXT_SPEED = 10; // ms

  // Animation variables
  private static final int DISC_ANIM_SPEED = 50; // ms
  private static final int DISC_ANIM_FRAMES = 10;

  // User input
  private static Scanner scan = new Scanner(System.in);

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
      gameState.get(from).add(i - 1);
    }
    // endregion

    // Display initial game state
    // showHanoiPegs();
    print("Initial State");
    visualizeHanoiPegs(-1, -1, -1, -1);

    // Wait for input
    waitForInput();

    // Make the first recursive call
    moveTower(numDiscs - 1, from, to, via, 1);

    // Report the result!
    print("Finished! Game completed in " + count + " moves.");
  }

  public static void showHanoiPegs() {
    for (int i = 0; i < 3; i++) {
      System.out.print("peg " + HANOI_PEGS.substring(i, i + 1) + ": ");
      System.out.println(gameState.get(i));
    }
    System.out.println();
  }

  /**
   * Visualize the game state and animate the disc that was just moved.
   * 
   * @param discBeingMoved The disc being animated. Should be -1 if there is no
   *                       animation.
   * @param lineFrom       The line the animated disc was originally on.
   * @param pegFrom        The peg the animated disc was originally on.
   * @param pegTo          The peg the animated disc was moved to.
   */
  public static void visualizeHanoiPegs(int discBeingMoved, int lineFrom, int pegFrom, int pegTo) {
    // If the disc being moved is -1, then we are not animating.
    // This is a quick hack I made to reduce the number of parameters
    // passed into the method.
    boolean doDiscAnimation = discBeingMoved != -1;

    boolean discAnimating = true;

    int animFrames = DISC_ANIM_FRAMES;

    // If not animating, then just set the animation length to 0
    // This is done to "animate" one frame, which is equivelant of just printing it
    // out
    if (!doDiscAnimation) {
      animFrames = 0;
    }

    int frame = 0;

    while (discAnimating) {
      // Clear terminal before drawing next frame
      if ((!doDiscAnimation && frame != 0) || (doDiscAnimation && frame != animFrames)) {
        clearTerminal();
      }

      // Calcualte the progress of the animation as a decimal
      float animationProgress = (float) frame / (float) animFrames;

      // region Prepare for printing
      // Add empty spacing above
      print("");

      // Print the move number
      if (count != 0) {
        print("\nMove " + count);
      }

      // Create a list of strings to store the image being drawn
      // The number of lines is the height of each peg
      // (number of discs + 4 for the top peg label and separator line,
      // top extra spacing and peg base layer)
      String[] lines = new String[totalDiscs + 4];

      // Populate the lines array
      for (int i = 0; i < lines.length; i++) {
        lines[i] = "";
      }
      // endregion

      // region Regular printing
      // Figure out the spacing between discs
      int whitespace = 5;
      int pegWidth = totalDiscs * 2 + 2; // Maximum width of a peg base

      // region Draw all the pegs
      // Go through every peg
      for (int peg = 0; peg < gameState.size(); peg++) {
        // Determine what this peg's whitespace should be
        // Should be one less for the first peg
        int pegWhitespace = peg == 0 ? whitespace - 1 : whitespace;

        // region Top part (peg labels and separator line)
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

        // region Draw the stem of the peg
        // Go through every line of the peg stem
        for (int l = lines.length - 2; l >= 2; l--) {
          // Draw the whitespace before the stem
          for (int e = 0; e < pegWhitespace + totalDiscs; e++) {
            lines[l] += " ";
          }

          // Draw the stem
          lines[l] += "║";

          // Draw the whitespace after the stem
          for (int e = 0; e < totalDiscs + 1; e++) {
            lines[l] += " ";
          }
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
      // endregion

      // region Draw the discs
      int animatedLine = -1;

      // Go through every line
      for (int l = lines.length - 2; l >= 2; l--) {
        int offset = 0;

        // Create a string builder for overwriting the line string
        StringBuilder lineBuilder = new StringBuilder(lines[l]);

        // Go through every peg
        for (int peg = 0; peg < gameState.size(); peg++) {
          // Calculate the disc number
          int disc = getDiscAt(peg, lines.length - l - 2);

          // If the disc number is -1 (doesn't exist)
          // or the disc is being animated,
          // then the disc does not exist
          if (disc == -1 || (doDiscAnimation && disc == discBeingMoved)) {
            if (doDiscAnimation) {
              animatedLine = l;
            }

            continue;
          }

          // Calculate the center of the peg (and so the center of the disc)
          int discCenter = ordinalIndexOf(lines[l], "║", peg + 1) + offset;

          // Calculate left and right disc draw string indices
          int discLeft = discCenter - (disc + 1);
          int discRight = discCenter + disc + 1;

          // Draw the disc over the current string
          for (int e = discLeft; e <= discRight; e++) {
            lineBuilder.setCharAt(e, '█');
          }

          // Set the center character to the disc number,
          // to remain consistent with the rest of the discs
          lineBuilder.setCharAt(discCenter, '║');

          // Set the draw color to the disc color
          lineBuilder.insert(discLeft, getDiscColor(disc));

          // Reset the line color
          lineBuilder.insert(discRight + 6, ANSI_RESET);

          // Increase the offset for the next disc
          offset += 9; // getDiscColor(disc).length() + ANSI_RESET.length();
        }

        // Replace the line with the overwritten string
        lines[l] = lineBuilder.toString();
      }
      // endregion
      // endregion

      // region Draw the animated disc
      // If we are animating
      if (doDiscAnimation) {
        // Go through every line again,
        // this time to animate the disc movement
        // Determine what line the disc was moved to
        int newLine = (lines.length - 2) - gameState.get(pegTo).indexOf(discBeingMoved);

        // Calculate the interpolated line to drawn on
        // Between the disc's original line and the line where the disc was moved to
        animatedLine = interpolate(lineFrom + 1, newLine, animationProgress);

        // Calculate the interpolated peg centers
        int centerFrom = ordinalIndexOf(lines[animatedLine], "║", pegFrom + 1);
        int centerTo = ordinalIndexOf(lines[animatedLine], "║", pegTo + 1);

        // Calculate the interpolated current index of the disc center
        int discCenter = interpolate(centerFrom, centerTo, animationProgress);

        // Calculate left and right disc draw string indices
        int discLeft = discCenter - (discBeingMoved + 1);
        int discRight = discCenter + discBeingMoved + 1;

        // print("progress: " + animationProgress + ", pegFrom: " + pegFrom + ", pegTo:
        // " + pegTo + ", from: "
        // + centerFrom + ", to: " + centerTo + ", center: " + discCenter
        // + ", lineFrom: " + lineFrom
        // + ", lineTo: " + newLine
        // + ", animLine: " + animatedLine);

        // Create a string builder for overwriting the line string
        StringBuilder animatedDiscDrawString = new StringBuilder(lines[animatedLine]);

        // Draw the disc over the current string
        for (int e = discLeft; e <= discRight; e++) {
          animatedDiscDrawString.setCharAt(e, '█');
        }

        // Set the center character to the disc number,
        // to remain consistent with the rest of the discs
        animatedDiscDrawString.setCharAt(discCenter, (char) (discBeingMoved + '0'));

        // Set the draw color to the disc color
        animatedDiscDrawString.insert(discLeft, getDiscColor(discBeingMoved));

        // Reset the line color
        animatedDiscDrawString.insert(discRight + 6, ANSI_RESET);

        // Replace the animated line with the overwritten string
        // If the disc draw string is null, then the disc does not exist
        if (animatedDiscDrawString != null) {
          lines[animatedLine] = animatedDiscDrawString.toString();
        }
      }
      // endregion

      // region Disc labeling
      // Our last step is to simply insert the disc's number
      // at its center.
      // This is done to make it easier to see which disc is which.
      // This is not done when drawing the actual discs because
      // the animation requires a unique character to find
      // the centers of pegs.

      // Go through every line
      for (int l = lines.length - 2; l >= 2; l--) {
        // Create a string builder for overwriting the line string
        StringBuilder lineBuilder = new StringBuilder(lines[l]);

        // Go through every peg
        for (int peg = 0; peg < gameState.size(); peg++) {
          // Calculate the disc number
          int disc = getDiscAt(peg, lines.length - l - 2);

          // Make sure there is a disc here
          if (disc == -1 || (doDiscAnimation && disc == discBeingMoved && animationProgress != 1.0)) {
            continue;
          }

          // Calculate the center of the peg (and so the center of the disc)
          int discCenter = ordinalIndexOf(lines[l], "║", peg + 1);

          // Set the center character to its number
          // only if the calculated center index is not -1
          if (discCenter != -1) {
            lineBuilder.setCharAt(discCenter, (char) (disc + '0'));
          }
        }

        // Replace the line with the overwritten string
        lines[l] = lineBuilder.toString();
      }
      // endregion

      // Draw every line
      for (String line : lines) {
        print(line);
      }

      // Wait for a small time delay
      // (otherwise the animation would be too fast)
      waitFor(DISC_ANIM_SPEED);

      // Clear terminal before drawing next frame
      if ((!doDiscAnimation && frame != 0) || (doDiscAnimation && frame != animFrames)) {
        clearTerminal();
      }

      // End case for the animation
      if (frame >= animFrames) {
        discAnimating = false;
      }
      // If not done, increment frame counter
      else {
        frame++;
      }
    }
  }

  // Classic Towers of Hanoi recursive solution
  public static void moveTower(int discNum, int from, int to, int via, int depth) {
    // Base case: can ALWAYS move the smallest disc
    if (discNum == 0) {
      // Set the line that the disc will be animated from
      int discAnimLineFrom = (totalDiscs - 2) - gameState.get(to).indexOf(discNum);

      // Move from peg "from" to peg "to" if it is legal
      if (canMove(0, getTop(to))) {
        // Remove the top of peg "from"
        removeTop(from);

        // Add it to peg "to"
        addToTop(to, 0);
      }

      // Increment count
      count++;

      info("End of layer " + depth + ". Moved disc 0 directly from " + HANOI_PEGS.charAt(from) + " to "
          + HANOI_PEGS.charAt(to) + ". Count is now " + count + ". Returning to next step of layer " + (depth - 1)
          + ".");

      // Display the game state
      visualizeHanoiPegs(discNum, discAnimLineFrom, from, to);

      // Wait for input
      waitForInput();
    } else {
      info("Step 1 of layer " + depth + ". Moving tower above disc " + discNum + " from " + HANOI_PEGS.charAt(from)
          + " to "
          + HANOI_PEGS.charAt(via) + " via " + HANOI_PEGS.charAt(to) + ". Cannot move yet, entering layer "
          + (depth + 1) + ".");

      // Move the tower on top of discNum to the spare peg
      moveTower(discNum - 1, from, via, to, depth + 1);

      infoin(
          "Step 2 of layer " + depth + ". Moving disc " + discNum + " directly from " + HANOI_PEGS.charAt(from) + " to "
              + HANOI_PEGS.charAt(to));

      // Set the line that the disc will be animated from
      int discAnimLineFrom = (totalDiscs - 2) - gameState.get(to).indexOf(discNum);

      // Move from peg "from" to peg "to" if it is legal
      if (canMove(discNum, getTop(to))) {
        // Remove the top of peg "from"
        removeTop(from);

        // Add it to peg "to"
        addToTop(to, discNum);
      }

      // Increment count
      count++;

      info(". Count is now " + count + ".");

      // Display the game state
      visualizeHanoiPegs(discNum, discAnimLineFrom, from, to);

      info("Step 3 of layer " + depth + ". Moving tower that used to be above disc " + discNum + " (Disc "
          + (discNum - 1)
          + ") from " + HANOI_PEGS.charAt(via) + " to " + HANOI_PEGS.charAt(to) + " via " + HANOI_PEGS.charAt(from)
          + ". Cannot finish layer yet, entering layer " + (depth + 1) + ".");

      // Wait for input
      waitForInput();

      // Then move the tower from spare peg to target peg
      moveTower(discNum - 1, via, to, from, depth + 1);

      info("End of layer " + depth + ". Returning to next step of layer " + (depth - 1) + ".");
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
    return (int) ((float) a * (1.0f - f) + ((float) b * f));
  }

  /**
   * Waits for the user to press enter before continuing to next move.
   * 
   */
  private static void waitForInput() {
    print("Press enter to continue");
    scan.nextLine();
  }

  /**
   * Get the index of the nth occurrence of a substring in a string
   * 
   * @param str    The string to search
   * @param substr The substring to search for
   * @param n      The number of the occurrence to find
   * @return The index of the nth occurrence of the substring in the string, or -1
   */
  private static int ordinalIndexOf(String str, String substr, int n) {
    int pos = str.indexOf(substr);

    while (--n > 0 && pos != -1) {
      pos = str.indexOf(substr, pos + 1);
    }

    return pos;
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

  /**
   * Removes and returns the topmost disc from a given peg.
   * 
   * @param peg The peg to remove the disc from. Starts at 0.
   * @return The topmost disc at that peg.
   */
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

  /**
   * Adds a disc to the top of a given peg.
   * 
   * @param peg   The peg to add the disc to.
   * @param toAdd The disc to add
   */
  private static void addToTop(int peg, Integer toAdd) {
    ArrayList<Integer> pegList = gameState.get(peg);

    pegList.add(pegList.size(), toAdd);
  }

  /**
   * Determines if you can move a disc above another disc.
   * 
   * @param a The first disc.
   * @param b The second disc.
   * @return Whether or not this move is legal.
   */
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
  // Animate the title in
  private static void animateTitleIn() {
    boolean animating = true;

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
      for (int i = 0; i < lineHalfLength - 1; i++) {
        line += "=";
      }

      print(line);
      print("");
      print("");
      print("");
      print("");
      print(line);

      waitFor(TITLE_ANIM_LINE_SPEED);

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

      waitFor(TITLE_ANIM_TEXT_SPEED);

      // Increment the title line half length
      titleLineHalfLength++;

      if (titleLineHalfLength * 2 >= titleLength) {
        animating = false;
      }
    }

    print("");
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
  /**
   * CLear the terminal. Used for animations.
   */
  private static void clearTerminal() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /**
   * Print a string without a new line.
   * 
   * @param s The string to print.
   */
  private static void inprint(String s) {
    System.out.print(s);
  }

  /**
   * Print a string with a new line.
   * 
   * @param s The string to print.
   */
  private static void print(String s) {
    System.out.println(s);
  }

  /**
   * Print an informational string with a new line.
   * Only prints if the information printing boolean is true.
   * 
   * @param s The string to print.
   */
  private static void info(String s) {
    if (printInfo) {
      System.out.println(s);
    }
  }

  /**
   * Print an informational string without a new line.
   * Only prints if the information printing boolean is true.
   * 
   * @param s The string to print.
   */
  private static void infoin(String s) {
    if (printInfo) {
      System.out.print(s);
    }
  }
  // endregion
}