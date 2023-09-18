package ProgressionProblem;

import utils.CE;

/**
 * Test program for the progression hierarchy.
 */
public class ProgressionTester extends CE {
    public static void main(String[] args) {
        AbstractProgression prog;

        // Test the Arithmetic Progression
        print("Arithmetic progression with default increment:");
        prog = new ArithmeticProgression();
        prog.printProgression(10);

        print("Arithmetic progression with increment 5:");
        prog = new ArithmeticProgression(5);
        prog.printProgression(10);

        print("Arithmetic progression with start 2:");
        prog = new ArithmeticProgression(2, 5);
        prog.printProgression(10);

        // Test the Geometric Progression
        print("Geometric progression with default base:");
        prog = new GeometricProgression();
        prog.printProgression(10);

        print("Geometric progression with base 3:");
        prog = new GeometricProgression(3);
        prog.printProgression(10);

        // Test the Fibonacci Progression
        print("Fibonacci progression with default start values:");
        prog = new FibonacciProgression();
        prog.printProgression(10);

        print("Fibonacci progression with start values 4 and 6:");
        prog = new FibonacciProgression(4, 6);
        prog.printProgression(8);
    }
}