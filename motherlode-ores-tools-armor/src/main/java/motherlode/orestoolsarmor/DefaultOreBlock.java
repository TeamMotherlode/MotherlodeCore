package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.util.Processor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Random;
import java.util.function.Predicate;

public class DefaultOreBlock extends OreBlock implements Processor<Biome> {

    private final int minExperience;
    private final int maxExperience;
    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;
    private final GenerationStep.Feature feature;
    private final OreFeatureConfig.Target target;
    private final Predicate<Category> category;

    public DefaultOreBlock(int miningLevel) {
        this(miningLevel, false);
    }

    public static final Predicate<Category> OVERWORLD_CATEGORY = category -> category != Category.NETHER && category != Category.THEEND;
    public static final Predicate<Category> NETHER_CATEGORY = category -> category == Category.NETHER;

    public DefaultOreBlock(int miningLevel, boolean nether) {
        this(0, 0, 8, 1, 0, 50, nether, miningLevel);
    }

    public DefaultOreBlock(int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, boolean nether, int miningLevel) {

        this(minExperience, maxExperience, veinSize, veinsPerChunk, minY, maxY, nether? GenerationStep.Feature.UNDERGROUND_DECORATION : GenerationStep.Feature.UNDERGROUND_ORES, nether? OreFeatureConfig.Target.NETHERRACK : OreFeatureConfig.Target.NATURAL_STONE, nether? NETHER_CATEGORY : OVERWORLD_CATEGORY, miningLevel);
    }
    
    public DefaultOreBlock(int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, GenerationStep.Feature feature, OreFeatureConfig.Target target, Predicate<Category> category, int miningLevel) {
        super(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));

        this.minExperience = minExperience;
        this.maxExperience = maxExperience;

        this.veinSize = veinSize;
        this.veinsPerChunk = veinsPerChunk;
        this.minY = minY;
        this.maxY = maxY;

        this.feature = feature;
        this.target = target;
        this.category = category;
    }

    protected int getExperienceWhenMined(Random random) {
        if (maxExperience!=0) {
            return MathHelper.nextInt(random, minExperience, maxExperience);
        }
        return 0;
    }
    @Override
    public void accept(Biome biome) {

        if(category.test(biome.getCategory())) {
            biome.addFeature(feature, Feature.ORE.configure(
             new OreFeatureConfig(target, getDefaultState(), this.veinSize)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(
             new RangeDecoratorConfig(this.veinsPerChunk, this.minY, this.minY, this.maxY))));
        }
    }
}
