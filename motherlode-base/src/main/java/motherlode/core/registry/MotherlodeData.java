package motherlode.core.registry;

import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder;

import motherlode.core.Motherlode;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class MotherlodeData {
    
    public void init(){
    }

    public static void register(){
        Artifice.registerData(Motherlode.id("server_pack"), pack -> {
           for(Block block : MotherlodeBlocks.defaultLootTableList){
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
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