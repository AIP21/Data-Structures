package utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    // Converts a List object to a string of its contents
    public static String toString(List toConvert) {
        StringBuilder builder = new StringBuilder();

        for (Object e : toConvert) {
            builder.append(e.toString());
        }

        return builder.toString();
    }

    public static String reverseString(String toReverse) {
        StringBuilder builder = new StringBuilder();

        for (int i = toReverse.length() - 1; i >= 0; i--) {
            builder.append(toReverse.charAt(i));
        }

        return builder.toString();
    }

    // Reverses a list of objects
    public static List reverseList(List toReverse) {
        List reversed = new ArrayList();

        for (int i = toReverse.size() - 1; i >= 0; i--) {
            reversed.add(toReverse.get(i));
        }

        return reversed;
    }
}