package motherlode.copperdungeon;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.copperdungeon.block.CutterTrapBlock;
import motherlode.copperdungeon.block.DecorationBlock;
import motherlode.copperdungeon.block.DefaultDoorBlock;
import motherlode.copperdungeon.block.DefaultLadderBlock;
import motherlode.copperdungeon.block.DefaultPaneBlock;
import motherlode.copperdungeon.block.ZapperTrapBlock;

public class MotherlodeCopperDungeonBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.REDSTONE);

    // public static final Block STEEL_BLOCK = register("steel_block", mineralBlock(4));
    public static final DecorationBlock STEEL_WALL = register("steel_wall", new DecorationBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4.0F, 5.0F), BLOCK_ITEM_SETTINGS));
    public static final DecorationBlock STEEL_TILES = register("steel_tiles", new DecorationBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(4.0F, 5.0F), BLOCK_ITEM_SETTINGS));

    public static final Block STEEL_LADDER = register("steel_ladder", new DefaultLadderBlock(AbstractBlock.Settings.of(Material.SUPPORTED).strength(0.8F).sounds(BlockSoundGroup.METAL).nonOpaque())); //TODO: Ladder sound?
    public static final Block STEEL_BARS = register("steel_bars", new DefaultPaneBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.CLEAR).requiresTool().strength(6.0F, 7.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));
    // public static final Block STEEL_PLATFORM = register("steel_platform", new PlatformBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.IRON).requiresTool().strength(4.0f,4.0f).sounds(BlockSoundGroup.METAL).nonOpaque()));
    // public static final Block STEEL_PLATFORM_STAIRS = register("steel_platform_stairs", new PlatformStairsBlock(STEEL_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(STEEL_PLATFORM)));
    public static final Block STEEL_DOOR = register("steel_door", new DefaultDoorBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.IRON).requiresTool().strength(6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));

    public static final Block CUTTER_TRAP = register("cutter_trap", new CutterTrapBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS).requiresTool().strength(3.0F, 3.0F).nonOpaque()));
    public static final Block ZAPPER_TRAP = register("zapper_trap", new ZapperTrapBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS).requiresTool().strength(3.0F, 3.0F).nonOpaque()));

    private static <T extends Block> T register(String name, T block) {
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            Motherlode.id(name),
            block,
            null,
            null,
            CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }

    private static <T extends DecorationBlock> T register(String name, T block) {
        return Motherlode.register(
            block,
            Motherlode.id(name),
            block,
            null,
            block,
            block
        );
    }

    public static void init() {
    }
}
