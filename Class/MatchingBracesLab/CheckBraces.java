import java.io.*;

/**
 * This program reads a text file (specified in a
 * command-line argument) into a string named inString
 * and checks the braces in that file.
 *
 * To execute, assuming an input file is named 'filename'
 * and exists in the same directory as CheckBraces.class, type
 *
 * java CheckBraces filename
 *
 */
public class CheckBraces {

  /**
   * Reads a file into a String and returns that String.
   * 
   * @param fileName The name of the file to read.
   * @return The contents of the file as a String.
   * @throws IOException If there is an error reading the file.
   */
  public static String readFile(String fileName) throws IOException {
    File f = new File(fileName);
    InputStreamReader inStream = new InputStreamReader(new FileInputStream(f));
    int length = (int) f.length();
    char input[] = new char[length];
    inStream.read(input);
    return (new String(input));
  }

  /**
   * Prints out a String character by character.
   * 
   * @param inString The String to print out.
   */
  public static void print(String inString) {
    for (int k = 0; k < inString.length(); k++)
      System.out.print(inString.charAt(k));
    System.out.println();
  }

  /**
   * Reads a file into a String and checks the braces in that file.
   * 
   * @param args The command-line arguments, which should be
   *             the name of the file to read.
   */
  public static void main(String args[]) {
    String inString = null;

    // if no input file named, then prints warning and ends
    if (args.length < 1) {
      System.out.println("Usage: java CheckBraces sourcefile");
      return;
    }

    // Read the file provided as the command-line argument
    try {
      inString = readFile(args[0]);
    } catch (FileNotFoundException e) {
      // file not present in same directory
      System.err.println("Error: File " + args[0] + " not found");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Error: I/O exception");
      e.printStackTrace();
    }

    // print out the file for viewing purposes
    print(inString);

    // Check braces
    checkBraces(inString);
  }

  /**
   * Checks for errors in the brace matching in a string.
   * Prints the input string
   * and either the first error encountered in the string
   * or "This file contains properly balanced braces."
   * if there are no errors in the input string.
   * 
   * @param inputFile The input string to check the braces for.
   */
  public static void checkBraces(String inputFile) {
    LinkedStack<Character> openingBraces = new LinkedStack<>();

    boolean error = false;

    int i = 0;

    // Go through every character in the input
    for (i = 0; i < inputFile.length(); i++) {
      // Get the character in the string
      char ch = inputFile.charAt(i);

      // If there was already an error, break out of the loop
      if (error) {
        break;
      }

      // Check if the character here is an opening brace
      if (ch == '(' || ch == '[' || ch == '{') {
        openingBraces.push(ch);
        continue;
      }

      // Otherwise, check if the character here is a closing brace
      // and compare the last opening bracket to this closing bracket
      if (ch == ')' || ch == ']' || ch == '}') {
        // Pop the top element from the stack
        Character lastOpener = openingBraces.pop();

        // Check if there is no opening brace for this closing brace
        if (lastOpener == null) {
          // Error, no opening brace
          error = true;
          print("Unmatched brace at character " + i + ": No opening brace for " + ch + ".");
          continue;
        }

        // Compare the two braces
        switch (lastOpener) {
          case '(':
            // Check if the opening bracket matches this closing bracket
            if (ch == ')') {
              // Valid match, keep going
              continue;
            } else {
              // Error, unmatched brace
              error = true;
              print("Unmatched brace at character " + i + ": Found " + ch + " expecting ).");
            }

            break;
          case '[':
            // Check if the opening bracket matches this closing bracket
            if (ch == ']') {
              // Valid match, keep going
              continue;
            } else {
              // Error, unmatched brace
              error = true;
              print("Unmatched brace at character " + i + ": Found " + ch + " expecting ].");
            }

            break;
          case '{':
            // Check if the opening bracket matches this closing bracket
            if (ch == '}') {
              // Valid match, keep going
              continue;
            } else {
              // Error, unmatched brace
              error = true;
              print("Unmatched brace at character " + i + ": Found " + ch + " expecting }.");
            }

            break;
        }
      }
    }

    if (!error) {
      // Once we've gone through the entire input
      // and there are no more opening braces,
      // then this is a valid file.
      if (openingBraces.isEmpty()) {
        // Valid file
        print("This file contains properly balanced braces.");
      } else {
        // There are unclosed braces
        print("Unmatched braces found at character " + i + ".");
      }
    }
  }
}