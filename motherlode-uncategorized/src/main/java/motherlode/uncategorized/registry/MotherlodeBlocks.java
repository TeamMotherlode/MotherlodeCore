package motherlode.uncategorized.registry;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import motherlode.uncategorized.MotherlodeUncategorized;
import motherlode.uncategorized.block.*;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MotherlodeBlocks {
    public static final ArrayList<Block> defaultStateList = new ArrayList<>();
    public static final ArrayList<Block> defaultRotatableStateList = new ArrayList<>();
    public static final ArrayList<Block> defaultModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultPlantModelList = new ArrayList<>();
    public static final ArrayList<Block> thickCrossModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultItemModelList = new ArrayList<>();
    public static final Map<Block, Supplier<String>> flatItemModelList = new HashMap<>();
    public static final ArrayList<Block> defaultLootTableList = new ArrayList<>();
    public static final ArrayList<Block> usesPaintableModel = new ArrayList<>();
    public static final ArrayList<DefaultShovelableBlock> shovelableBlocks = new ArrayList<>();

    public static final ArrayList<Block> cutouts = new ArrayList<>();
    public static final ArrayList<Block> grassColored = new ArrayList<>();
    public static final ArrayList<Block> foliageColored = new ArrayList<>();

    public static final Block MORTAR_BRICKS = register("mortar_bricks", new PaintableWallBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA)));

    public static final Block POT = register("pot", new PotBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.GLASS).strength(2.0F, 2.0F)), (BlockItem) null);
    public static final Item POT_ITEM = Registry.register(Registry.ITEM, Motherlode.id("pot"), new BlockItem(POT, new Item.Settings().group(MotherlodeUncategorized.ITEMS)));

    public static final Block ROPE_BLOCK = register("rope", new RopeBlock(AbstractBlock.Settings.of(Material.PLANT)), (BlockItem) null);
    public static final Item ROPE_ITEM = Registry.register(Registry.ITEM, Motherlode.id("rope"), new BlockItem(ROPE_BLOCK, new Item.Settings().group(MotherlodeUncategorized.ITEMS)));

    public static final Block DIRT_PATH = register("dirt_path", new PathBlock(FabricBlockSettings.copy(Blocks.GRASS_PATH)), (block) -> {
        defaultStateList.add(block);
        defaultItemModelList.add(block);

        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            BlockPos pos = hit.getBlockPos();
            if(player.getStackInHand(hand).getItem() instanceof ShovelItem && world.getBlockState(pos).getBlock() == Blocks.DIRT && world.getBlockState(pos.up()).isAir() && hit.getSide() != Direction.DOWN) {
                world.setBlockState(pos, block.getDefaultState());
                world.playSound(pos.getX()+0.5, pos.getY() +0.5, pos.getZ()+0.5, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    });
    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    public static <T extends Block> T register(String name, T block) {
        return register(name, block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS));
    }

    public static <T extends Block> T register(String name, T block, ArtificeProcessor p) {
        return register(name, block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS));
    }

    public static <T extends Block> T register(String name, T block, Processor<T> p) {
        p.accept(block);
        return register(name, block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS));
    }

    static <T extends Block> T register(String name, T block, Function<T, BlockItem> itemFactory) {
        return register(name, block, itemFactory.apply(block));
    }

    static <T extends Block, J extends DefaultBlock> T register(String name, T block, BlockItem item) {
        T b = Registry.register(Registry.BLOCK, Motherlode.id(name), block);
        if (item != null) {
            Registry.register(Registry.ITEM, Motherlode.id(name), new BlockItem(block, new Item.Settings().group(MotherlodeUncategorized.BLOCKS)));
        }
        if (block instanceof DefaultBlock) {
            DefaultBlock defaultBlock = (DefaultBlock)block;
            if (defaultBlock.hasDefaultState()){
                defaultStateList.add(defaultBlock);
            }
            if (defaultBlock.hasDefaultModel()){
                defaultModelList.add(defaultBlock);
            }
            if (defaultBlock.hasDefaultItemModel()) {
                defaultItemModelList.add(defaultBlock);
            }
            if (defaultBlock.hasDefaultLootTable()) {
                defaultLootTableList.add(defaultBlock);
            }
        }
        return b;
    }

    public static class PathBlock extends GrassPathBlock {
        public PathBlock(Settings settings) {
            super(settings);
        }
    }
}