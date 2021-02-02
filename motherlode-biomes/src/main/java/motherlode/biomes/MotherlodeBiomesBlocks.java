package motherlode.biomes;

import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import motherlode.base.Motherlode;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import motherlode.base.api.assets.AssetProcessor;
import motherlode.base.api.assets.CommonAssets;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.assets.DataProcessor;
import motherlode.biomes.block.DefaultPlantBlock;
import motherlode.biomes.block.MossBlock;
import motherlode.biomes.block.ReedsBlock;
import motherlode.biomes.block.ShovelableBlock;
import motherlode.biomes.block.WaterplantBlock;

public class MotherlodeBiomesBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.DECORATIONS);
    private static final Processor<Block> CUTOUT_RENDER_LAYER = block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());

    private static final Processor<Block> PLANT = CUTOUT_RENDER_LAYER.andThen(block -> {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> BiomeColors.getGrassColor(Objects.requireNonNull(world), pos), block);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getSpruceColor(), block);
    });

    public static final Block SLIGHTLY_ROCKY_DIRT = register("slightly_rocky_dirt", new ShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)), CommonAssets.DEFAULT_BLOCK);
    public static final Block ROCKY_DIRT = register("rocky_dirt", new ShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)), CommonAssets.DEFAULT_BLOCK);
    public static final Block VERY_ROCKY_DIRT = register("very_rocky_dirt", new ShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)), CommonAssets.DEFAULT_BLOCK);

    public static final Block SPROUTS = register("sprouts", new DefaultPlantBlock(4, FabricBlockSettings.copy(Blocks.GRASS)), flatItemModel("sprouts_0"), PLANT);
    public static final Block DRACAENA = register("dracaena", new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS)), CommonAssets.FLAT_BLOCK_ITEM_MODEL, PLANT);
    public static final Block PHILODENDRON = register("philodendron", new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS)), CommonAssets.FLAT_BLOCK_ITEM_MODEL, PLANT);

    public static final Block MOSS = register("moss", new MossBlock(FabricBlockSettings.copy(Blocks.GRASS)), CommonAssets.BLOCK_ITEM);

    public static final Block WATERPLANT = register("waterplant", new WaterplantBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), null, CUTOUT_RENDER_LAYER);
    public static final Block REEDS = register("reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), CommonAssets.FLAT_BLOCK_ITEM_MODEL, CUTOUT_RENDER_LAYER);
    public static final Block CATTAIL_REEDS = register("cattail_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), CommonAssets.FLAT_BLOCK_ITEM_MODEL, CUTOUT_RENDER_LAYER);
    public static final Block DRY_REEDS = register("dry_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), CommonAssets.FLAT_BLOCK_ITEM_MODEL, CUTOUT_RENDER_LAYER);

    private static Block register(String name, Block block, AssetProcessor assets) {
        return register(name, block, assets, CommonData.DEFAULT_BLOCK_LOOT_TABLE);
    }

    private static Block register(String name, Block block, AssetProcessor assets, DataProcessor data) {
        return register(name, block, assets, data, null);
    }

    private static Block register(String name, Block block, AssetProcessor assets, Processor<Block> p) {
        return register(name, block, assets, CommonData.DEFAULT_BLOCK_LOOT_TABLE, p);
    }

    private static Block register(String name, Block block, AssetProcessor assets, DataProcessor data, Processor<Block> p) {
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            MotherlodeModule.id(name),
            block,
            p,
            assets,
            data
        );
    }

    public static AssetProcessor flatItemModel(String textureName) {
        return (pack, id) -> pack.addItemModel(id, state -> state
            .parent(new Identifier("item/generated"))
            .texture("layer0", new Identifier(id.getNamespace(), "block/" + textureName))
        );
    }

    public static void init() {
        // Called to load the class
    }
}
