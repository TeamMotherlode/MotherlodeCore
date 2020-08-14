package motherlode.base.api;

import net.minecraft.util.Identifier;

@FunctionalInterface
public interface Registerable<T> {

    T register(Identifier identifier);
}
