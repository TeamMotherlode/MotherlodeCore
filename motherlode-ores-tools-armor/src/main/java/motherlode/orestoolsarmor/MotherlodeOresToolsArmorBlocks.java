package motherlode.orestoolsarmor;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import motherlode.base.Motherlode;
import motherlode.base.api.CommonAssets;
import motherlode.base.api.CommonData;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import motherlode.base.api.worldgen.FeatureTargets;
import motherlode.base.api.worldgen.MotherlodeOreBlock;

@SuppressWarnings("unused")
public class MotherlodeOresToolsArmorBlocks {
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.BUILDING_BLOCKS);

    // public static final MotherlodeOreBlock COPPER_ORE = register("copper_ore", new MotherlodeOreBlock(OreTargets.OVERWORLD, IntRange.between(3, 7), 10, 18, 16, 64, 1, "copper_ingot"));
    public static final MotherlodeOreBlock SILVER_ORE = register("silver_ore", new MotherlodeOreBlock(2, "silver_ingot"));
    public static final MotherlodeOreBlock CHARITE_ORE = register("charite_ore", new MotherlodeOreBlock(FeatureTargets.NETHER, 4, "charite_ingot"));
    public static final MotherlodeOreBlock ECHERITE_ORE = register("echerite_ore", new MotherlodeOreBlock(FeatureTargets.NETHER, 4, f -> f.spreadHorizontally().repeat(2).decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(3, 3, 125))).repeat(5), 5, "echerite_ingot"));
    public static final MotherlodeOreBlock TITANIUM_ORE = register("titanium_ore", new MotherlodeOreBlock(6, "titanium_ingot"));
    public static final MotherlodeOreBlock ADAMANTITE_ORE = register("adamantite_ore", new MotherlodeOreBlock(FeatureTargets.NETHER, 3, 1, 2, 32, 7, "adamantite_ingot"));
    public static final MotherlodeOreBlock AMETHYST_ORE = register("amethyst_ore", new MotherlodeOreBlock(FeatureTargets.OVERWORLD, 4, 6, 8, 32, 2, "amethyst"));
    public static final MotherlodeOreBlock HOWLITE_ORE = register("howlite_ore", new MotherlodeOreBlock(FeatureTargets.OVERWORLD, 4, 6, 8, 32, 2, "howlite"));
    public static final MotherlodeOreBlock RUBY_ORE = register("ruby_ore", new MotherlodeOreBlock(FeatureTargets.OVERWORLD, 4, 6, 8, 32, 2, "ruby"));
    public static final MotherlodeOreBlock SAPPHIRE_ORE = register("sapphire_ore", new MotherlodeOreBlock(FeatureTargets.OVERWORLD, 4, 6, 8, 32, 2, "sapphire"));
    public static final MotherlodeOreBlock TOPAZ_ORE = register("topaz_ore", new MotherlodeOreBlock(FeatureTargets.OVERWORLD, 4, 6, 8, 32, 2, "topaz"));
    public static final MotherlodeOreBlock ONYX_ORE = register("onyx_ore", new MotherlodeOreBlock(FeatureTargets.OVERWORLD, 4, 6, 8, 32, 2, "onyx"));

    // public static final Block COPPER_BLOCK = register("copper_block", mineralBlock(1, "copper_ingot"));
    public static final Block SILVER_BLOCK = register("silver_block", mineralBlock(2, "silver_ingot"));
    public static final Block CHARITE_BLOCK = register("charite_block", mineralBlock(3, "charite_ingot"));
    public static final Block ECHERITE_BLOCK = register("echerite_block", mineralBlock(4, "echerite_ingot"));
    public static final Block TITANIUM_BLOCK = register("titanium_block", mineralBlock(5, "titanium_ingot"));
    public static final Block ADAMANTITE_BLOCK = register("adamantite_block", mineralBlock(6, "adamantite_ingot"));
    public static final Block AMETHYST_BLOCK = register("amethyst_block", mineralBlock(2, "amethyst"));
    public static final Block HOWLITE_BLOCK = register("howlite_block", mineralBlock(2, "howlite"));
    public static final Block RUBY_BLOCK = register("ruby_block", mineralBlock(2, "ruby"));
    public static final Block SAPPHIRE_BLOCK = register("sapphire_block", mineralBlock(2, "sapphire"));
    public static final Block TOPAZ_BLOCK = register("topaz_block", mineralBlock(2, "topaz"));
    public static final Block ONYX_BLOCK = register("onyx_block", mineralBlock(2, "onyx"));

    private static Block mineralBlock(int miningLevel, String mineral) {
        return new MineralBlock(FabricBlockSettings.of(Material.STONE, MapColor.IRON).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel), mineral);
    }

    private static <T extends Block> T register(String name, T block) {
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            MotherlodeModule.id(name),
            block,
            CommonAssets.DEFAULT_BLOCK,
            block instanceof DataProcessor ? ((DataProcessor) block).andThen(CommonData.DEFAULT_BLOCK_LOOT_TABLE)
                : CommonData.DEFAULT_BLOCK_LOOT_TABLE
        );
    }

    private static <T extends MotherlodeOreBlock> T register(String name, T block) {
        Identifier id = MotherlodeModule.id(name);
        return Motherlode.register(
            Registerable.block(block, BLOCK_ITEM_SETTINGS),
            id,
            block,
            (Processor<MotherlodeOreBlock>) b -> Motherlode.getFeaturesManager().addOre(id, b),
            CommonAssets.DEFAULT_BLOCK,
            block.andThen(CommonData.DEFAULT_BLOCK_LOOT_TABLE)
        );
    }

    public static void init() {
        // Called to load the class
    }
}
