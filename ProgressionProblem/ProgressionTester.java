package ProgressionProblem;

import utils.CE;

/**
 * Test program for the progression hierarchy.
 */
public class ProgressionTester extends CE {
    public static void main(String[] args) {
        AbstractProgression prog;

        // Test the Arithmetic Progression
        print("Arithmetic progression with the default start value and increment:");
        prog = new ArithmeticProgression();
        prog.printProgression(10);

        print("Arithmetic progression with an increment of 5:");
        prog = new ArithmeticProgression(5);
        prog.printProgression(10);

        print("Arithmetic progression with a start value of 2:");
        prog = new ArithmeticProgression(2, 5);
        prog.printProgression(10);

        // Test the Geometric Progression
        print("Geometric progression with the default start value and base:");
        prog = new GeometricProgression();
        prog.printProgression(10);

        print("Geometric progression with a base of 3:");
        prog = new GeometricProgression(3);
        prog.printProgression(10);

        // Test the Fibonacci Progression
        print("Fibonacci progression with the default first two values:");
        prog = new FibonacciProgression();
        prog.printProgression(10);

        print("Fibonacci progression with a first value of 4 and a second value of 6:");
        prog = new FibonacciProgression(4, 6);
        prog.printProgression(8);
        prog.printProgression(10);

        // Test the Harmonic Progression
        print("Harmonic progression with the default start values: ");
        prog = new HarmonicProgression();
        prog.printProgression(8);
        print("Harmonic progression with start values of 3 and 5: ");
        prog = new HarmonicProgression(3, 5);
        prog.printProgression(8);

        /*
         * print("Harmonic progression with the default start value and common difference:"
         * );
         * prog = new HarmonicProgression();
         * prog.printProgression(10);
         * 
         * print("Harmonic progression with a start value of 2 and a common difference of 3:"
         * );
         * prog = new HarmonicProgression(2, 3);
         * prog.printProgression(8);
         */
    }
}