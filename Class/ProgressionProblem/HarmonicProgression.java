package ProgressionProblem;

/**
 * Harmonic Progression.
 * 
 * A Harmonic Progression is determined by taking the reciprocals of the
 * arithmetic progression that does not contain 0.
 * 
 * @author Alexander Irausquin-Petit (September 2023)
 */
public class HarmonicProgression extends ArithmeticProgression {

    /**
     * Creates a Harmonic Progression object whose first value is 1 and whose second
     * denominator value is 2.
     */
    public HarmonicProgression() {
        this(1, 2);
    }

    /**
     * Creates a Harmonic Progression object whose first value is first and whose
     * second denominator value is second.
     * 
     * @param start  The first value of the progression. Must not be zero.
     * @param second The second denominator value of the progression.
     * @throws ArithmeticProgression If the first value is 0.
     */
    public HarmonicProgression(long first, long second) {
        super(first, second - first);

        if (first == 0) {
            throw new ArithmeticException(
                    "The first value of a Harmonic Progression cannot be 0 since you cannot divide by 0.");
        }
    }

    // Override the printProgression() method to make the progression print as 1 /
    // current. This is done because current represents the denominator.
    @Override
    public void printProgression(int n) {
        if (n < 1) {
            throw new IndexOutOfBoundsException(
                    "Trying to print the progression with an n parameter less than 1. This is not allowed.");
        }

        inprint("1/" + nextValue());

        for (int j = 1; j < n; j++) {
            inprint(" 1/" + nextValue());
        }

        print("");
    }
}