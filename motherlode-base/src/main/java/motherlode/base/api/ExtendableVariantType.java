package motherlode.base.api;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;

/**
 * JavaDoc planned.
 *
 * @param <T>
 * @param <S>
 */
public interface ExtendableVariantType<T, S extends ExtendableVariantType<T, S>> extends RegisterableVariantType<T> {
    S register();

    S withoutBase();

    S with(Extension<T> extension);

    S conditionallyWith(boolean condition, Supplier<Extension<T>> extension);

    S conditionallyWith(BooleanSupplier condition, Supplier<Extension<T>> extension);

    default void register(Identifier id) {
        throw new UnsupportedOperationException("register(Identifier). Call register() instead.");
    }

    interface Extension<T> extends RegisterableVariantType<T> {
    }
}
