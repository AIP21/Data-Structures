package ProgressionProblem;

import utils.CE;

/**
 * Abstract Progression.
 * 
 * Subclasses inherit this class and create its behavior by implementing the
 * advance method.
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
     * @param n The index to compute the progression up to.
     */
    public void printProgression(int n) {
        inprint(nextValue());

        for (int j = 1; j < n; j++) {
            inprint(" " + nextValue());
        }

        print("");
    }
}