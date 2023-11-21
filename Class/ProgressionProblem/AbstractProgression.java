package ProgressionProblem;

import utils.CE;

/**
 * Abstract Progression.
 * 
 * Subclasses inherit this class and create its behavior by implementing the
 * advance method.
 * 
 * @author Alexander Irausquin-Petit (September 2023)
 */
public abstract class AbstractProgression extends CE {
    protected long current;

    /**
     * Creates an Abstract Progression object that starts at 0.
     */
    public AbstractProgression() {
        this(0);
    }

    /**
     * Creates an Abstract Progression object that starts at start.
     * 
     * @param start The value that the progression starts at.
     */
    public AbstractProgression(long start) {
        current = start;
    }

    /**
     * Returns the next value in the progression.
     * 
     * @return The next value in the progression.
     */
    public long nextValue() {
        long answer = current;
        advance();
        return answer;
    }

    /**
     * Computes the next value in the progression. This is implemented by
     * subclasses.
     */
    protected abstract void advance();

    /**
     * Computes the progression up to index n and prints it out.
     * 
     * @param n The index to compute the progression up to. Must be greater than or
     *          equal to 1.
     * @throws IndexOutOfBoundsException If the n parameter is less than 1.
     */
    public void printProgression(int n) {
        if (n < 1) {
            throw new IndexOutOfBoundsException(
                    "Trying to print the progression with an n parameter less than 1. This is not allowed.");
        }

        inprint(nextValue());

        for (int j = 1; j < n; j++) {
            inprint(" " + nextValue());
        }

        print("");
    }
}