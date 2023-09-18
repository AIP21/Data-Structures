package utils;

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
}