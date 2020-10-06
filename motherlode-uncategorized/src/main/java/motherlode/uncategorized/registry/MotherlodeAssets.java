package motherlode.uncategorized.registry;

import com.google.gson.JsonObject;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.uncategorized.block.DefaultShovelableBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class MotherlodeAssets implements AssetProcessor {

    public static final Map<Block, Supplier<String>> flatItemModelList = new HashMap<>();

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
        for (Block block : MotherlodeBlocks.defaultStateList) {
            String blockId = Registry.BLOCK.getId(block).getPath();
            pack.addBlockState(Motherlode.id(blockId), state -> state
                    .variant("", settings -> settings
                            .model(Motherlode.id("block/" + blockId))
                    )
            );
        }
        for (Block block : MotherlodeBlocks.defaultModelList) {
            String blockId = Registry.BLOCK.getId(block).getPath();
            pack.addBlockModel(Motherlode.id(blockId), state -> state
                    .parent(new Identifier("block/cube_all"))
                    .texture("all", Motherlode.id("block/" + blockId))
            );
        }
        for (Block block : MotherlodeBlocks.defaultPlantModelList) {
            String blockId = Registry.BLOCK.getId(block).getPath();
            pack.addBlockModel(Motherlode.id(blockId), state -> state
                    .parent(new Identifier("block/tinted_cross"))
                    .texture("cross", Motherlode.id("block/" + blockId))
            );
        }
        for (Block block : MotherlodeBlocks.thickCrossModelList) {
            String blockId = Registry.BLOCK.getId(block).getPath();
            pack.addBlockModel(Motherlode.id(blockId), state -> state
                    .parent(Motherlode.id("block/thick_cross"))
                    .texture("cross", Motherlode.id("block/" + blockId))
            );
        }
        for (Block block : MotherlodeBlocks.defaultItemModelList) {
            String blockId = Registry.BLOCK.getId(block).getPath();
            pack.addItemModel(Motherlode.id(blockId), state -> state
                    .parent(Motherlode.id("block/" + blockId))
            );
        }
        for (Block block : flatItemModelList.keySet()) {
            String itemId = Registry.BLOCK.getId(block).getPath();
            String texture = flatItemModelList.get(block).get();
            pack.addItemModel(Motherlode.id(itemId), state -> state
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id("block/" + texture))
            );
        }

        for (DefaultShovelableBlock block : MotherlodeBlocks.shovelableBlocks) {
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
                    .texture("all", Motherlode.id("block/" + blockId))
            );
            pack.addBlockModel(Motherlode.id(blockId + "_shoveled"), state -> state
                    .parent(Motherlode.id("block/cube_lowered"))
                    .texture("top", Motherlode.id("block/" + blockId))
                    .texture("side", Motherlode.id("block/" + blockId))
                    .texture("bottom", Motherlode.id("block/" + blockId))
            );
        }
    }

    public static ModelBuilder.Override floatPredicate(ModelBuilder.Override override, String name, Number value) {
        override.with("predicate", JsonObject::new, predicate -> predicate.addProperty(name, value));
        return override;
    }
}