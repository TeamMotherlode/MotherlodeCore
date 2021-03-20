package motherlode.base.util;

import java.util.function.Function;

public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return this.first;
    }

    public B getSecond() {
        return this.second;
    }

    public C getThird() {
        return this.third;
    }

    public <T> Triple<T, B, C> mapFirst(Function<A, T> function) {
        return new Triple<>(function.apply(this.first), this.second, this.third);
    }

    public <T> Triple<A, T, C> mapSecond(Function<B, T> function) {
        return new Triple<>(this.first, function.apply(this.second), this.third);
    }

    public <T> Triple<A, B, T> mapThird(Function<C, T> function) {
        return new Triple<>(this.first, this.second, function.apply(this.third));
    }
}
