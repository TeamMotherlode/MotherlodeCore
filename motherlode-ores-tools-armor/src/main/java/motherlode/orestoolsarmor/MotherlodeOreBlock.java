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

public class MotherlodeOreBlock extends OreBlock implements Processor<Biome> {

    private final int minExperience;
    private final int maxExperience;
    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;
    private final Dimension dimension;

    public MotherlodeOreBlock(int miningLevel) {
        this(miningLevel, Dimension.OVERWORLD);
    }

    public MotherlodeOreBlock(int miningLevel, Dimension dimension) {
        this(0, 0, 8, 1, 0, 50, dimension, miningLevel);
    }
    
    public MotherlodeOreBlock(int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, Dimension dimension, int miningLevel) {
        super(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));

        this.minExperience = minExperience;
        this.maxExperience = maxExperience;

        this.veinSize = veinSize;
        this.veinsPerChunk = veinsPerChunk;
        this.minY = minY;
        this.maxY = maxY;

        this.dimension = dimension;
    }

    protected int getExperienceWhenMined(Random random) {
        if (maxExperience!=0) {
            return MathHelper.nextInt(random, minExperience, maxExperience);
        }
        return 0;
    }
    @Override
    public void accept(Biome biome) {

        if(dimension.getBiomeCategory().test(biome.getCategory())) {
            biome.addFeature(dimension.getGenerationStepFeature(), Feature.ORE.configure(
             new OreFeatureConfig(dimension.getTarget(), getDefaultState(), this.veinSize)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(
             new RangeDecoratorConfig(this.veinsPerChunk, this.minY, this.minY, this.maxY))));
        }
    }

    public enum Dimension {

        OVERWORLD(
                GenerationStep.Feature.UNDERGROUND_ORES,
                OreFeatureConfig.Target.NATURAL_STONE,
                category -> category != Category.NETHER && category != Category.THEEND),
        NETHER(
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                OreFeatureConfig.Target.NETHER_ORE_REPLACEABLES,
                category -> category == Category.NETHER),
        THE_END(
                GenerationStep.Feature.UNDERGROUND_ORES,
                OreFeatureConfig.Target.NATURAL_STONE, // There is no OreFeatureConfig.Target for ores in The End
                category -> category == Category.THEEND);

        private final GenerationStep.Feature feature;
        private final OreFeatureConfig.Target target;
        private final Predicate<Category> category;

        Dimension(GenerationStep.Feature feature, OreFeatureConfig.Target target, Predicate<Category> category) {

            this.feature = feature;
            this.target = target;
            this.category = category;
        }

        public GenerationStep.Feature getGenerationStepFeature() {

            return this.feature;
        }

        public OreFeatureConfig.Target getTarget() {

            return this.target;
        }

        public Predicate<Category> getBiomeCategory() {

            return this.category;
        }
    }
}
