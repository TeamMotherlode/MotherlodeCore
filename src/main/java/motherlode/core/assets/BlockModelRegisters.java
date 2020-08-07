package motherlode.core.assets;

import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import motherlode.core.Motherlode;
import motherlode.core.block.stateproperty.BlockDyeColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import static com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;

@Environment(EnvType.CLIENT)
public class BlockModelRegisters {

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> DEFAULT = (pack, blockId, _b ) ->
        pack.addBlockModel(Motherlode.id(blockId), state -> state
            .parent(new Identifier("block/cube_all"))
            .texture("all", Motherlode.id("block/"+blockId))
        );

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> PLANT = (pack, blockId, _b ) ->
        pack.addBlockModel(Motherlode.id(blockId), state -> state
                .parent(new Identifier("block/tinted_cross"))
                .texture("cross", Motherlode.id("block/" + blockId))
        );

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> CROSS = (pack, blockId, _b ) ->
        pack.addBlockModel(Motherlode.id(blockId), state -> state
                .parent(Motherlode.id("block/thick_cross"))
                .texture("cross", Motherlode.id("block/"+blockId))
        );


    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> SLAB = (pack, blockId, newStone ) -> {
        String texId = blockId.replace("_slab", "").replace("_pillar","_pillar_side");
        String namespace = newStone ? "motherlode" : "minecraft";
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
    };

    private static final String[] modelStrings = new String[]{"","_inner","_outer"};
    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> STAIR = (pack, blockId, newStone ) -> {
        String texId = blockId.replace("_stairs", "");
        String namespace = newStone ? "motherlode" : "minecraft";
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
    };

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> PILLAR = (pack, blockId, _b ) -> {
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
    };

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> PLATFORM = (pack, blockId, _b ) ->
        pack.addBlockModel(Motherlode.id(blockId), model -> model
            .parent(Motherlode.id("block/platform"))
            .texture("top", new Identifier("block/" + blockId.substring(0, blockId.lastIndexOf('_')) + "_planks"))
            .texture("side", Motherlode.id("block/platforms/" + blockId + "_side"))
        );

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> PAINTABLE = (pack, blockId, _b ) -> {
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
                        String colorStr = color.asString();
                        addPaintableCase(builder, "side_a", colorStr, "0", blockId, 0);
                        addPaintableCase(builder, "side_b", colorStr, "0", blockId, 180);
                        addPaintableCase(builder, "side_a", colorStr, "1", blockId, 90);
                        addPaintableCase(builder, "side_b", colorStr, "1", blockId, 270);
                        addPaintableCase(builder, "side_a", colorStr, "2", blockId, 0);
                        addPaintableCase(builder, "side_b", colorStr, "2", blockId, 270);
                        addPaintableCase(builder, "side_a", colorStr, "3", blockId, 0);
                        addPaintableCase(builder, "side_b", colorStr, "3", blockId, 90);
                        addPaintableCase(builder, "side_a", colorStr, "4", blockId, 180);
                        addPaintableCase(builder, "side_b", colorStr, "4", blockId, 90);
                        addPaintableCase(builder, "side_a", colorStr, "5", blockId, 180);
                        addPaintableCase(builder, "side_b", colorStr, "5", blockId, 270);
                    }
                }
        );
    };

    private static void addPaintableCase(BlockStateBuilder builder, String side, String color, String state, String blockId, int rotationY) {
        builder.multipartCase(cases -> cases.when(side, color).when("variant", state).apply(variant -> variant.model(Motherlode.id("block/" + blockId + "_" + color)).rotationY(rotationY)));
    }

    public static final TriConsumer<ClientResourcePackBuilder, String, Boolean> SHOVELABLE = (pack, blockId, _b ) -> {
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
        pack.addBlockState(Motherlode.id(blockId), state -> {
            for (int i = 0; i < (/*block.isRotatable? 4 : 1*/1); i++) {
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
    };


    private static final String[] facings = new String[]{"east","north","south","west"};
    private static final String[] halfs = new String[]{"bottom","top"};
    private static final String[] shapes = new String[]{"inner_left","inner_right","outer_left","outer_right","straight"};

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
