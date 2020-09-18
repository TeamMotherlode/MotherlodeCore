package motherlode.biomes;

import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.Registerable;
import motherlode.biomes.block.DefaultPlantBlock;
import motherlode.biomes.block.MossBlock;
import motherlode.biomes.block.ReedsBlock;
import motherlode.biomes.block.WaterplantBlock;
import motherlode.uncategorized.block.DefaultShovelableBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class MotherlodeBiomesBlocks {

    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.DECORATIONS);
    private static final AssetProcessor DEFAULT_PROCESSOR = CommonAssets.FLAT_ITEM_MODEL.andThen(CommonAssets.PLANT);

    public static final Block SLIGHTLY_ROCKY_DIRT = register("slightly_rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)), CommonAssets.DEFAULT_BLOCK);
    public static final Block ROCKY_DIRT = register("rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)), CommonAssets.DEFAULT_BLOCK);
    public static final Block VERY_ROCKY_DIRT = register("very_rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)), CommonAssets.DEFAULT_BLOCK);

    public static final Block SPROUTS = register("sprouts", new DefaultPlantBlock(4, FabricBlockSettings.copy(Blocks.GRASS)), flatItemModel("sprouts_0"));
    public static final Block DRACAENA = register("dracaena", new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS)), CommonAssets.FLAT_ITEM_MODEL);
    public static final Block PHILODENDRON = register("philodendron", new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS)), CommonAssets.FLAT_ITEM_MODEL);

    public static final Block MOSS = register("moss", new MossBlock(FabricBlockSettings.copy(Blocks.GRASS)), CommonAssets.BLOCK_ITEM);

    public static final Block WATERPLANT = register("waterplant", new WaterplantBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), null);
    public static final Block REEDS = register("reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), CommonAssets.FLAT_ITEM_MODEL);
    public static final Block CATTAIL_REEDS = register("cattail_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), CommonAssets.FLAT_ITEM_MODEL);
    public static final Block DRY_REEDS = register("dry_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), CommonAssets.FLAT_ITEM_MODEL);

    private static Block register(String name, Block block, AssetProcessor p) {

        return Motherlode.register(
                Registerable.block(block, BLOCK_ITEM_SETTINGS),
                Motherlode.id(MotherlodeBiomesMod.MODID, name),
                block,
                null,
                p,
                CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }

    public static AssetProcessor flatItemModel(String textureName) {

        return (pack, id) -> {

            pack.addItemModel(id, state -> state
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", Motherlode.id(id.getNamespace(), "block/" + textureName))
            );
        };
    }

    public static void init() {
        // Called to load the class
    }
}
