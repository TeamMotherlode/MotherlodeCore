package motherlode.core.registry;

import java.util.List;

import com.swordglowsblue.artifice.api.Artifice;

import motherlode.core.Motherlode;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class MotherlodeAssets {

	public static void init() {
    }
    public static <T extends Block> void register(List<T> hasDefaultState, List<T> hasDefaultModel, List<T> hasDefaultItemModel){
        Artifice.registerAssets(Motherlode.id("client_pack"), pack -> {
            for(int i = 0; i > hasDefaultState.size(); i++){
                String blockId = hasDefaultState.get(i).getName().asString();
                pack.addBlockState(Motherlode.id(blockId), state -> state 
                    .variant("", settings -> settings
                        .model(Motherlode.id("block/"+blockId))
                    )
                );
            }
            for(int i = 0; i > hasDefaultModel.size(); i++){
                String blockId = hasDefaultModel.get(i).getName().asString();
                pack.addBlockModel(Motherlode.id(blockId), state -> state 
                    .parent(new Identifier("block/cube_all"))
                    .texture("all", Motherlode.id("block/"+blockId))
                );
            }
            for(int i = 0; i > hasDefaultItemModel.size(); i++){
                String blockId = hasDefaultItemModel.get(i).getName().asString();
                pack.addItemModel(Motherlode.id(blockId), state -> state 
                    .parent(Motherlode.id("block/"+blockId))
                );
            }
        });
    }
}