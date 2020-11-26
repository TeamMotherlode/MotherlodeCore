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
public interface ExtendableVariantType<T, S extends ExtendableVariantType<T, S>> extends VariantType<T> {
    S register();

    S withoutBase();

    S with(Extension<T> extension);

    S conditionallyWith(boolean condition, Supplier<Extension<T>> extension);

    S conditionallyWith(BooleanSupplier condition, Supplier<Extension<T>> extension);

    interface Extension<T> extends RegisterableVariantType<T> {
        void registerExtension(Identifier id);
        
        @Override
        default void register(Identifier id) {
            this.registerExtension(id);
        }
    }
}
