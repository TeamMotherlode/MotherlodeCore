package motherlode.base;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Motherlode implements ModInitializer, ClientModInitializer {

    public static final String MODID = "motherlode";

    @Override
    public void onInitialize() {

        // TODO
    }
    @Override
    public void onInitializeClient() {

        // TODO
    }
    public static Identifier id(String namespace, String name) { return new Identifier(namespace, name); }
    public static Identifier id(String name) {
        return id(MODID, name);
    }
}