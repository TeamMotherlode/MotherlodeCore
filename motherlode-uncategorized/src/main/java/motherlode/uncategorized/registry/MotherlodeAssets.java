package motherlode.uncategorized.registry;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import motherlode.base.Motherlode;
import motherlode.uncategorized.block.DefaultShovelableBlock;
import motherlode.uncategorized.block.PotBlock;
import motherlode.uncategorized.block.stateproperty.BlockDyeColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class MotherlodeAssets {

    public static final Map<Block, Supplier<String>> flatItemModelList = new HashMap<>();

	public static void init() {
    }
    public static void register(){
        Artifice.registerAssets(Motherlode.id("resource_pack"), pack -> {
            for(Block block : MotherlodeBlocks.defaultStateList) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addBlockState(Motherlode.id(blockId), state -> state 
                    .variant("", settings -> settings
                        .model(Motherlode.id("block/"+blockId))
                    )
                );
            }
            for(Block block : MotherlodeBlocks.defaultModelList) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addBlockModel(Motherlode.id(blockId), state -> state 
                    .parent(new Identifier("block/cube_all"))
                    .texture("all", Motherlode.id("block/"+blockId))
                );
            }
            for(Block block : MotherlodeBlocks.defaultPlantModelList) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addBlockModel(Motherlode.id(blockId), state -> state
                        .parent(new Identifier("block/tinted_cross"))
                        .texture("cross", Motherlode.id("block/"+blockId))
                );
            }
            for(Block block : MotherlodeBlocks.thickCrossModelList) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addBlockModel(Motherlode.id(blockId), state -> state
                        .parent(Motherlode.id("block/thick_cross"))
                        .texture("cross", Motherlode.id("block/"+blockId))
                );
            }
            for(Block block : MotherlodeBlocks.defaultItemModelList) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addItemModel(Motherlode.id(blockId), state -> state 
                    .parent(Motherlode.id("block/"+blockId))
                );
            }
            for(Block block : flatItemModelList.keySet()) {
                String itemId = Registry.BLOCK.getId(block).getPath();
                String texture = flatItemModelList.get(block).get();
                pack.addItemModel(Motherlode.id(itemId), state -> state
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", Motherlode.id("block/"+texture))
                );
            }
            for(Item item : MotherlodeItems.defaultItemModelList) {
                String itemId = Registry.ITEM.getId(item).getPath();
                pack.addItemModel(Motherlode.id(itemId), state -> state 
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id("item/"+itemId))
                );
            }
            for(Item item : MotherlodeItems.handheldItemModelList) {
                String itemId = Registry.ITEM.getId(item).getPath();
                pack.addItemModel(Motherlode.id(itemId), state -> state
                        .parent(new Identifier("item/handheld"))
                        .texture("layer0", Motherlode.id("item/"+itemId))
                );
            }

            for (Map.Entry<SlabBlock,Boolean>  entry : MotherlodeBlocks.usesSlabModel.entrySet()) {
                String blockId = Registry.BLOCK.getId(entry.getKey()).getPath();
                String texId = blockId.replace("_slab", "").replace("_pillar","_pillar_side");
                String namespace = entry.getValue() ? "motherlode" : "minecraft";
                for (String variant : new String[]{"_top",""}) {
                    pack.addBlockModel(Motherlode.id(blockId + variant), model -> model
                            .parent(new Identifier("block/slab" + variant))
                            .texture("top", new Identifier(namespace,"block/" + texId))
                            .texture("bottom", new Identifier(namespace,"block/" + texId))
                            .texture("side", new Identifier(namespace,"block/" + texId))
                    );
                }
                pack.addBlockModel(Motherlode.id(blockId + "_double"), model -> model
                        .parent(new Identifier("block/cube_column"))
                        .texture("end", new Identifier(namespace,"block/" + texId))
                        .texture("side", new Identifier(namespace,"block/" + texId))
                );
                pack.addBlockState(Motherlode.id(blockId), builder -> builder
                    .variant("type=top", settings -> settings.model(Motherlode.id("block/" + blockId + "_top")))
                    .variant("type=bottom", settings -> settings.model(Motherlode.id("block/" + blockId)))
                    .variant("type=double", settings -> settings.model(Motherlode.id("block/" + blockId + "_double")))
                );
            }

            for (Map.Entry<StairsBlock,Boolean>  entry : MotherlodeBlocks.usesStairModel.entrySet()) {
                String blockId = Registry.BLOCK.getId(entry.getKey()).getPath();
                String texId = blockId.replace("_stairs", "");
                String namespace = entry.getValue() ? "motherlode" : "minecraft";
                for (int i = 0; i < 3; i++) {
                    int ii = i;
                    pack.addBlockModel(Motherlode.id(blockId + modelStrings[i]), model -> model
                        .parent(new Identifier("block/" + (ii == 0? "" : ii == 1? "inner_" : "outer_") + "stairs"))
                        .texture("top", new Identifier(namespace, "block/" + texId))
                        .texture("bottom", new Identifier(namespace, "block/" + texId))
                        .texture("side", new Identifier(namespace, "block/" + texId))
                    );
                }
                pack.addBlockState(Motherlode.id(blockId), builder -> stairBlockState(builder,blockId));
            }

            for(DefaultShovelableBlock block : MotherlodeBlocks.shovelableBlocks) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addBlockState(Motherlode.id(blockId), state -> {
                    for (int i = 0; i < (block.isRotatable ? 4 : 1); i++) {
                        int finalI = i;
                        state.variant("shoveled=false", settings -> settings
                                .model(Motherlode.id("block/" + blockId))
                                .rotationY(finalI * 90)
                        );
                        state.variant("shoveled=true", settings -> settings
                                .model(Motherlode.id("block/" + blockId + "_shoveled"))
                                .rotationY(finalI * 90)
                        );
                    }
                });
                pack.addBlockModel(Motherlode.id(blockId), state -> state
                        .parent(new Identifier("block/cube_all"))
                        .texture("all", Motherlode.id("block/"+blockId))
                );
                pack.addBlockModel(Motherlode.id(blockId+"_shoveled"), state -> state
                        .parent(Motherlode.id("block/cube_lowered"))
                        .texture("top", Motherlode.id("block/"+blockId))
                        .texture("side", Motherlode.id("block/"+blockId))
                        .texture("bottom", Motherlode.id("block/"+blockId))
                );
            }

            for (Block block : MotherlodeBlocks.usesPillarModel) {
                String blockId = Registry.BLOCK.getId(block).getPath();
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

            for(Block block : MotherlodeBlocks.usesPaintableModel) {
                String blockId = Registry.BLOCK.getId(block).getPath();
                pack.addBlockModel(Motherlode.id(blockId + "_base"), model -> model
                        .parent(new Identifier("block/cube_all"))
                        .texture("all", Motherlode.id("block/" + blockId + "_side"))
                );
                for (BlockDyeColor color : BlockDyeColor.values()) {
                    pack.addBlockModel(Motherlode.id(blockId + "_" + color.asString()), model -> model
                            .parent(Motherlode.id("block/paintable_face"))
                            .texture("texture", Motherlode.id("block/" + blockId + "_" + color.asString()))
                    );
                }
                pack.addBlockState(Motherlode.id(blockId), builder -> {
                        builder.multipartCase(cases -> cases.apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_base"))));
                        for (BlockDyeColor color : BlockDyeColor.values()) {
                            builder.multipartCase(cases -> cases.when("side_a", color.asString()).when("variant", "0").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString()))));
                            builder.multipartCase(cases -> cases.when("side_b", color.asString()).when("variant", "0").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(180)));
                            builder.multipartCase(cases -> cases.when("side_a", color.asString()).when("variant", "1").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(90)));
                            builder.multipartCase(cases -> cases.when("side_b", color.asString()).when("variant", "1").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(270)));
                            builder.multipartCase(cases -> cases.when("side_a", color.asString()).when("variant", "2").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString()))));
                            builder.multipartCase(cases -> cases.when("side_b", color.asString()).when("variant", "2").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(270)));
                            builder.multipartCase(cases -> cases.when("side_a", color.asString()).when("variant", "3").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString()))));
                            builder.multipartCase(cases -> cases.when("side_b", color.asString()).when("variant", "3").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(90)));
                            builder.multipartCase(cases -> cases.when("side_a", color.asString()).when("variant", "4").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(180)));
                            builder.multipartCase(cases -> cases.when("side_b", color.asString()).when("variant", "4").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(90)));
                            builder.multipartCase(cases -> cases.when("side_a", color.asString()).when("variant", "5").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(180)));
                            builder.multipartCase(cases -> cases.when("side_b", color.asString()).when("variant", "5").apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color.asString())).rotationY(270)));
                        }
                    }
                );
            }

            pack.addBlockState(Motherlode.id("pot"), state -> {
                for (int i = 0; i <= PotBlock.maxPattern; i++) {
                    int ii = i;
                    pack.addBlockModel(Motherlode.id("pot_with_overlay_" + i), model -> model
                            .parent(Motherlode.id("block/pot"))
                            .texture("overlay", Motherlode.id("block/pots/pot_overlay_" + ii))
                    );
                    state.variant("pattern="+i, settings -> settings.model(Motherlode.id("block/pot_with_overlay_" + ii)));
                }
            });

            pack.addItemModel(Motherlode.id("pot_template"), model2 -> model2
                    .parent(Motherlode.id("block/pot"))
                    .texture("overlay", Motherlode.id("block/pots/pot_overlay_1"))

                    .display("thirdperson_righthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(66F, 135F, 0F).translation(0,4,4))
                    .display("thirdperson_lefthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(66F, 135F, 0F).translation(0,4,4))
                    .display("firstperson_righthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(0F, 135F, 0F).translation(2,2,-2))
                    .display("firstperson_lefthand", settings -> settings.scale(0.625F,0.625F,0.625F).rotation(0F, 135F, 0F).translation(0,5,10))
            );

            pack.addItemModel(Motherlode.id("pot"), model -> {
                for (int i = 0; i <= PotBlock.maxPattern; i++) {
                    float pattern = i / 100F;
                    int ii = i;
                    model.override(override -> floatPredicate(override, "pot_pattern", pattern).model(Motherlode.id("item/pot_" + ii)));

                    pack.addItemModel(Motherlode.id("pot_" + ii), model2 -> model2
                       .parent(Motherlode.id("item/pot_template"))
                       .texture("overlay", Motherlode.id("block/pots/pot_overlay_" + ii))
                    );

                }
            });

            int[] stackCounts = new int[]{0,8,16,24,32,40,48,56,64};

            for (int stackCount : stackCounts) {
                pack.addItemModel(Motherlode.id("rope" + stackCount), builder -> builder
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id("item/rope/rope" + stackCount))
                );
            }

            pack.addItemModel(Motherlode.id("rope"), builder -> {
                for (int stackCount : stackCounts)
                    builder.override(override -> floatPredicate(override, "stack_count", stackCount / 100F).model(Motherlode.id("item/rope" + stackCount)));
            });

            pack.addBlockState(Motherlode.id("rope"), builder -> {
                String[] directions = new String[]{"south", "west", "north", "east"};
                for (int i = 0; i < directions.length; i++) {
                    int ii = i;
                    builder.variant("bottom=false,connected=up,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_top")).rotationY(ii *90));
                    builder.variant("bottom=true,connected=up,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_top_bottom")).rotationY(ii *90));
                    builder.variant("bottom=false,connected=side,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_side")).rotationY(ii *90));
                    builder.variant("bottom=true,connected=side,facing="+directions[i], settings -> settings.model(Motherlode.id("block/rope_side_bottom")).rotationY(ii *90));
                }
                builder.variant("bottom=false,connected=none", settings -> settings.model(Motherlode.id("block/rope")));
                builder.variant("bottom=true,connected=none", settings -> settings.model(Motherlode.id("block/rope_bottom")));
            });

        });
    }

    public static ModelBuilder.Override floatPredicate(ModelBuilder.Override override, String name, Number value) {
	    override.with("predicate", JsonObject::new, predicate -> predicate.addProperty(name, value));
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