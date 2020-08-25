package motherlode.uncategorized.registry;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;
import motherlode.base.Motherlode;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MotherlodeData {
    
    public void init(){
        // Called to load the class
    }

    public static void register(){
        Artifice.registerData(Motherlode.id("data_pack"), pack -> {
           for(Block block : MotherlodeBlocks.defaultLootTableList){
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addLootTable(Motherlode.id("blocks/"+blockId), table -> table
                .type(new Identifier("minecraft:block"))
                .pool(pool -> pool
                    .rolls(1)
                    .entry(entry -> entry
                        .type(new Identifier("minecraft","item"))
                        .name(Motherlode.id(blockId))
                    )
                    .condition(new Identifier("minecraft","survives_explosion"), TypedJsonBuilder::build
                    )
                )
            );
           }
        });
    }
}