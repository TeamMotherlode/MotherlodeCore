package motherlode.core.registry;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.core.Motherlode;
import motherlode.core.api.ArtificeProperties;
import motherlode.core.block.*;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public enum MotherlodeBlocks {
    COPPER_ORE("copper_ore", new DefaultOreBlock(true, 3, 7, 12, 3, 11, 64, 1)),
    SILVER_ORE ("silver_ore", new DefaultOreBlock(true, 2)),
    CHARITE_ORE("charite_ore", new DefaultOreBlock(false, 3)),
    ECHERITE_ORE("echerite_ore", new DefaultOreBlock(true, 4)),
    TITANIUM_ORE("titanium_ore", new DefaultOreBlock(true, 5)),
    ADAMANTITE_ORE("adamantite_ore", new DefaultOreBlock(true, 6)),
    AMETHYST_ORE("amethyst_ore", new DefaultOreBlock(false, 2)),
    HOWLITE_ORE("howlite_ore", new DefaultOreBlock(false, 2)),
    RUBY_ORE("ruby_ore", new DefaultOreBlock(false, 2)),
    SAPPHIRE_ORE("sapphire_ore", new DefaultOreBlock(false, 2)),
    TOPAZ_ORE("topaz_ore", new DefaultOreBlock(false, 2)),
    ONYX_ORE("onyx_ore", new DefaultOreBlock(false, 2)),
    
    COPPER_BLOCK("copper_block", mineralBlock(1)),
    SILVER_BLOCK("silver_block", mineralBlock(2)),
    CHARITE_BLOCK("charite_block", mineralBlock(3)),
    ECHERITE_BLOCK("echerite_block", mineralBlock(4)),
    TITANIUM_BLOCK("titanium_block", mineralBlock(5)),
    ADAMANTITE_BLOCK("adamantite_block", mineralBlock(6)),
    AMETHYST_BLOCK("amethyst_block", mineralBlock(2)),
    HOWLITE_BLOCK("howlite_block", mineralBlock(2)),
    RUBY_BLOCK("ruby_block", mineralBlock(2)),
    SAPPHIRE_BLOCK("sapphire_block", mineralBlock(2)),
    TOPAZ_BLOCK("topaz_block", mineralBlock(2)),
    ONYX_BLOCK("onyx_block", mineralBlock(2)),

    MORTAR_BRICKS("mortar_bricks", PaintableWallBlock::new, FabricBlockSettings.copy(Blocks.TERRACOTTA)),
    
    POT("pot", PotBlock::new, FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.GLASS).strength(2.0F, 2.0F), Motherlode.ITEMS),
    ROPE("rope", RopeBlock::new, AbstractBlock.Settings.of(Material.PLANT), Motherlode.ITEMS),

    SLIGHTLY_ROCKY_DIRT("slightly_rocky_dirt", new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM))),
    ROCKY_DIRT("rocky_dirt",  new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM))),
    VERY_ROCKY_DIRT("very_rocky_dirt",  new DefaultShovelableBlock(false, FabricBlockSettings.copy(Blocks.COARSE_DIRT).sounds(BlockSoundGroup.NYLIUM))),
    
     DIRT_PATH("dirt_path", new PathBlock(FabricBlockSettings.copy(Blocks.GRASS_PATH)), block -> {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            BlockPos pos = hit.getBlockPos();
            if(player.getStackInHand(hand).getItem() instanceof ShovelItem && world.getBlockState(pos).getBlock() == Blocks.DIRT && world.getBlockState(pos.up()).isAir() && hit.getSide() != Direction.DOWN) {
                world.setBlockState(pos, block.getDefaultState());
                world.playSound(pos.getX()+0.5, pos.getY() +0.5, pos.getZ()+0.5, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }),
    
    SPROUTS("sprouts", new DefaultPlantBlock(4, false, false, "sprouts_0", FabricBlockSettings.copy(Blocks.GRASS))),
    DRACAENA("dracaena", new DefaultPlantBlock(10, true, true, FabricBlockSettings.copy(Blocks.GRASS))),
    PHILODENDRON("philodendron", new DefaultPlantBlock(10, true, true, FabricBlockSettings.copy(Blocks.GRASS))),

    MOSS("moss", new MossBlock(FabricBlockSettings.copy(Blocks.GRASS))),

    WATERPLANT("waterplant", new WaterplantBlock(FabricBlockSettings.copy(Blocks.SEAGRASS))),
    REEDS("reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), block -> { MotherlodeAssets.flatItemModelList.put(block, () -> "reeds"); }),
    CATTAIL_REEDS("cattail_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), block -> { MotherlodeAssets.flatItemModelList.put(block, () -> "cattail_reeds"); }),
    DRY_REEDS("dry_reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)), block -> { MotherlodeAssets.flatItemModelList.put(block, () -> "dry_reeds"); });

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

    
    public static final ArrayList<Block> defaultStateList = new ArrayList<>();
    public static final ArrayList<Block> defaultRotatableStateList = new ArrayList<>();
    public static final ArrayList<Block> defaultModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultPlantModelList = new ArrayList<>();
    public static final ArrayList<Block> thickCrossModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultItemModelList = new ArrayList<>();
    public static final ArrayList<Block> defaultLootTableList = new ArrayList<>();
    public static final Map<StairsBlock, Boolean> usesStairModel = new LinkedHashMap<>();
    public static final Map<SlabBlock, Boolean> usesSlabModel = new LinkedHashMap<>();
    public static final ArrayList<Block> usesPillarModel = new ArrayList<>();
    public static final ArrayList<Block> usesPaintableModel = new ArrayList<>();
    public static final ArrayList<DefaultShovelableBlock> shovelableBlocks = new ArrayList<>();

    public static final ArrayList<Block> cutouts = new ArrayList<>();
    public static final ArrayList<Block> grassColored = new ArrayList<>();
    public static final ArrayList<Block> foliageColored = new ArrayList<>();
    
    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }
    
    private final Block block;
    private final BlockItem item;

    private static Block mineralBlock(int miningLevel) {
        return new DefaultBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.IRON).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));
    }

    MotherlodeBlocks(String name, Block block, Item.Settings settings) {
        this(name, block, new BlockItem(block, settings));
    }

    MotherlodeBlocks(String name, Function<AbstractBlock.Settings, Block> blockfactory, AbstractBlock.Settings settings, ItemGroup group) {
        this(name, blockfactory.apply(settings), new Item.Settings().group(group));
    }

    MotherlodeBlocks(String name, Function<AbstractBlock.Settings, Block> blockfactory, AbstractBlock.Settings settings) {
        this(name, blockfactory.apply(settings), new Item.Settings().group(Motherlode.BLOCKS));
    }
    
    MotherlodeBlocks(String name, Block block) {
        this(name, block, new Item.Settings().group(Motherlode.BLOCKS));
    }

    MotherlodeBlocks(String name, Block block, Processor<Block> p) {
        this(name, p.process(block), new Item.Settings().group(Motherlode.BLOCKS));
    }

    MotherlodeBlocks(String name, Block block, Function<Block, BlockItem> itemFactory) {
        this(name, block, itemFactory.apply(block));
    }

    MotherlodeBlocks(String name, Block block, BlockItem item) {
        
        this.block = Registry.register(Registry.BLOCK, Motherlode.id(name), block);
        
        if (item != null) {
            this.item = MotherlodeItems.register(name, item);
        }
        else this.item = null;
        
        if (block instanceof ArtificeProperties) {
            
           addToArtificeLists((ArtificeProperties) block, block);
        }
    }
    public Block get() {

        return this.block;
    }
    public BlockItem getItem() {

        return this.item;
    }
    public static void addToArtificeLists(ArtificeProperties properties, Block block) {

        if (properties.hasDefaultState()){
            defaultStateList.add(block);
        }
        if (properties.hasDefaultModel()){
            defaultModelList.add(block);
        }
        if (properties.hasDefaultItemModel()) {
            defaultItemModelList.add(block);
        }
        if (properties.hasDefaultLootTable()) {
            defaultLootTableList.add(block);
        }
    }

    public static class PathBlock extends GrassPathBlock implements ArtificeProperties {
        public PathBlock(Settings settings) {
            super(settings);
        }

        @Override
        public boolean hasDefaultState() { return true; }
        @Override
        public boolean hasDefaultModel() { return false; }
        @Override
        public boolean hasDefaultItemModel() {  return true; }
        @Override
        public boolean hasDefaultLootTable() { return false; }
        @Override
        public Block getBlockInstance() { return this; }
    }
}
