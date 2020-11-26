package motherlode.base.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;

/**
 * JavaDoc planned.
 *
 * @param <T>
 * @param <S>
 */
public abstract class AbstractExtendableVariantType<T, S extends AbstractExtendableVariantType<T, S>> implements ExtendableVariantType<T, S> {
    private boolean withoutBase;

    private final String baseNamespace;
    private String currentNamespace;
    private final String name;

    private final List<ExtensionEntry<T, Extension<T>>> extensions;

    public AbstractExtendableVariantType(Identifier id) {
        this.withoutBase = false;

        this.baseNamespace = id.getNamespace();
        this.currentNamespace = id.getNamespace();
        this.name = id.getPath();

        this.extensions = new ArrayList<>();
    }

    protected abstract S getThis();

    protected abstract void registerBase(Identifier id);

    protected abstract T[] baseVariants();

    protected S withNamespace(String namespace) {
        this.currentNamespace = namespace;
        return getThis();
    }

    protected boolean isWithoutBase() {
        return this.withoutBase;
    }

    protected String getCurrentNamespace() {
        return this.currentNamespace;
    }

    protected Identifier getBaseId() {
        return Motherlode.id(this.baseNamespace, this.name);
    }

    @Override
    public S register() {
        if (!withoutBase) {
            this.registerBase(Motherlode.id(this.baseNamespace, this.name));
            this.withoutBase();
        }

        this.extensions.stream()
            .filter(extension -> extension.getExtension() != null)
            .filter(extension -> !extension.isRegistered())
            .forEach(extension -> extension.getExtension().register(Motherlode.id(extension.getNamespace(), this.name)));

        for (int i = 0; i < this.extensions.size(); i++) {
            extensions.set(i, new ExtensionEntry<>(true, this.extensions.get(i).getNamespace(), this.extensions.get(i).getExtension()));
        }

        return getThis();
    }

    @Override
    public S withoutBase() {
        this.withoutBase = true;
        return getThis();
    }

    @Override
    public S with(Extension<T> extension) {
        this.extensions.add(new ExtensionEntry<>(this.currentNamespace, extension));
        return getThis();
    }

    @Override
    public S conditionallyWith(boolean condition, Supplier<Extension<T>> extension) {
        if (condition)
            this.extensions.add(new ExtensionEntry<>(this.currentNamespace, extension.get()));
        return getThis();
    }

    @Override
    public S conditionallyWith(BooleanSupplier condition, Supplier<Extension<T>> extension) {
        if (condition.getAsBoolean())
            this.extensions.add(new ExtensionEntry<>(this.currentNamespace, extension.get()));
        return getThis();
    }

    @Override
    public T[] variants() {
        T[] baseVariants = this.baseVariants();
        List<T> variants = new ArrayList<>(Arrays.asList(baseVariants));

        for (ExtensionEntry<T, Extension<T>> extension : this.extensions) {
            variants.addAll(Arrays.asList(extension.getExtension().variants()));
        }
        return variants.toArray(baseVariants);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> S extend(S variantType, String namespace) {
        return variantType.withNamespace(namespace);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> S extend(S variantType, String namespace, Function<S, S> extensionsFunction) {
        return extensionsFunction.apply(variantType.withNamespace(namespace));
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(BooleanSupplier condition, S variantType, String namespace, Function<S, S> extensionsFunction) {
        return conditionallyExtend(condition.getAsBoolean(), variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(boolean condition, S variantType, String namespace, Function<S, S> extensionsFunction) {
        return conditionallyExtend(condition, (Supplier<S>) () -> variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(BooleanSupplier condition, Supplier<S> variantType, String namespace, Function<S, S> extensionsFunction) {
        return conditionallyExtend(condition.getAsBoolean(), variantType, namespace, extensionsFunction);
    }

    public static <T, S extends AbstractExtendableVariantType<T, S>> Optional<S> conditionallyExtend(boolean condition, Supplier<S> variantType, String namespace, Function<S, S> extensionsFunction) {
        return (condition ? Optional.of(variantType.get()) : Optional.<S>empty()).map(v -> v.withNamespace(namespace)).map(extensionsFunction);
    }

    protected static class ExtensionEntry<T, E extends ExtendableVariantType.Extension<T>> {
        private final boolean registered;
        private final String namespace;
        private final E extension;

        public ExtensionEntry(String namespace, E extension) {
            this(false, namespace, extension);
        }

        public ExtensionEntry(boolean registered, String namespace, E extension) {
            this.registered = registered;
            this.namespace = namespace;
            this.extension = extension;
        }

        public boolean isRegistered() {
            return this.registered;
        }

        public String getNamespace() {
            return this.namespace;
        }

        public E getExtension() {
            return this.extension;
        }
    }
}
