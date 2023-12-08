
/**
  * This program reads a text file (specified in a
  * command-line argument) into a string named inString
  * and converts the contained expressions as directed below
  *
  * To execute, assuming an input file is named 'filename'
  * and exists in the same directory as PreInPost.class, type
  *
  *   java PreInPost filename
  *
  */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PreInPost {

  // Reads a file into a String and returns that String.
  public static String readFile(String fileName) throws IOException {
    File f = new File(fileName);
    InputStreamReader inStream = new InputStreamReader(new FileInputStream(f));
    int length = (int) f.length();
    char input[] = new char[length];
    inStream.read(input);
    return (new String(input));
  }

  // Prints out a String character by character.
  public static void print(String inString) {
    for (int k = 0; k < inString.length(); k++)
      System.out.print(inString.charAt(k));
    System.out.println();
  }

  public static void main(String args[]) {
    String inString = null;

    // if no input file named, then prints warning and ends
    if (args.length < 1) {
      System.out.println("Usage: java PreInPost sourcefile");
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
    System.out.println("The file contents:");
    print(inString);

    String[] expressions = inString.split("\n");

    System.out.println("The original InFix expressions:");
    for (String e : expressions) {
      System.out.println(e.substring(4));
    }
    System.out.println();

    System.out.println("The evaluations");
    for (String e : expressions) {
      String postFix = inToPostFix(e.substring(4));
      String preFix = inToPreFix(e.substring(4));
      if (e.indexOf("val") == 0) {
        System.out.println("PostFix: " + postFix + " = " + evalPost(postFix));
        System.out.println("PreFix:  " + preFix + " = " + evalPre(preFix));
      } else {
        System.out.println("PostFix: " + postFix);
        System.out.println("PreFix:  " + preFix);
      }
      System.out.println();
    }
  }

  /**
   * Returns the precedence of the given operator.
   * Based on the rules of PEMDAS.
   * 
   * @param operator The operator
   * @return The precedence of the given operator
   */
  private static int precedence(String operator) {
    if (operator.equals("+") || operator.equals("-")) {
      return 1;
    } else if (operator.equals("*") || operator.equals("/")) {
      return 2;
    } else if (operator.equals("^")) {
      return 3;
    } else {
      return 0;
    }
  }

  /**
   * Returns true if the given input is an operator.
   * An operator can be one of: ^, +, -, *, /
   * 
   * @param input The input string.
   * @return True if the given input is an operator, false otherwise.
   */
  private static boolean isOperator(String input) {
    return input.equals("^") || input.equals("+") || input.equals("-") || input.equals("*") || input.equals("/");
  }

  /**
   * Returns the reversed version of the given expression string.
   * Replaces ( with ) and vice versa.
   * 
   * @param input The input expression
   * @return The reversed expression
   */
  private static String reverseExpression(String input) {
    // Convert the input expression string to a list of tokens
    String[] tokens = input.split(" ");

    ArrayList<String> reversedTokens = new ArrayList<>();

    // Convert array to list
    for (String t : tokens) {
      // Fix parentheses
      if (t.equals("(")) {
        t = ")";
      } else if (t.equals(")")) {
        t = "(";
      }

      reversedTokens.add(t);
    }

    // Reverse the input expression token list
    Collections.reverse(reversedTokens);

    // Convert the now-reversed list of tokens back to a string
    String reversed = "";
    for (String c : reversedTokens) {
      reversed += c + " ";
    }

    return reversed;
  }

  /**
   * Perform the integer operation specified by operator on two integer values.
   * 
   * @param operator The string operator. Can be on of: ^, +, -, *, /
   * @param a        The first integer value
   * @param b        The second integer value
   * @return The result of the operation, or -1 if the operator was invalid.
   */
  private static int operate(String operator, int a, int b) {
    // Check each operator condition
    if (operator.equals("^")) {
      return (int) Math.pow(a, b);
    } else if (operator.equals("+")) {
      return a + b;
    } else if (operator.equals("-")) {
      return a - b;
    } else if (operator.equals("*")) {
      return a * b;
    } else if (operator.equals("/")) {
      return a / b;
    } else {
      // Return -1 if the operator was invalid
      return -1;
    }
  }

  /**
   * Converts an in-fix expression to a post-fix expression.
   * 
   * @param expr The in-fix expression, as a string
   * @return The post-fix expression, as a string
   */
  public static String inToPostFix(String expr) {
    String result = "";
    LinkedStack<String> S = new LinkedStack<String>();

    String[] tokens = expr.split(" ");

    for (String t : tokens) {
      // If t is a left-parenthesis, push it onto S
      if (t.equals("(")) {
        S.push(t);
      }

      // If t is a right-parenthesis, pop S and concatenate to the result
      else if (t.equals(")")) {

        // Add everything inside the parentheses to the result
        while (!S.isEmpty() && !S.top().equals("(")) {
          result += S.pop() + " ";
        }

        // Pop the opening parenthesis off
        S.pop();
      }

      // If t is an operator
      else if (isOperator(t)) {
        // If the stack is empty, is within an open parenthesis, or if t's precedence is
        // greater than the precedence of the operator on top, then push t onto S.
        // This is becuase higher precendence operators should be evaluated first.
        if (S.isEmpty() || S.top().equals("(") || precedence(t) > precedence(S.top())) {
          S.push(t);
        }

        // Otherwise, get operators whose precedence is greater than or equal to t's
        // precedence, pop all of them and add them to the result.
        // Once that is done, push t onto S.
        // Since t is the lowest operator, it should be evaluated last.
        else {
          while (!S.isEmpty() && precedence(t) <= precedence(S.top())) {
            result += S.pop() + " ";
          }

          S.push(t);
        }
      }

      // Otherwise, t must be an operand so just add it to the result
      else {
        result += t + " ";
      }
    }

    // Add the remaining of the operators to the result
    while (!S.isEmpty()) {
      result += S.pop() + " ";
    }

    return result;
  }

  /**
   * Converts an in-fix expression to a pre-fix expression.
   * 
   * @param expr The in-fix expression, as a string
   * @return The pre-fix expression, as a string
   */
  public static String inToPreFix(String expr) {
    // Reverse the input string
    String reversedExpression = reverseExpression(expr);

    // Get the post-fix expression of the reversed input expression
    String postFix = "";
    LinkedStack<String> S = new LinkedStack<String>();

    String[] tokens = reversedExpression.split(" ");

    for (String t : tokens) {
      // If t is a left-parenthesis, push it onto S
      if (t.equals("(")) {
        S.push(t);
      }

      // If t is a right-parenthesis, pop S and concatenate to the result
      else if (t.equals(")")) {

        // Add everything inside the parentheses to the result
        while (!S.isEmpty() && !S.top().equals("(")) {
          postFix += S.pop() + " ";
        }

        // Pop the opening parenthesis off
        S.pop();
      }

      // If t is an operator
      else if (isOperator(t)) {
        // If the stack is empty, is within an open parenthesis, or if t's precedence is
        // greater than the precedence of the operator on top, then push t onto S.
        // This is becuase higher precendence operators should be evaluated first.
        if (S.isEmpty() || S.top().equals("(") || precedence(t) > precedence(S.top())) {
          S.push(t);
        }

        // Otherwise, get operators whose precedence is greater than or equal to t's
        // precedence, pop all of them and add them to the result.
        // Once that is done, push t onto S.
        // Since t is the lowest operator, it should be evaluated last.
        else {
          while (!S.isEmpty() && precedence(t) < precedence(S.top())) {
            postFix += S.pop() + " ";
          }

          S.push(t);
        }
      }

      // Otherwise, t must be an operand so just add it to the result
      else {
        postFix += t + " ";
      }
    }

    // Add the remaining of the operators to the result
    while (!S.isEmpty()) {
      postFix += S.pop() + " ";
    }

    // Reverse the post-fix expression to get the pre-fix expression
    String preFix = reverseExpression(postFix);

    return preFix;
  }

  /**
   * Evaluates a post-fix expression.
   * 
   * @param postExpr The post-fix expression, as a string
   * @return The result of the expression
   */
  public static int evalPost(String postExpr) {
    LinkedStack<Integer> operands = new LinkedStack<>();

    String[] tokens = postExpr.split(" ");

    for (String t : tokens) {
      // Check if this token is an operator
      if (isOperator(t)) {
        int A = operands.pop();
        int B = operands.pop();

        // Perform that operation on the last two numbers in the stack
        int result = operate(t, B, A);

        // Readd that value to the stack
        operands.push(result);
      }

      // This token is not an operator
      else {
        // Push its numeric value onto the stack
        operands.push(Integer.parseInt(t));
      }
    }

    // Return the last value on the stack (the result)
    return operands.pop();
  }

  /**
   * Evaluates a pre-fix expression.
   * 
   * @param preExpr The pre-fix expression, as a string
   * @return The result of the expression
   */
  public static int evalPre(String preExpr) {
    String reversedInput = reverseExpression(preExpr);

    LinkedStack<Integer> operands = new LinkedStack<>();

    String[] tokens = reversedInput.split(" ");

    // Go through every token
    for (String t : tokens) {
      // Check if this token is an operator
      if (isOperator(t)) {
        int A = operands.pop();
        int B = operands.pop();

        // Perform that operation on the last two numbers in the stack
        int result = operate(t, A, B);

        // Readd that value to the stack
        operands.push(result);
      }

      // This token is not an operator
      else {
        // Push its numeric value onto the stack
        operands.push(Integer.parseInt(t));
      }
    }

    // Return the last value on the stack (the result)
    return operands.pop();
  }
}
