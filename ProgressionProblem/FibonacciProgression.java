package ProgressionProblem;

/**
 * Fibonacci Progression.
 * 
 * Adds the previous value to the current value every time the progression is
 * advanced.
 */
public class FibonacciProgression extends AbstractProgression {
    protected long previousValue;

    /**
     * Creates a Fibonacci Progression object whose first value is 0 and whose
     * second value is 1.
     */
    public FibonacciProgression() {
        this(0, 1);
    }

    /**
     * Creates a Fibonacci Progression object whose first value is first and whose
     * second value is second.
     * 
     * @param first  The first value in the progression.
     * @param second The second value in the progression.
     */
    public FibonacciProgression(long first, long second) {
        super(first);
        previousValue = second - first;
    }

    protected void advance() {
        long temp = previousValue;
        previousValue = current;
        current += temp;
    }
}