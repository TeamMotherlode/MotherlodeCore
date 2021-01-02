package motherlode.enderinvasion;

import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.fabricmc.fabric.api.tag.TagRegistry;

public class MotherlodeEnderInvasionTags {
    public static final Tag<Block> SPREADABLE = register("spreadable");
    public static final Tag<Block> END_FOAM_REPLACEABLE = register("end_foam_replaceable");

    private static Tag<Block> register(String name) {
        return TagRegistry.block(MotherlodeModule.id(name));
    }

    public static void init() {
    }
}
