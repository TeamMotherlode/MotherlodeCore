package motherlode.base.api.varianttype;

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

    S with(Extension<T, S> extension);

    S conditionallyWith(boolean condition, Supplier<Extension<T, S>> extension);

    S conditionallyWith(BooleanSupplier condition, Supplier<Extension<T, S>> extension);

    interface Extension<T, V extends ExtendableVariantType<T, V>> extends VariantType<T> {
        void registerExtension(Identifier id, V variantType);

        default void register(Identifier id, V variantType) {
            this.registerExtension(id, variantType);
        }
    }
}
