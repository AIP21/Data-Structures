package utils;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ImmutablePair<F, S> {
    private final F first;
    private final S second;

    public ImmutablePair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public ImmutablePair<S, F> swap() {
        return of(second, first);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ImmutablePair<?, ?>)) {
            return false;
        }
        final ImmutablePair<?, ?> other = (ImmutablePair<?, ?>) obj;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }

    public <F2> ImmutablePair<F2, S> mapFirst(final Function<? super F, ? extends F2> function) {
        return of(function.apply(first), second);
    }

    public <S2> ImmutablePair<F, S2> mapSecond(final Function<? super S, ? extends S2> function) {
        return of(first, function.apply(second));
    }

    public static <F, S> ImmutablePair<F, S> of(final F first, final S second) {
        return new ImmutablePair<>(first, second);
    }

    public static <F, S> Collector<ImmutablePair<F, S>, ?, Map<F, S>> toMap() {
        return Collectors.toMap(ImmutablePair::getFirst, ImmutablePair::getSecond);
    }
}