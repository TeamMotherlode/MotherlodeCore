package motherlode.enderinvasion;

import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.woodtype.WoodType;
import motherlode.base.api.worldgen.DefaultSaplingGenerator;
import motherlode.base.mixin.BlocksAccessor;
import motherlode.enderinvasion.block.EnderInvasionPlantBlock;

public class MotherlodeEnderInvasionBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);

    public static final Block CORRUPTED_DIRT = register("corrupted_dirt", new Block(FabricBlockSettings.copy(Blocks.DIRT)), CommonAssets.DEFAULT_BLOCK);
    public static final Block CORRUPTED_GRASS = register("corrupted_grass", new EnderInvasionPlantBlock(FabricBlockSettings.copy(Blocks.GRASS)),
        (pack, id) -> CommonAssets.PLANT_FUNCTION.apply(id).accept(pack, Motherlode.id(id.getNamespace(), "corrupted_grass_02")));
    public static final Block WITHERED_LOG = register("withered_log", BlocksAccessor.callCreateLogBlock(MapColor.GRAY, MapColor.BLACK), CommonAssets.DEFAULT_ITEM_MODEL);
    public static final Block END_FOAM = register("end_foam", new Block(FabricBlockSettings.copy(Blocks.SLIME_BLOCK)), CommonAssets.DEFAULT_BLOCK);
    public static final Block END_CAP = register("end_cap", new EnderInvasionPlantBlock(FabricBlockSettings.copy(Blocks.BROWN_MUSHROOM), 6), CommonAssets.PLANT);

    public static final WoodType WITHERED_WOOD_TYPE = new WoodType(MotherlodeModule.id("withered"), MapColor.GRAY, MapColor.BLACK, MapColor.PURPLE, (log, leaves) ->
        new DefaultSaplingGenerator(MotherlodeModule.id("withered_tree"), ConfiguredFeatures.OAK), ((variant, vanilla) -> variant == WoodType.Variant.LEAVES ? Optional.empty() : vanilla.apply(variant)));

    public static final Block WITHERED_OAK_LEAVES = register("withered_oak_leaves", createLeavesBlock(), CommonAssets.DEFAULT_BLOCK);
    public static final Block WITHERED_DARK_OAK_LEAVES = register("withered_dark_oak_leaves", createLeavesBlock(), CommonAssets.DEFAULT_BLOCK);
    public static final Block WITHERED_BIRCH_LEAVES = register("withered_birch_leaves", createLeavesBlock(), CommonAssets.DEFAULT_BLOCK);
    public static final Block WITHERED_SPRUCE_LEAVES = register("withered_spruce_leaves", createLeavesBlock(), CommonAssets.DEFAULT_BLOCK);
    public static final Block WITHERED_JUNGLE_LEAVES = register("withered_jungle_leaves", createLeavesBlock(), CommonAssets.DEFAULT_BLOCK);
    public static final Block WITHERED_ACACIA_LEAVES = register("withered_acacia_leaves", createLeavesBlock(), CommonAssets.DEFAULT_BLOCK);

    private static <T extends Block> T register(String name, T block, AssetProcessor assets) {
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            MotherlodeModule.id(name),
            block,
            assets,
            CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }

    public static void init() {
    }

    private static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(FabricBlockSettings.of(Material.LEAVES, MapColor.PURPLE).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksAccessor::canSpawnOnLeaves).suffocates(((state, world, pos) -> false)).blockVision(((state, world, pos) -> false)));
    }
}
