package motherlode.core.registry;

import com.swordglowsblue.artifice.api.Artifice;

import motherlode.core.Motherlode;
import motherlode.core.block.DefaultBlock;
import net.minecraft.util.Identifier;

public class MotherlodeData {
    
    public void init(){
    }

    public static void register(){
        Artifice.registerData(Motherlode.id("server_pack"), pack -> {
           for(DefaultBlock defaultBlock : MotherlodeBlocks.defaultLootTableList){
                String blockId = defaultBlock.getTranslationKey().replace("block.motherlode.","");
                pack.addLootTable(Motherlode.id("blocks/"+blockId), table -> table
                .type(new Identifier("minecraft:block"))
                .pool(pool -> pool
                    .rolls(1)
                    .entry(entry -> entry
                        .type(new Identifier("minecraft","item"))
                        .name(Motherlode.id(blockId))
                    )
                    .condition(new Identifier("minecraft","survives_explosion"), condition -> condition
                        .build()
                    )
                )
            );
           }
        });
    }
}