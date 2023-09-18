package ProgressionProblem;

/**
 * Arithmetic Progression.
 * 
 * Uses an increment and increases the current value by the increment every time
 * it is advanced.
 */
public class ArithmeticProgression extends AbstractProgression {
    protected long increment;

    /**
     * Creates an Arithmetic Progression object that starts at 0 and increments by
     * 1.
     */
    public ArithmeticProgression() {
        this(0, 1);
    }

    /**
     * Creates an Arithmetic Progression object that starts at 0 and increments by
     * increment.
     * 
     * @param increment The number to increment the arithmetic progression by.
     */
    public ArithmeticProgression(long increment) {
        this(0, increment);
    }

    /**
     * Creates an Arithmetic Progression object that starts at start and increments
     * by
     * increment.
     * 
     * @param start     The number the arithmetic progression starts at.
     * @param increment The number to increment the arithmetic progression by.
     */
    public ArithmeticProgression(long start, long increment) {
        super(start);
        this.increment = increment;
    }

    protected void advance() {
        current += increment;
    }
}