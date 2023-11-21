package utils;

import java.io.Serializable;
import java.util.Objects;

public abstract class Triple<L, M, R> implements Comparable<Triple<L, M, R>>, Serializable {
    private static final long serialVersionUID = 1L;

    public Triple() {
    }

    public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
        return new ImmutableTriple(left, middle, right);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Triple)) {
            return false;
        } else {
            Triple<?, ?, ?> other = (Triple) obj;
            return Objects.equals(this.getLeft(), other.getLeft())
                    && Objects.equals(this.getMiddle(), other.getMiddle())
                    && Objects.equals(this.getRight(), other.getRight());
        }
    }

    public abstract L getLeft();

    public abstract M getMiddle();

    public abstract R getRight();

    public int hashCode() {
        return Objects.hashCode(this.getLeft()) ^ Objects.hashCode(this.getMiddle())
                ^ Objects.hashCode(this.getRight());
    }

    public String toString() {
        return "(" + this.getLeft() + "," + this.getMiddle() + "," + this.getRight() + ")";
    }

    public String toString(String format) {
        return String.format(format, this.getLeft(), this.getMiddle(), this.getRight());
    }
}
