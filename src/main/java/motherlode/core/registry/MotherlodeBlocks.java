package motherlode.core.registry;

import com.swordglowsblue.artifice.api.util.Processor;
import motherlode.core.Motherlode;
import motherlode.core.api.BlockProperties;
import motherlode.core.block.*;
import motherlode.core.block.defaults.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import static motherlode.core.registry.MotherlodeBlockProperties.*;

@SuppressWarnings("unused")
public class MotherlodeBlocks {

    public static final Block COPPER_ORE = register("copper_ore", ORE(true), new DefaultOreBlock(3, 7, 12, 3, 11, 64, 1));
    public static final Block SILVER_ORE = register("silver_ore", ORE(true), new DefaultOreBlock(2));
    public static final Block CHARITE_ORE = register("charite_ore", ORE(false), new DefaultOreBlock(3));
    public static final Block ECHERITE_ORE = register("echerite_ore", ORE(true), new DefaultOreBlock(4));
    public static final Block TITANIUM_ORE = register("titanium_ore", ORE(true), new DefaultOreBlock(5));
    public static final Block ADAMANTITE_ORE = register("adamantite_ore", ORE(true), new DefaultOreBlock(6));
    public static final Block AMETHYST_ORE = register("amethyst_ore", ORE(false), new DefaultOreBlock(2));
    public static final Block HOWLITE_ORE = register("howlite_ore", ORE(false), new DefaultOreBlock(2));
    public static final Block RUBY_ORE = register("ruby_ore", ORE(false), new DefaultOreBlock(2));
    public static final Block SAPPHIRE_ORE = register("sapphire_ore", ORE(false), new DefaultOreBlock(2));
    public static final Block TOPAZ_ORE = register("topaz_ore", ORE(false), new DefaultOreBlock(2));
    public static final Block ONYX_ORE = register("onyx_ore", ORE(false), new DefaultOreBlock(2));

    public static final Block COPPER_BLOCK = register("copper_block", DEFAULT, BlockGenerator.mineral(1));
    public static final Block SILVER_BLOCK = register("silver_block", DEFAULT, BlockGenerator.mineral(2));
    public static final Block CHARITE_BLOCK = register("charite_block", DEFAULT, BlockGenerator.mineral(3));
    public static final Block ECHERITE_BLOCK = register("echerite_block", DEFAULT, BlockGenerator.mineral(4));
    public static final Block TITANIUM_BLOCK = register("titanium_block", DEFAULT, BlockGenerator.mineral(5));
    public static final Block ADAMANTITE_BLOCK = register("adamantite_block", DEFAULT, BlockGenerator.mineral(6));
    public static final Block AMETHYST_BLOCK = register("amethyst_block", DEFAULT, BlockGenerator.mineral(2));
    public static final Block HOWLITE_BLOCK = register("howlite_block", DEFAULT, BlockGenerator.mineral(2));
    public static final Block RUBY_BLOCK = register("ruby_block", DEFAULT, BlockGenerator.mineral(2));
    public static final Block SAPPHIRE_BLOCK = register("sapphire_block", DEFAULT, BlockGenerator.mineral(2));
    public static final Block TOPAZ_BLOCK = register("topaz_block", DEFAULT, BlockGenerator.mineral(2));
    public static final Block ONYX_BLOCK = register("onyx_block", DEFAULT, BlockGenerator.mineral(2));

    public static final Block MORTAR_BRICKS = register("mortar_bricks", PAINTABLE_WALL, new PaintableWallBlock(FabricBlockSettings.copy(Blocks.TERRACOTTA)));

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

    public static final Block POT = register("pot", MANUAL_CUTOUT(Motherlode.ITEMS), new PotBlock());
    public static final Block ROPE = register("rope", MANUAL_CUTOUT(Motherlode.ITEMS), new RopeBlock());
  
    public static final Block REDSTONE_TRANSMITTER = register("redstone_transmitter", MANUAL_MODEL, new RedstoneTransmitterBlock());

    public static final Block SLIGHTLY_ROCKY_DIRT = register("slightly_rocky_dirt", SHOVELABLE, BlockGenerator.rockyDirt());
    public static final Block ROCKY_DIRT = register("rocky_dirt", SHOVELABLE, BlockGenerator.rockyDirt());
    public static final Block VERY_ROCKY_DIRT = register("very_rocky_dirt", SHOVELABLE, BlockGenerator.rockyDirt());

    public static final Block DIRT_PATH = register("dirt_path", MANUAL_MODEL, new PathBlock(FabricBlockSettings.copy(Blocks.GRASS_PATH)), (block) ->
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            BlockPos pos = hit.getBlockPos();
            if(player.getStackInHand(hand).getItem() instanceof ShovelItem && world.getBlockState(pos).getBlock() == Blocks.DIRT && world.getBlockState(pos.up()).isAir() && hit.getSide() != Direction.DOWN) {
                world.setBlockState(pos, block.getDefaultState());
                world.playSound(pos.getX()+0.5, pos.getY() +0.5, pos.getZ()+0.5, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }));

    public static final Block SPROUTS = register("sprouts", PLANT(false), new DefaultPlantBlock(4, "sprouts_0", FabricBlockSettings.copy(Blocks.GRASS)));
    public static final Block DRACAENA = register("dracaena", PLANT(true), new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS)));
    public static final Block PHILODENDRON = register("philodendron", PLANT(true), new DefaultPlantBlock(10, FabricBlockSettings.copy(Blocks.GRASS)));

    public static final Block MOSS = register("moss", MANUAL_CUTOUT(Motherlode.BLOCKS), new MossBlock());

    public static final Block WATERPLANT = register("waterplant", MANUAL_CUTOUT_LOOT_TABLE, new WaterplantBlock(FabricBlockSettings.copy(Blocks.SEAGRASS)));
    public static final Block REEDS = register("reeds", REED, new ReedsBlock());
    public static final Block CATTAIL_REEDS = register("cattail_reeds", REED, new ReedsBlock());
    public static final Block DRY_REEDS = register("dry_reeds", REED, new ReedsBlock());

    public static final Block ACACIA_PLATFORM = register("acacia_platform", PLATFORM, BlockGenerator.platform());
    public static final Block BIRCH_PLATFORM = register("birch_platform", PLATFORM, BlockGenerator.platform());
    public static final Block DARK_OAK_PLATFORM = register("dark_oak_platform", PLATFORM, BlockGenerator.platform());
    public static final Block JUNGLE_PLATFORM = register("jungle_platform", PLATFORM, BlockGenerator.platform());
    public static final Block OAK_PLATFORM = register("oak_platform", PLATFORM, BlockGenerator.platform());
    public static final Block SPRUCE_PLATFORM = register("spruce_platform", PLATFORM, BlockGenerator.platform());

    public static void init() {
        // CALLED TO MAINTAIN REGISTRY ORDER
    }

    public static <T extends Block> T register(String id, BlockProperties properties, T block, Processor<Block> processor) {
        processor.accept(block);
        return register(id, properties, block);
    }

    public static <T extends Block> T register(String id, BlockProperties properties, T block) {
        T b = Registry.register(Registry.BLOCK, Motherlode.id(id), block);

        if (properties != null) {
            if (Motherlode.isClient)
                MotherlodeAssets.register(properties, id, b);

            if (properties.itemSettings != null)
                Registry.register(Registry.ITEM, Motherlode.id(id), new BlockItem(b, properties.itemSettings));

            if (properties.defaultLootTable)
                MotherlodeData.defaultLootTable.add(id);
        }
        return b;
    }

    public static class PathBlock extends GrassPathBlock {
        public PathBlock(Settings settings) {
            super(settings);
        }
    }
}
