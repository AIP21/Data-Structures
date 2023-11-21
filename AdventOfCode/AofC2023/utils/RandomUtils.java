package utils;

import java.util.Random;

/**
 * A helper class to make it easier to use randomness.
 * 
 * @author Alexander Irausquin-Petit (2023)
 */
public class RandomUtils {
    private static final Random rand = new Random();

    public static float randomFloat() {
        return rand.nextFloat();
    }

    public static float randomFloat(float start, float end) {
        return rand.nextFloat(start, end);
    }

    public static int randomInt() {
        return rand.nextInt();
    }

    public static int randomInt(int start, int end) {
        return rand.nextInt(start, end);
    }

    public static double randomDouble() {
        return rand.nextDouble();
    }

    public static double randomDouble(double start, double end) {
        return rand.nextDouble(start, end);
    }

    public static boolean randomBoolean() {
        return rand.nextBoolean();
    }

    public static boolean randomBoolean(float chance) {
        return rand.nextFloat() < chance;
    }
}
