package motherlode.core.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.Artifice;

import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import motherlode.core.Motherlode;
import motherlode.core.block.DefaultBlock;
import motherlode.core.item.DefaultItem;
import motherlode.core.registry.MotherlodePotions.PotionModelInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
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

            for (PotionModelInfo info : MotherlodePotions.potionPredicateValues.values()) {
                pack.addItemModel(Motherlode.id("potions/" + info.model), (model) -> model
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", Motherlode.id("item/potions/" + info.model)));
            }

            pack.addItemModel(new Identifier("potion"), (model) -> {
                 model.parent(new Identifier("item/generated"));
                 model.texture("layer0", new Identifier("item/potion"));

                 for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                     if (info.model != null)
                        model.override( override -> potionPredicate(override, info.predicateValue).model(Motherlode.id("item/potions/" + info.model)) );
                 }
             });

        });
    }

    private static ModelBuilder.Override potionPredicate(ModelBuilder.Override override, Number value) {
	    override.with("predicate", JsonObject::new, predicate -> predicate.addProperty("potion_type", value));
	    return override;
    }
}