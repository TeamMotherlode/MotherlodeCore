package motherlode.spelunky;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Registerable;
import motherlode.spelunky.block.PlatformBlocks;
import motherlode.spelunky.block.PotAssets;
import motherlode.spelunky.block.PotBlock;
import motherlode.spelunky.block.RopeAssets;
import motherlode.spelunky.block.RopeBlock;

public class MotherlodeSpelunkyBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.DECORATIONS);

    public static final Block POT = register("pot", new PotBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.GLASS).strength(2.0F, 2.0F)),
        PotAssets.POT_BLOCK_STATE.andThen(PotAssets.POT_TEMPLATE).andThen(PotAssets.POT_ITEM_MODEL), null);

    public static final Block ROPE = register("rope", new RopeBlock(FabricBlockSettings.of(Material.PLANT)),
        RopeAssets.ITEM_MODELS.andThen(RopeAssets.ITEM_MODEL).andThen(RopeAssets.BLOCK_STATE).andThen(CommonAssets.BLOCK_ITEM), CommonData.DEFAULT_BLOCK_LOOT_TABLE);

    public static final PlatformBlocks OAK_PLATFORM = register("oak", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/oak_planks")));
    public static final PlatformBlocks SPRUCE_PLATFORM = register("spruce", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/spruce_planks")));
    public static final PlatformBlocks BIRCH_PLATFORM = register("birch", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/birch_planks")));
    public static final PlatformBlocks JUNGLE_PLATFORM = register("jungle", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/jungle_planks")));
    public static final PlatformBlocks ACACIA_PLATFORM = register("acacia", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/acacia_planks")));
    public static final PlatformBlocks DARK_OAK_PLATFORM = register("dark_oak", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/dark_oak_planks")));
    public static final PlatformBlocks CRIMSON_PLATFORM = register("crimson", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/crimson_planks")));
    public static final PlatformBlocks WARPED_PLATFORM = register("warped", new PlatformBlocks(BLOCK_ITEM_SETTINGS, new Identifier("block/warped_planks")));

    public static Block register(String name, Block block, AssetProcessor assets, DataProcessor data) {
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            MotherlodeModule.id(name),
            block,
            null,
            assets,
            data
        );
    }

    public static <T extends PlatformBlocks> T register(String name, T blocks) {
        return Motherlode.register(
            blocks,
            MotherlodeModule.id(name),
            blocks,
            null,
            blocks,
            blocks
        );
    }

    public static void init() {
    }
}
