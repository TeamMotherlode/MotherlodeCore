package motherlode.core.registry;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.Artifice;

import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import motherlode.core.Motherlode;
import motherlode.core.block.stateproperty.BlockDyeColor;
import motherlode.core.registry.MotherlodePotions.PotionModelInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MotherlodeAssets {

	public static void init() {
    }
    public static void register(){
        Artifice.registerAssets(Motherlode.id("client_pack"), pack -> {
            for(Block block : MotherlodeBlocks.defaultStateList) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                pack.addBlockState(Motherlode.id(blockId), state -> state 
                    .variant("", settings -> settings
                        .model(Motherlode.id("block/"+blockId))
                    )
                );
            }
            for(Block block : MotherlodeBlocks.defaultModelList) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                pack.addBlockModel(Motherlode.id(blockId), state -> state 
                    .parent(new Identifier("block/cube_all"))
                    .texture("all", Motherlode.id("block/"+blockId))
                );
            }
            for(Block block : MotherlodeBlocks.defaultItemModelList) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                pack.addItemModel(Motherlode.id(blockId), state -> state 
                    .parent(Motherlode.id("block/"+blockId))
                );
            }
            for(Item item : MotherlodeItems.defaultItemModelList) {
                String itemId = item.getTranslationKey().replace("item.motherlode.","");
                pack.addItemModel(Motherlode.id(itemId), state -> state 
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id("item/"+itemId))
                );
            }

            for (SlabBlock block : MotherlodeBlocks.usesSlabModel) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                for (String variant : new String[]{"_top",""}) {
                    pack.addBlockModel(Motherlode.id(blockId + variant), model -> model
                            .parent(new Identifier("block/slab" + variant))
                            .texture("top", Motherlode.id("block/" + blockId + "_top"))
                            .texture("bottom", Motherlode.id("block/" + blockId + "_top"))
                            .texture("side", Motherlode.id("block/" + blockId + "_side"))
                    );
                }
                pack.addBlockModel(Motherlode.id(blockId + "_double"), model -> model
                        .parent(new Identifier("block/cube_column"))
                        .texture("end", Motherlode.id("block/" + blockId + "_top"))
                        .texture("side", Motherlode.id("block/" + blockId + "_side"))
                );
                pack.addBlockState(Motherlode.id(blockId), builder -> builder
                    .variant("type=top", settings -> settings.model(Motherlode.id("block/" + blockId + "_top")))
                    .variant("type=bottom", settings -> settings.model(Motherlode.id("block/" + blockId)))
                    .variant("type=double", settings -> settings.model(Motherlode.id("block/" + blockId + "_double")))
                );
            }

            for (StairsBlock block : MotherlodeBlocks.usesStairModel) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                String texId = blockId.replace("_stairs", "");
                for (int i = 0; i < 3; i++) {
                    int ii = i;
                    pack.addBlockModel(Motherlode.id(blockId + modelStrings[i]), model -> model
                        .parent(new Identifier("block/" + (ii == 0? "" : ii == 1? "inner_" : "outer_") + "stairs"))
                        .texture("top", Motherlode.id("block/" + texId))
                        .texture("bottom", Motherlode.id("block/" + texId))
                        .texture("side", Motherlode.id("block/" + texId))
                    );
                }
                pack.addBlockState(Motherlode.id(blockId), builder -> stairBlockState(builder,blockId));
            }

            for (Block block : MotherlodeBlocks.usesPillarModel) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                for (String variant : new String[]{"","_horizontal"}) {
                    pack.addBlockModel(Motherlode.id(blockId + variant), model -> model
                            .parent(new Identifier("block/cube_column" + variant))
                            .texture("end", Motherlode.id("block/" + blockId + "_top"))
                            .texture("side", Motherlode.id("block/" + blockId + "_side"))
                    );
                }
                pack.addBlockState(Motherlode.id(blockId), builder -> builder
                    .variant("axis=x", settings -> settings.model(Motherlode.id("block/" + blockId + "_horizontal")).rotationX(90).rotationY(90))
                    .variant("axis=y", settings -> settings.model(Motherlode.id("block/" + blockId)))
                    .variant("axis=z", settings -> settings.model(Motherlode.id("block/" + blockId + "_horizontal")).rotationX(90))
                );
            }

            /* DEAD CODE
            for (Block block : MotherlodeBlocks.paintableBlocks) {
                String blockId = block.getTranslationKey().replace("block.motherlode.","");
                System.out.println(blockId);
                for (BlockDyeColor color : BlockDyeColor.values()) {
                    System.out.println(blockId + "_" + color.asString());
                    pack.addBlockModel(Motherlode.id(blockId + "_" + color.asString()), model -> model
                            .parent(Motherlode.id("block/single_face"))
                            .texture("texture", Motherlode.id("block/" + blockId + "_" + color.asString()))
                    );
                }
                pack.addBlockState(Motherlode.id(blockId), builder -> builder
                        .multipartCase(cases -> {
                            for (BlockDyeColor color : BlockDyeColor.values()) {
                                System.out.println("Blockstating " + blockId + "_" + color.asString());
                                cases.when("north", color.asString()).apply(variant -> variant
                                        .model(Motherlode.id("block/" + blockId + "_" + color.asString()))
                                );
                                cases.when("south", color.asString()).apply(variant -> variant
                                        .model(Motherlode.id("block/" + blockId + "_" + color.asString()))
                                        .rotationY(180)
                                );
                                cases.when("east", color.asString()).apply(variant -> variant
                                        .model(Motherlode.id("block/" + blockId + "_" + color.asString()))
                                        .rotationY(270)
                                );
                                cases.when("west", color.asString()).apply(variant -> variant
                                        .model(Motherlode.id("block/" + blockId + "_" + color.asString()))
                                        .rotationY(90)
                                );
                                cases.when("up", color.asString()).apply(variant -> variant
                                        .model(Motherlode.id("block/" + blockId + "_" + color.asString()))
                                        .rotationX(90)
                                );
                                cases.when("down", color.asString()).apply(variant -> variant
                                        .model(Motherlode.id("block/" + blockId + "_" + color.asString()))
                                        .rotationX(270)
                                );
                            }
                        })
                );
            } DEAD CODE */

            for (PotionModelInfo info : MotherlodePotions.potionModelInfos.values()) {
                if (!info.useDefaultModel)
                    pack.addItemModel(Motherlode.id("potions/" + info.model), (model) -> model
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", Motherlode.id("item/potions/" + info.model)));
            }

            pack.addItemModel(Motherlode.id("potions/default"), (model) -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", new Identifier("item/potion_overlay"))
                .texture("layer1", new Identifier("item/potion")));

            pack.addItemModel(new Identifier("potion"), (model) -> {
                 model.parent(new Identifier("item/generated"));
                 model.texture("layer0", new Identifier("item/potion"));
                model.texture("layer1", new Identifier("item/potion_overlay"));

                 for (PotionModelInfo info : MotherlodePotions.getPotionModelInfos()) {
                     if (info.model == null || info.useDefaultModel)
                         model.override( override -> potionPredicate(override, info.predicateValue).model(Motherlode.id("item/potions/default")) );
                     else
                         model.override( override -> potionPredicate(override, info.predicateValue).model(Motherlode.id("item/potions/" + info.model)) );

                 }
             });

        });
    }

    private static ModelBuilder.Override potionPredicate(ModelBuilder.Override override, Number value) {
	    override.with("predicate", JsonObject::new, predicate -> predicate.addProperty("potion_type", value));
	    return override;
    }


    private static final String[] facings = new String[]{"east","north","south","west"};
    private static final String[] halfs = new String[]{"bottom","top"};
    private static final String[] shapes = new String[]{"inner_left","inner_right","outer_left","outer_right","straight"};
    private static final String[] modelStrings = new String[]{"","_inner","_outer"};

    // models: 0 = "", 1 = "_inner", 2 = "_outer" | xs & ys: # = # * 90
    private static final int[] models = new int[]{1,1,2,2,0,1,1,2,2,0};
    private static final int[] xs = new int[]{0,0,0,0,0,2,2,2,2,2};
    private static final int[] ys = new int[]{3,0,3,0,0,0,1,0,1,0,0,2,2,3,3,3,3,3,0,3,3,0,0,1,1,1,1,1,2,1,1,1,1,2,2,2,2,2,3,2};

    private static void stairBlockState(BlockStateBuilder builder, String id) {
        int i = 0;
        for (String facing : facings) {
            int j = 0;
            for (String half : halfs) {
                for (String shape : shapes) {
                    int jj = j;
                    int ii = i;
                    builder.variant("facing="+facing+",half="+half+",shape="+shape, settings ->
                            settings.model(Motherlode.id("block/"+id+modelStrings[models[jj]]))
                                    .rotationX(xs[jj]*90)
                                    .rotationY(ys[ii]*90)
                                    .uvlock(xs[jj] != 0 || ys[ii] != 0));
                    i++;
                    j++;
                }
            }
        }
    }
}