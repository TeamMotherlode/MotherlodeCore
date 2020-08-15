package motherlode.base.api;

import net.minecraft.util.Identifier;

@FunctionalInterface
public interface Registerable<T> {

    void register(Identifier identifier);
}
