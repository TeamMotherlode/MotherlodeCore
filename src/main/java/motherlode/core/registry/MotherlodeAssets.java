package motherlode.core.registry;

import com.swordglowsblue.artifice.api.Artifice;

import motherlode.core.Motherlode;
import motherlode.core.block.DefaultBlock;
import motherlode.core.item.DefaultItem;
import net.minecraft.util.Identifier;

public class MotherlodeAssets {

	public static void init() {
    }
    public static void register(){
        Artifice.registerAssets(Motherlode.id("client_pack"), pack -> {
            for(DefaultBlock defaultBlock : MotherlodeBlocks.defaultStateList) {
                String blockId = defaultBlock.getTranslationKey().replace("block.motherlode.","");
                pack.addBlockState(Motherlode.id(blockId), state -> state 
                    .variant("", settings -> settings
                        .model(Motherlode.id("block/"+blockId))
                    )
                );
            }
            for(DefaultBlock defaultBlock : MotherlodeBlocks.defaultModelList) {
                String blockId = defaultBlock.getTranslationKey().replace("block.motherlode.","");
                pack.addBlockModel(Motherlode.id(blockId), state -> state 
                    .parent(new Identifier("block/cube_all"))
                    .texture("all", Motherlode.id("block/"+blockId))
                );
            }
            for(DefaultBlock defaultBlock : MotherlodeBlocks.defaultItemModelList) {
                String blockId = defaultBlock.getTranslationKey().replace("block.motherlode.","");
                pack.addItemModel(Motherlode.id(blockId), state -> state 
                    .parent(Motherlode.id("block/"+blockId))
                );
            }
            for(DefaultItem defaultItem : MotherlodeItems.defaultItemModelList) {
                String itemId = defaultItem.getTranslationKey().replace("item.motherlode.","");
                pack.addItemModel(Motherlode.id(itemId), state -> state 
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id("item/"+itemId))
                );
            }
        });
    }
}