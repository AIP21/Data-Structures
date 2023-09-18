package ProgressionProblem;

/**
 * Geometric Progression.
 * 
 * Uses a base and multiplies the current value by the base every time it is
 * advanced.
 */
public class GeometricProgression extends AbstractProgression {
    protected long multiplier;

    /**
     * Creates a Geometric Progression object that starts at 1 with a base of 2.
     */
    public GeometricProgression() {
        this(1, 2);
    }

    /**
     * Creates a Geometric Progression object that starts at 1 with a multiplier of
     * multiplier.
     * 
     * @param multiplier The multiplier value of the progression. The current value
     *                   is multiplied by this every step.
     */
    public GeometricProgression(long multiplier) {
        this(1, multiplier);
    }

    /**
     * Creates a Geometric Progression object that starts at start with a multiplier
     * of multiplier.
     * 
     * @param start      The starting value of the progression.
     * @param multiplier The multiplier value of the progression. The current value
     *                   is multiplied by this every step.
     */
    public GeometricProgression(long start, long multiplier) {
        super(start);
        this.multiplier = multiplier;
    }

    protected void advance() {
        current *= multiplier;
    }
}