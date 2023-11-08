package utils;

import java.util.Objects;

public class Point extends Pair<Integer, Integer> {

    public Point(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }

        final Point other = (Point) obj;
        return Objects.equals(getFirst(), other.getFirst()) && Objects.equals(getSecond(), other.getSecond());
    }
}