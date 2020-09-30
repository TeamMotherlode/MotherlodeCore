package motherlode.spelunky;

import motherlode.base.Motherlode;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.Registerable;
import motherlode.spelunky.block.PotAssets;
import motherlode.spelunky.block.PotBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;

public class MotherlodeSpelunkyBlocks {

    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.DECORATIONS);

    public static final Block POT = register("pot", new PotBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.GLASS).strength(2.0F, 2.0F)),
      PotAssets.POT_BLOCK_STATE.andThen(PotAssets.POT_ITEM_MODEL));

    public static Block register(String name, Block block, AssetProcessor assets) {
        return Motherlode.register(
          Registerable.block(block, BLOCK_ITEM_SETTINGS),
          Motherlode.id(MotherlodeSpelunkyMod.MODID, name),
          block,
          null,
          assets,
          null
        );
    }

    public static void init() {
    }
}
