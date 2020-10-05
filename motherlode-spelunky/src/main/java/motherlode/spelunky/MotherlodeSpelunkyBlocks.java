package motherlode.spelunky;

import motherlode.base.CommonAssets;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Registerable;
import motherlode.spelunky.block.PotAssets;
import motherlode.spelunky.block.PotBlock;
import motherlode.spelunky.block.RopeAssets;
import motherlode.spelunky.block.RopeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;

public class MotherlodeSpelunkyBlocks {

    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.DECORATIONS);

    public static final Block POT = register("pot", new PotBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.GLASS).strength(2.0F, 2.0F)),
      PotAssets.POT_BLOCK_STATE.andThen(PotAssets.POT_TEMPLATE).andThen(PotAssets.POT_ITEM_MODEL), null);

    public static final Block ROPE = register("rope", new RopeBlock(FabricBlockSettings.of(Material.PLANT)),
      RopeAssets.ITEM_MODELS.andThen(RopeAssets.ITEM_MODEL).andThen(RopeAssets.BLOCK_STATE).andThen(CommonAssets.BLOCK_ITEM), CommonData.DEFAULT_BLOCK_LOOT_TABLE);

    public static Block register(String name, Block block, AssetProcessor assets, DataProcessor data) {
        return Motherlode.register(
          Registerable.block(block, BLOCK_ITEM_SETTINGS),
          Motherlode.id(MotherlodeSpelunkyMod.MODID, name),
          block,
          null,
          assets,
          data
        );
    }

    public static void init() {
    }
}
