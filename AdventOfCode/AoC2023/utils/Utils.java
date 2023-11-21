package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    // Converts an array of integers to a string of its contents
    public static String toString(int[] toConvert) {
        StringBuilder builder = new StringBuilder();

        for (Object e : toConvert) {
            builder.append(e.toString() + ", ");
        }

        return builder.toString();
    }

    // Converts an array of objects to a string of its contents
    public static String toString(Object[] toConvert) {
        StringBuilder builder = new StringBuilder();

        for (Object e : toConvert) {
            builder.append(e.toString() + ", ");
        }

        return builder.toString();
    }

    // Converts a collection to a string of its contents
    public static String toString(Collection toConvert) {
        StringBuilder builder = new StringBuilder();

        for (Object e : toConvert) {
            builder.append(e.toString() + ", ");
        }

        return builder.toString();
    }

    public static int indexFromCoord(int x, int y, int width) {
        return y * width + x;
    }

    public static class ListComparator<T extends Comparable<T>> implements Comparator<List<T>> {
        @Override
        public int compare(List<T> o1, List<T> o2) {
            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
                int c = o1.get(i).compareTo(o2.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(o1.size(), o2.size());
        }

    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static String bytesToHex(byte[] bytes, int limit) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < limit; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}