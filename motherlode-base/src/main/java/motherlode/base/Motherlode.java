package motherlode.base;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import motherlode.base.api.MotherlodeAssets;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class Motherlode implements ModInitializer {

    public static final String MODID = "motherlode";

    private static final List<Identifier> BLOCKS_FOR_DEFAULT_LOOT_TABLE = new ArrayList<>();

    public static void addDefaultLootTable(Identifier id) {

        BLOCKS_FOR_DEFAULT_LOOT_TABLE.add(id);
    }

    @Override
    public void onInitialize() {

        MotherlodeAssets.EVENT.register(pack -> MotherlodeAssets.getProcessors().forEach(
                p -> p.getRight().accept(pack, p.getLeft())));

        Artifice.registerData(Motherlode.id("data_pack"), pack -> {
            for (Identifier id : BLOCKS_FOR_DEFAULT_LOOT_TABLE) {
                pack.addLootTable(Motherlode.id(id.getNamespace(), "blocks/" + id.getPath()), table -> table
                        .type(new Identifier("minecraft:block"))
                        .pool(pool -> pool
                                .rolls(1)
                                .entry(entry -> entry
                                        .type(new Identifier("minecraft","item"))
                                        .name(id)
                                )
                                .condition(new Identifier("minecraft","survives_explosion"), TypedJsonBuilder::build
                                )
                        )
                );
            }
        });
    }
    public static Identifier id(String namespace, String name) { return new Identifier(namespace, name); }
    public static Identifier id(String name) {
        return id(MODID, name);
    }
}