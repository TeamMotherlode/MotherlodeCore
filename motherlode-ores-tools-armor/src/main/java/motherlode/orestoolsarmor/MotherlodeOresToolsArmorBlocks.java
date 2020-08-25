package motherlode.orestoolsarmor;

import motherlode.base.CommonArtificeProcessors;
import motherlode.base.Motherlode;
import motherlode.base.api.ArtificeProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MotherlodeOresToolsArmorBlocks {

    public static final List<Pair<Identifier, ArtificeProcessor>> ARTIFICE_PROCESSORS = new ArrayList<>();
    private static final Item.Settings BLOCK_ITEM_SETTINGS = new Item.Settings().group(ItemGroup.MATERIALS);

    public static final DefaultOreBlock COPPER_ORE = register("copper_ore", new DefaultOreBlock(3, 7, 12, 3, 11, 64, false, 1));
    public static final DefaultOreBlock SILVER_ORE = register("silver_ore", new DefaultOreBlock(2));
    public static final DefaultOreBlock CHARITE_ORE = register("charite_ore", new DefaultOreBlock(3, true));
    public static final DefaultOreBlock ECHERITE_ORE = register("echerite_ore", new DefaultOreBlock( 4, true));
    public static final DefaultOreBlock TITANIUM_ORE = register("titanium_ore", new DefaultOreBlock(5));
    public static final DefaultOreBlock ADAMANTITE_ORE = register("adamantite_ore", new DefaultOreBlock(6));
    public static final DefaultOreBlock AMETHYST_ORE = register("amethyst_ore", new DefaultOreBlock(2));
    public static final DefaultOreBlock HOWLITE_ORE = register("howlite_ore", new DefaultOreBlock(2));
    public static final DefaultOreBlock RUBY_ORE = register("ruby_ore", new DefaultOreBlock(2));
    public static final DefaultOreBlock SAPPHIRE_ORE = register("sapphire_ore", new DefaultOreBlock(2));
    public static final DefaultOreBlock TOPAZ_ORE = register("topaz_ore", new DefaultOreBlock(2));
    public static final DefaultOreBlock ONYX_ORE = register("onyx_ore", new DefaultOreBlock(2));

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

    private static Block mineralBlock(int miningLevel) {
        return new Block(FabricBlockSettings.of(Material.STONE, MaterialColor.IRON).requiresTool().strength(5.0F, 6.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));
    }

    private static<T extends Block> T register(String name, T block) {

        Registry.register(Registry.BLOCK, Motherlode.id(MotherlodeOresToolsArmorMod.MODID, name), block);
        Registry.register(Registry.ITEM, Motherlode.id(MotherlodeOresToolsArmorMod.MODID, name), new BlockItem(block, BLOCK_ITEM_SETTINGS));
        ARTIFICE_PROCESSORS.add(new Pair<>(Motherlode.id(MotherlodeOresToolsArmorMod.MODID, name), CommonArtificeProcessors.DEFAULT_BLOCK));
        return block;
    }

    public static void init() {

     // Called to load the class
    }
}
