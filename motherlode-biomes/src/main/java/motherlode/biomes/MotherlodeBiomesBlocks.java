package motherlode.biomes;

import motherlode.base.CommonAssets;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.RegisterBlock;
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

    public static final Block SLIGHTLY_ROCKY_DIRT = register(new RegisterBlock().name("slightly_rocky_dirt").block(new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM))).assets(CommonAssets.DEFAULT_BLOCK));
    public static final Block ROCKY_DIRT = register(new RegisterBlock().name("rocky_dirt").block(new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM))).assets(CommonAssets.DEFAULT_BLOCK));
    public static final Block VERY_ROCKY_DIRT = register(new RegisterBlock().name("very_rocky_dirt").block(new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM))).assets(CommonAssets.DEFAULT_BLOCK));

    public static final Block SPROUTS = register(new RegisterBlock().name("sprouts").block(new DefaultPlantBlock(4, FabricBlockSettings.copy(Blocks.GRASS))).assets(flatItemModel("sprouts_0")));
    public static final Block DRACAENA = register(new RegisterBlock().name("dracaena").block(new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS))).assets(CommonAssets.FLAT_ITEM_MODEL));
    public static final Block PHILODENDRON = register(new RegisterBlock().name("philodendron").block(new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS))).assets(CommonAssets.FLAT_ITEM_MODEL));

    public static final Block MOSS = register(new RegisterBlock().name("moss").block(new MossBlock(FabricBlockSettings.copy(Blocks.GRASS))).assets(CommonAssets.BLOCK_ITEM));

    public static final Block WATERPLANT = register(new RegisterBlock().name("waterplant").block(new WaterplantBlock(FabricBlockSettings.copy(Blocks.SEAGRASS))).assets(DEFAULT_PROCESSOR));
    public static final Block REEDS = register(new RegisterBlock().name("reeds").block(new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS))).assets(CommonAssets.FLAT_ITEM_MODEL));
    public static final Block CATTAIL_REEDS = register(new RegisterBlock().name("cattail_reeds").block(new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS))).assets(CommonAssets.FLAT_ITEM_MODEL));
    public static final Block DRY_REEDS = register(new RegisterBlock().name("dry_reeds").block(new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS))).assets(CommonAssets.FLAT_ITEM_MODEL));

    private static Block register(RegisterBlock block) {
        return block.register(MotherlodeBiomesMod.MODID, BLOCK_ITEM_SETTINGS);
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
