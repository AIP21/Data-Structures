package utils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F newFirst) {
        this.first = newFirst;
    }

    public void setSecond(S newSecond) {
        this.second = newSecond;
    }

    public Pair<S, F> swap() {
        return of(getSecond(), getFirst());
    }

    @Override
    public String toString() {
        return "(" + getFirst() + ", " + getSecond() + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Pair<?, ?>)) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(getFirst(), other.getFirst()) && Objects.equals(getSecond(), other.getSecond());
    }

    public <F2> Pair<F2, S> mapFirst(final Function<? super F, ? extends F2> function) {
        return of(function.apply(getFirst()), getSecond());
    }

    public <S2> Pair<F, S2> mapSecond(final Function<? super S, ? extends S2> function) {
        return of(getFirst(), function.apply(getSecond()));
    }

    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(first, second);
    }

    public static <F, S> Collector<Pair<F, S>, ?, Map<F, S>> toMap() {
        return Collectors.toMap(Pair::getFirst, Pair::getSecond);
    }
}