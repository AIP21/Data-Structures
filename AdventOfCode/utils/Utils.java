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
    /**
     * returns the int value of string that is a word digit (one, two, three, etc.)
     * ignores extra characters after a number, so you can input "onedbfgj" and it
     * will return 1
     * 
     * Works with numbers from one to nine
     * 
     * @param in The input string
     * @return The determined digit or -1 if nothing was found
     */
    public static int getDigitFromWord(String in) {
        if (in.substring(0, Math.min(in.length(), 3)).equals("one")) {
            return 1;
        } else if (in.substring(0, Math.min(in.length(), 3)).equals("two")) {
            return 2;
        } else if (in.substring(0, Math.min(in.length(), 5)).equals("three")) {
            return 3;
        } else if (in.substring(0, Math.min(in.length(), 4)).equals("four")) {
            return 4;
        } else if (in.substring(0, Math.min(in.length(), 4)).equals("five")) {
            return 5;
        } else if (in.substring(0, Math.min(in.length(), 3)).equals("six")) {
            return 6;
        } else if (in.substring(0, Math.min(in.length(), 3)).equals("seven")) {
            return 7;
        } else if (in.substring(0, Math.min(in.length(), 5)).equals("eight")) {
            return 8;
        } else if (in.substring(0, Math.min(in.length(), 4)).equals("nine")) {
            return 9;
        } else {
            return -1;
        }

        // else if (in.substring(0, Math.min(in.length(), 4)).equals("zero")) {
        // return 0;
        // }
    }

    // returns true if a character is a digit
    public static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

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

    public record Range(long start, long length) implements Comparable<Range> {
        public Range {
            if (length <= 0) {
                throw new IllegalArgumentException();
            }
        }

        public static Range from(long l) {
            return new Range(l, 1);
        }

        public static Range fromTo(long from, long to) {
            return new Range(from, to - from);
        }

        public long length() {
            return length;
        }

        public long start() {
            return start;
        }

        public long end() {
            return start + length;
        }

        public boolean intersects(Range other) {
            return start() < other.end() && other.start() < end();
        }

        public Range intersection(Range other) {
            long left = Long.max(start(), other.start());
            long right = Long.min(end(), other.end());
            return new Range(left, right - left);
        }

        @Override
        public int compareTo(Range o) {
            return Long.compare(start, o.start);
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end() + "]";
        }
    }
}