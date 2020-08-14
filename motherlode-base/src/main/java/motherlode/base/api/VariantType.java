package motherlode.base.api;

@FunctionalInterface
public interface VariantType<T> {

    T[] variants();
}
