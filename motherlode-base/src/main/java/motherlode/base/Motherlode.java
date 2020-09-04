package motherlode.base;

import motherlode.base.api.MotherlodeAssets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Motherlode implements ModInitializer {

    public static final String MODID = "motherlode";

    @Override
    public void onInitialize() {

        MotherlodeAssets.EVENT.register(pack -> MotherlodeAssets.getProcessors().forEach(
                p -> p.getRight().accept(pack, p.getLeft())));
    }
    public static Identifier id(String namespace, String name) { return new Identifier(namespace, name); }
    public static Identifier id(String name) {
        return id(MODID, name);
    }
}