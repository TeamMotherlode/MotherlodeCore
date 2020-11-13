package motherlode.copperdungeon;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.copperdungeon.block.CutterTrapBlock;
import motherlode.copperdungeon.block.ZapperTrapBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class MotherlodeCopperDungeonBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.REDSTONE);

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

    public static void init() {
    }
}
