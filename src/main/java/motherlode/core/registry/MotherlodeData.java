package motherlode.core.registry;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

import motherlode.core.Motherlode;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeData {
    
    public void init() {}

    public static List<String> defaultLootTable = new ArrayList<>();

    public static void register() throws IOException {

        System.out.println("Loot Table Entries: " + defaultLootTable);

        Artifice.registerData(Motherlode.id("server_pack"), pack -> {
           for(String blockId : defaultLootTable){
                pack.addLootTable(Motherlode.id("blocks/"+blockId), table -> table
                .type(new Identifier("minecraft:block"))
                .pool(pool -> pool
                    .rolls(1)
                    .entry(entry -> entry
                        .type(new Identifier("minecraft","item"))
                        .name(Motherlode.id(blockId))
                    )
                    .condition(new Identifier("minecraft","survives_explosion"), TypedJsonBuilder::build)
                )
            );
           }
            defaultLootTable = null;
        }).dumpResources("server_pack");
    }
}