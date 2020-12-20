package motherlode.base.api.varianttype;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.DataProcessor;

/**
 * JavaDoc planned.
 *
 * @param <T>
 * @param <S>
 */
public abstract class MotherlodeVariantType<T, S extends MotherlodeVariantType<T, S>> extends AbstractExtendableVariantType<T, S> implements AssetProcessor, DataProcessor {
    public MotherlodeVariantType(Identifier id) {
        super(id);
    }

    protected abstract void registerOnClient(Identifier id);

    @Override
    public S register() {
        if (!this.isWithoutBase()) {
            Motherlode.getAssetsManager().addAssets(this.getBaseId(), this);
            Motherlode.getAssetsManager().addData(this.getBaseId(), this);
            Motherlode.registerOnClient(this.getBaseId(), this::registerOnClient);
        }

        return super.register();
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> S extend(S variantType, String namespace) {
        return AbstractExtendableVariantType.extend(variantType, namespace);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> S extend(S variantType, String namespace, Function<S, S> extensionsFunction) {
        return AbstractExtendableVariantType.extend(variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(BooleanSupplier condition, S variantType, String namespace, Function<S, S> extensionsFunction) {
        return AbstractExtendableVariantType.conditionallyExtend(condition, variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(boolean condition, S variantType, String namespace, Function<S, S> extensionsFunction) {
        return AbstractExtendableVariantType.conditionallyExtend(condition, variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(BooleanSupplier condition, Supplier<S> variantType, String namespace, Function<S, S> extensionsFunction) {
        return AbstractExtendableVariantType.conditionallyExtend(condition, variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(boolean condition, Supplier<S> variantType, String namespace, Function<S, S> extensionsFunction) {
        return AbstractExtendableVariantType.conditionallyExtend(condition, variantType, namespace, extensionsFunction);
    }

    public interface Extension<T> extends ExtendableVariantType.Extension<T>, AssetProcessor, DataProcessor {
        @Override
        default void register(Identifier id) {
            ExtendableVariantType.Extension.super.register(id);

            Motherlode.getAssetsManager().addAssets(id, this);
            Motherlode.getAssetsManager().addData(id, this);
            Motherlode.registerOnClient(id, this::registerOnClient);
        }

        void registerOnClient(Identifier id);
    }
}
