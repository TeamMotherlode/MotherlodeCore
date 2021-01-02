package motherlode.spelunky;

import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.assets.DataProcessor;
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

    public static final PlatformBlocks OAK_PLATFORM = register("oak", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/oak_planks")));
    public static final PlatformBlocks SPRUCE_PLATFORM = register("spruce", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/spruce_planks")));
    public static final PlatformBlocks BIRCH_PLATFORM = register("birch", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/birch_planks")));
    public static final PlatformBlocks JUNGLE_PLATFORM = register("jungle", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/jungle_planks")));
    public static final PlatformBlocks ACACIA_PLATFORM = register("acacia", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/acacia_planks")));
    public static final PlatformBlocks DARK_OAK_PLATFORM = register("dark_oak", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/dark_oak_planks")));
    public static final PlatformBlocks CRIMSON_PLATFORM = register("crimson", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/crimson_planks")));
    public static final PlatformBlocks WARPED_PLATFORM = register("warped", id -> new PlatformBlocks(id, BLOCK_ITEM_SETTINGS, new Identifier("block/warped_planks")));

    public static Block register(String name, Block block, AssetProcessor assets, DataProcessor data) {
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            MotherlodeModule.id(name),
            block,
            assets,
            data
        );
    }

    public static PlatformBlocks register(String name, Function<Identifier, PlatformBlocks> blocks) {
        return blocks.apply(MotherlodeModule.id(name)).register();
    }

    public static void init() {
    }
}
