package motherlode.base.api.varianttype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.util.Identifier;

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

    private final List<ExtensionEntry<T, S, Extension<T, S>>> extensions;

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
        return new Identifier(this.baseNamespace, this.name);
    }

    @Override
    public S register() {
        if (!withoutBase) {
            this.registerBase(new Identifier(this.baseNamespace, this.name));
            this.withoutBase();
        }

        this.extensions.stream()
            .filter(extension -> extension.getExtension() != null)
            .filter(extension -> !extension.isRegistered())
            .forEach(extension -> extension.getExtension().register(new Identifier(extension.getNamespace(), this.name), getThis()));

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
    public S with(Extension<T, S> extension) {
        this.extensions.add(new ExtensionEntry<>(this.currentNamespace, extension));
        return getThis();
    }

    @Override
    public S conditionallyWith(boolean condition, Supplier<Extension<T, S>> extension) {
        if (condition)
            this.extensions.add(new ExtensionEntry<>(this.currentNamespace, extension.get()));
        return getThis();
    }

    @Override
    public S conditionallyWith(BooleanSupplier condition, Supplier<Extension<T, S>> extension) {
        if (condition.getAsBoolean())
            this.extensions.add(new ExtensionEntry<>(this.currentNamespace, extension.get()));
        return getThis();
    }

    public S extend(Extension<T, S> extension, String namespace) {
        return this.withNamespace(namespace).with(extension);
    }

    @Override
    public T[] variants() {
        T[] baseVariants = this.baseVariants();
        List<T> variants = new ArrayList<>(Arrays.asList(baseVariants));

        for (ExtensionEntry<T, S, Extension<T, S>> extension : this.extensions) {
            variants.addAll(Arrays.asList(extension.getExtension().variants()));
        }
        return variants.toArray(baseVariants);
    }

    @Deprecated
    public static <T, S extends AbstractExtendableVariantType<T, S>> S extend(S variantType, String namespace) {
        return variantType.withNamespace(namespace);
    }

    @Deprecated
    public static <T, S extends AbstractExtendableVariantType<T, S>, E extends Extension<T, S>> E extend(S variantType, String namespace, E extension) {
        variantType.withNamespace(namespace).with(extension).register();
        return extension;
    }

    @Deprecated
    public static <T, S extends AbstractExtendableVariantType<T, S>, E extends Extension<T, S>> Optional<E> conditionallyExtend(BooleanSupplier condition, S variantType, String namespace, Supplier<E> extensionFunction) {
        return conditionallyExtend(condition.getAsBoolean(), variantType, namespace, extensionFunction);
    }

    @Deprecated
    public static <T, S extends AbstractExtendableVariantType<T, S>, E extends Extension<T, S>> Optional<E> conditionallyExtend(boolean condition, S variantType, String namespace, Supplier<E> extensionFunction) {
        return conditionallyExtend(condition, (Supplier<S>) () -> variantType, namespace, extensionFunction);
    }

    @Deprecated
    public static <T, S extends AbstractExtendableVariantType<T, S>, E extends Extension<T, S>> Optional<E> conditionallyExtend(BooleanSupplier condition, Supplier<S> variantType, String namespace, Supplier<E> extensionFunction) {
        return conditionallyExtend(condition.getAsBoolean(), variantType, namespace, extensionFunction);
    }

    @Deprecated
    public static <T, S extends AbstractExtendableVariantType<T, S>, E extends Extension<T, S>> Optional<E> conditionallyExtend(boolean condition, Supplier<S> variantType, String namespace, Supplier<E> extensionFunction) {
        E extension = condition ? extensionFunction.get() : null;
        (condition ? Optional.of(variantType.get()) : Optional.<S>empty()).map(v -> v.withNamespace(namespace)).map(type -> type.with(extension));
        return condition ? Optional.of(extension) : Optional.empty();
    }

    protected static class ExtensionEntry<T, S extends AbstractExtendableVariantType<T, S>, E extends Extension<T, S>> {
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
