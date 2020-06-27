package motherlode.core.registry;

import java.util.ArrayList;

import com.swordglowsblue.artifice.api.Artifice;

import motherlode.core.Motherlode;
import motherlode.core.block.DefaultBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MotherlodeAssets {

	public static void init() {
    }
    public static void register(ArrayList<DefaultBlock> defaultStateList, ArrayList<DefaultBlock> defaultModelList, ArrayList<DefaultBlock> defaultItemModelList){
        Artifice.registerAssets(Motherlode.id("client_pack"), pack -> {
            for(int i = 0; i < defaultStateList.size(); i++){
                String blockId = defaultStateList.get(i).getTranslationKey().replace("block.motherlode.","");
                pack.addBlockState(Motherlode.id(blockId), state -> state 
                    .variant("", settings -> settings
                        .model(Motherlode.id("block/"+blockId))
                    )
                );
            }
            for(int i = 0; i < defaultModelList.size(); i++){
                String blockId = defaultModelList.get(i).getTranslationKey().replace("block.motherlode.","");
                pack.addBlockModel(Motherlode.id(blockId), state -> state 
                    .parent(new Identifier("block/cube_all"))
                    .texture("all", Motherlode.id("block/"+blockId))
                );
            }
            for(int i = 0; i < defaultItemModelList.size(); i++) {
                String blockId = defaultItemModelList.get(i).getTranslationKey().replace("block.motherlode.","");
                pack.addItemModel(Motherlode.id(blockId), state -> state 
                    .parent(Motherlode.id("block/"+blockId))
                );
            }
        });
    }
}