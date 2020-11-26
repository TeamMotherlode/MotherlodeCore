package motherlode.base.api;

/**
 * An interface used for more easily creating several variations of things that consist of multiple variants.
 *
 * @param <T>
 */
public interface VariantType<T> {
    /**
     * Returns all variants of this {@code VariantType}.
     * The returned array should not change.
     *
     * @return The variants that this {@code VariantType} consists of.
     */
    T[] variants();
}
