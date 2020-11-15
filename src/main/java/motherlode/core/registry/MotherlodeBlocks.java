package motherlode.core.registry;

import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import com.swordglowsblue.artifice.api.builder.assets.ModelBuilder;
import com.swordglowsblue.artifice.api.builder.data.LootTableBuilder;
import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.core.Motherlode;
import motherlode.core.block.*;
import motherlode.core.block.DefaultPlantBlock;
import motherlode.core.block.MotherlodeDoorBlock;
import motherlode.core.block.MotherlodeLadderBlock;
import motherlode.core.block.MotherlodePaneBlock;
import motherlode.core.block.DefaultDecorationBlock;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MotherlodeBlocks {
    public static final ArrayList<Block> defaultStateList = new ArrayList<>();
    public static final ArrayList<Block> defaultRotatableStateList = new ArrayList<>();
    public static final ArrayList<Block> defaultModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultPlantModelList = new ArrayList<>();

    public static final HashMap<Block, Function<String, Processor<BlockStateBuilder>>> customStateList = new HashMap<>();
    public static final HashMap<Block, Function<String, Processor<LootTableBuilder>>> customLootTableList = new HashMap<>();
    public static final HashMap<Block, ArrayList<Function<String, Pair<String, Processor<ModelBuilder>>>>> customBlockModelList = new HashMap<>();
    public static final HashMap<Block, Function<String, Processor<ModelBuilder>>> customItemModelList = new HashMap<>();


    public static final ArrayList<Block> thickCrossModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultItemModelList = new ArrayList<>();
    public static final Map<Block, Supplier<String>> flatItemModelList = new HashMap<>();
    public static final ArrayList<Block> defaultLootTableList = new ArrayList<>();
    public static final Map<StairsBlock, Boolean> usesStairModel = new LinkedHashMap<>();
    public static final Map<SlabBlock, Boolean> usesSlabModel = new LinkedHashMap<>();
    public static final ArrayList<Block> usesPillarModel = new ArrayList<>();
    public static final ArrayList<Block> usesPaintableModel = new ArrayList<>();
    public static final ArrayList<DefaultShovelableBlock> shovelableBlocks = new ArrayList<>();

    public static final ArrayList<Block> cutouts = new ArrayList<>();
    public static final ArrayList<Block> translucent = new ArrayList<>();
    public static final ArrayList<Block> grassColored = new ArrayList<>();
    public static final ArrayList<Block> foliageColored = new ArrayList<>();

    public static final Block COPPER_ORE = register("copper_ore", new DefaultOreBlock(true, 3, 7, 12, 3, 11, 64, 1));
    public static final Block SILVER_ORE = register("silver_ore", new DefaultOreBlock(true, 2));
    public static final Block CHARITE_ORE = register("charite_ore", new DefaultOreBlock(false, 3));
    public static final Block ECHERITE_ORE = register("echerite_ore", new DefaultOreBlock(true, 4));
    public static final Block TITANIUM_ORE = register("titanium_ore", new DefaultOreBlock(true, 5));
    public static final Block ADAMANTITE_ORE = register("adamantite_ore", new DefaultOreBlock(true, 6));
    public static final Block AMETHYST_ORE = register("amethyst_ore", new DefaultOreBlock(false, 2));
    public static final Block HOWLITE_ORE = register("howlite_ore", new DefaultOreBlock(false, 2));
    public static final Block RUBY_ORE = register("ruby_ore", new DefaultOreBlock(false, 2));
    public static final Block SAPPHIRE_ORE = register("sapphire_ore", new DefaultOreBlock(false, 2));
    public static final Block TOPAZ_ORE = register("topaz_ore", new DefaultOreBlock(false, 2));
    public static final Block ONYX_ORE = register("onyx_ore", new DefaultOreBlock(false, 2));

    public static final Block STEEL_BLOCK = register("steel_block", mineralBlock(4)); // VARIABLES MIGHT CHANGE
    public static final DefaultDecorationBlock STEEL_WALL = register("steel_wall", new DefaultDecorationBlock("steel_wall", FabricBlockSettings.of(Material.METAL).requiresTool().strength(4.0F, 5.0F))).registerAll();
    public static final DefaultDecorationBlock STEEL_TILES = register("steel_tiles", new DefaultDecorationBlock("steel_tiles", FabricBlockSettings.of(Material.METAL).requiresTool().strength(4.0F, 5.0F))).registerAll();

    public static final Block STEEL_LADDER = register("steel_ladder", new MotherlodeLadderBlock(AbstractBlock.Settings.of(Material.SUPPORTED).strength(0.8F).sounds(BlockSoundGroup.METAL).nonOpaque())); //TODO: Ladder sound?
    public static final Block STEEL_BARS = register("steel_bars", new MotherlodePaneBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.CLEAR).requiresTool().strength(6.0F, 7.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));
    public static final Block STEEL_PLATFORM = register("steel_platform", new PlatformBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.IRON).requiresTool().strength(4.0f,4.0f).sounds(BlockSoundGroup.METAL).nonOpaque()));
    public static final Block STEEL_PLATFORM_STAIRS = register("steel_platform_stairs", new PlatformStairsBlock(STEEL_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(STEEL_PLATFORM)));
    public static final Block STEEL_DOOR = register("steel_door", new MotherlodeDoorBlock(AbstractBlock.Settings.of(Material.METAL, MaterialColor.IRON).requiresTool().strength(6.0F).sounds(BlockSoundGroup.METAL).nonOpaque()));

    public static final Block OAK_PLATFORM = register("oak_platform", new PlatformBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(1.0f,1.0f).nonOpaque()));
    public static final Block OAK_PLATFORM_STAIRS = register("oak_platform_stairs", new PlatformStairsBlock(OAK_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(OAK_PLATFORM)));
    public static final Block SPRUCE_PLATFORM = register("spruce_platform", new PlatformBlock(AbstractBlock.Settings.copy(OAK_PLATFORM)));
    public static final Block SPRUCE_PLATFORM_STAIRS = register("spruce_platform_stairs", new PlatformStairsBlock(SPRUCE_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(SPRUCE_PLATFORM)));
    public static final Block BIRCH_PLATFORM = register("birch_platform", new PlatformBlock(AbstractBlock.Settings.copy(OAK_PLATFORM)));
    public static final Block BIRCH_PLATFORM_STAIRS = register("birch_platform_stairs", new PlatformStairsBlock(BIRCH_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(BIRCH_PLATFORM)));
    public static final Block JUNGLE_PLATFORM = register("jungle_platform", new PlatformBlock(AbstractBlock.Settings.copy(OAK_PLATFORM)));
    public static final Block JUNGLE_PLATFORM_STAIRS = register("jungle_platform_stairs", new PlatformStairsBlock(JUNGLE_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(JUNGLE_PLATFORM)));
    public static final Block ACACIA_PLATFORM = register("acacia_platform", new PlatformBlock(AbstractBlock.Settings.copy(OAK_PLATFORM)));
    public static final Block ACACIA_PLATFORM_STAIRS = register("acacia_platform_stairs", new PlatformStairsBlock(ACACIA_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(ACACIA_PLATFORM)));
    public static final Block DARK_OAK_PLATFORM = register("dark_oak_platform", new PlatformBlock(AbstractBlock.Settings.copy(OAK_PLATFORM)));
    public static final Block DARK_OAK_PLATFORM_STAIRS = register("dark_oak_platform_stairs", new PlatformStairsBlock(DARK_OAK_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(DARK_OAK_PLATFORM)));
    public static final Block CRIMSON_PLATFORM = register("crimson_platform", new PlatformBlock(AbstractBlock.Settings.copy(Blocks.WARPED_PLANKS).strength(1.0f, 1.0f).nonOpaque()));
    public static final Block CRIMSON_PLATFORM_STAIRS = register("crimson_platform_stairs", new PlatformStairsBlock(CRIMSON_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(CRIMSON_PLATFORM)));
    public static final Block WARPED_PLATFORM = register("warped_platform", new PlatformBlock(AbstractBlock.Settings.copy(CRIMSON_PLATFORM)));
    public static final Block WARPED_PLATFORM_STAIRS = register("warped_platform_stairs", new PlatformStairsBlock(WARPED_PLATFORM.getDefaultState(), AbstractBlock.Settings.copy(WARPED_PLATFORM)));




    public static final Block COPPER_BLOCK = register("copper_block", mineralBlock(1));
    public static final Block SILVER_BLOCK = register("silver_block", mineralBlock(2));
    public static final Block CHARITE_BLOCK = register("charite_block", mineralBlock(3));
    public static final Block ECHERITE_BLOCK = register("echerite_block", mineralBlock(4));
    public static final Block TITANIUM_BLOCK = register("titanium_block", mineralBlock(5));
    public static final Block ADAMANTITE_BLOCK = register("adamantite_block", mineralBlock(6));
    public static final Block AMETHYST_BLOCK = register("amethyst_block", mineralBlock(2));
    public static final Block HOWLITE_BLOCK = register("howlite_block", mineralBlock(2));
    public static final Block RUBY_BLOCK = register("ruby_block", mineralBlock(2));
    public static final Block SAPPHIRE_BLOCK = register("sapphire_block", mineralBlock(2));
    public static final Block TOPAZ_BLOCK = register("topaz_block", mineralBlock(2));
    public static final Block ONYX_BLOCK = register("onyx_block", mineralBlock(2));

    public static final Block MORTAR_BRICKS = register("mortar_bricks", new PaintableWallBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA)));

    public static final StoneBlocks LIMESTONE = StoneBlocks.newStone("limestone",false);
    public static final StoneBlocks GRAVESTONE = StoneBlocks.newStone("gravestone",true);
    public static final StoneBlocks JASPER = StoneBlocks.newStone("jasper",false);
    public static final StoneBlocks MARBLE = StoneBlocks.newStone("marble",false);
    public static final StoneBlocks SLATE = StoneBlocks.newStone("slate",false);

    public static final StoneBlocks BRICK = StoneBlocks.fromBlock("brick", Blocks.BRICKS);
    public static final StoneBlocks MAGMA = StoneBlocks.fromBlock("magma", Blocks.MAGMA_BLOCK);
    public static final StoneBlocks OBSIDIAN = StoneBlocks.fromBlock("obsidian", null);
    public static final StoneBlocks CRYING_OBSIDIAN = StoneBlocks.fromBlock("crying_obsidian", null);
    public static final StoneBlocks GOLD = StoneBlocks.fromBlock("gold", Blocks.GOLD_BLOCK);
    public static final StoneBlocks ICE = StoneBlocks.fromBlock("ice", null);

    public static final StoneBlocks STONE = StoneBlocks.fromStone("stone", Blocks.STONE_BRICKS, Blocks.SMOOTH_STONE);
    public static final StoneBlocks GRANTITE = StoneBlocks.fromStone("granite", null, Blocks.POLISHED_GRANITE);
    public static final StoneBlocks DIORITE = StoneBlocks.fromStone("diorite", null, Blocks.POLISHED_DIORITE);
    public static final StoneBlocks ANDESITE = StoneBlocks.fromStone("andesite", null, Blocks.POLISHED_ANDESITE);
    public static final StoneBlocks BLACK_STONE = StoneBlocks.fromStone("blackstone", null, Blocks.POLISHED_BLACKSTONE);
    public static final StoneBlocks BASALT = StoneBlocks.fromStone("basalt", null, Blocks.POLISHED_BASALT);
    public static final StoneBlocks SANDSTONE = StoneBlocks.fromStone("sandstone", null, Blocks.SMOOTH_SANDSTONE);

    public static final Block POT = register("pot", new PotBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.GLASS).strength(2.0F, 2.0F)), (BlockItem) null);
    public static final Item POT_ITEM = Registry.register(Registry.ITEM, Motherlode.id("pot"), new BlockItem(POT, new Item.Settings().group(Motherlode.ITEMS)));

    public static final Block ROPE_BLOCK = register("rope", new RopeBlock(AbstractBlock.Settings.of(Material.PLANT)), (BlockItem) null);
    public static final Item ROPE_ITEM = Registry.register(Registry.ITEM, Motherlode.id("rope"), new BlockItem(ROPE_BLOCK, new Item.Settings().group(Motherlode.ITEMS)));
  
    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", new RedstoneTransmitterBlock(false, false, true, true, AbstractBlock.Settings.of(Material.METAL).requiresTool().strength(3.0F, 3.0F)));

    public static final Block STEEL_GRATE = register("steel_grate", new GrateBlock(AbstractBlock.Settings.of(Material.METAL).requiresTool().dynamicBounds().nonOpaque().strength(3.0F, 2.5F)));

    public static final Block SLIGHTLY_ROCKY_DIRT = register("slightly_rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)));
    public static final Block ROCKY_DIRT = register("rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)));
    public static final Block VERY_ROCKY_DIRT = register("very_rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM)));

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

    public static final Block SPROUTS = register("sprouts", new DefaultPlantBlock(4, false, false, "sprouts_0", FabricBlockSettings.copy(Blocks.GRASS)));
    public static final Block DRACAENA = register("dracaena", new DefaultPlantBlock(10, true, true, FabricBlockSettings.copy(Blocks.GRASS)));
    public static final Block PHILODENDRON = register("philodendron", new DefaultPlantBlock(10, true, true, FabricBlockSettings.copy(Blocks.GRASS)));

    public static final Block MOSS = register("moss", new MossBlock(FabricBlockSettings.copy(Blocks.GRASS)));

    public static final Block WATERPLANT = register("waterplant", new WaterplantBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)));
    public static final Block REEDS = register("reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), (block) -> { flatItemModelList.put(block, () -> "reeds"); });
    public static final Block CATTAIL_REEDS = register("cattail_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), (block) -> { flatItemModelList.put(block, () -> "cattail_reeds"); });
    public static final Block DRY_REEDS = register("dry_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), (block) -> { flatItemModelList.put(block, () -> "dry_reeds"); });

    public static final Block TRAP_CUTTER = register("trap_cutter", new CutterTrapBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS).requiresTool().strength(3.0F, 3.0F).nonOpaque()));
    public static final Block TRAP_ZAPPER = register("trap_zapper", new ZapperTrapBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS).requiresTool().strength(3.0F, 3.0F).nonOpaque()));

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    private static Block mineralBlock(int miningLevel) {
        return new DefaultBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel)); 
    }
    
    static <T extends Block> T register(String name, T block, Item.Settings settings) {
        return register(name, block, new BlockItem(block, settings));
    }

    public static <T extends Block> T register(String name, T block) {
        return register(name, block, new Item.Settings().group(Motherlode.BLOCKS));
    }

    public static <T extends Block> T register(String name, T block, Processor<T> p) {
        p.accept(block);
        return register(name, block, new Item.Settings().group(Motherlode.BLOCKS));
    }

    static <T extends Block> T register(String name, T block, Function<T, BlockItem> itemFactory) {
        return register(name, block, itemFactory.apply(block));
    }

    static <T extends Block, J extends DefaultBlock> T register(String name, T block, BlockItem item) {
        T b = Registry.register(Registry.BLOCK, Motherlode.id(name), block);
        if (item != null) {
            MotherlodeItems.register(name, item);
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
