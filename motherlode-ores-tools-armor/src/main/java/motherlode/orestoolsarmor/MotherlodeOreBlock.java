package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import java.util.Random;
import java.util.function.Consumer;

public class MotherlodeOreBlock extends OreBlock implements Consumer<GenerationSettings.Builder>, DataProcessor {

    private final int minExperience;
    private final int maxExperience;
    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;
    private final Dimension dimension;
    private final String mineral;

    public MotherlodeOreBlock(int miningLevel, String mineral) {
        this(miningLevel, Dimension.OVERWORLD, mineral);
    }

    public MotherlodeOreBlock(int miningLevel, Dimension dimension, String mineral) {
        this(0, 0, 8, 1, 0, 50, dimension, miningLevel, mineral);
    }
    
    public MotherlodeOreBlock(int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, Dimension dimension, int miningLevel, String mineral) {
        super(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));

        this.minExperience = minExperience;
        this.maxExperience = maxExperience;

        this.veinSize = veinSize;
        this.veinsPerChunk = veinsPerChunk;
        this.minY = minY;
        this.maxY = maxY;

        this.dimension = dimension;

        this.mineral = mineral;
    }

    protected int getExperienceWhenMined(Random random) {
        if (maxExperience!=0) {
            return MathHelper.nextInt(random, minExperience, maxExperience);
        }
        return 0;
    }
    @Override
    public void accept(GenerationSettings.Builder builder) {
        builder.feature(dimension.getGenerationStepFeature(), Feature.ORE.configure(
             new OreFeatureConfig(dimension.getTarget(), getDefaultState(), this.veinSize)).decorate(Decorator.RANGE.configure(
             new RangeDecoratorConfig(this.minY, 0, this.maxY))).repeat(this.veinsPerChunk).spreadHorizontally());
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        Identifier commonId = Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath());

        CommonData.BLOCK_TAG.apply(commonId).accept(pack, id);

        Identifier mineral = Motherlode.id(id.getNamespace(), this.mineral);

        pack.addSmeltingRecipe(Motherlode.id(id.getNamespace(), mineral.getPath() + "_smelting"), recipe -> recipe
                .ingredientTag(commonId)
                .result(mineral)
                .experience(1)
                .cookingTime(200));

        pack.addBlastingRecipe(Motherlode.id(id.getNamespace(), mineral.getPath() + "_blasting"), recipe -> recipe
                .ingredientTag(commonId)
                .result(mineral)
                .experience(1)
                .cookingTime(100));
    }

    public enum Dimension {

        OVERWORLD(
                GenerationStep.Feature.UNDERGROUND_ORES,
                OreFeatureConfig.Rules.BASE_STONE_OVERWORLD),
        NETHER(
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                OreFeatureConfig.Rules.BASE_STONE_NETHER),
        THE_END(
                GenerationStep.Feature.UNDERGROUND_ORES,
                new BlockMatchRuleTest(Blocks.END_STONE));

        private final GenerationStep.Feature feature;
        private final RuleTest target;

        Dimension(GenerationStep.Feature feature, RuleTest target) {

            this.feature = feature;
            this.target = target;
        }

        public GenerationStep.Feature getGenerationStepFeature() {

            return this.feature;
        }

        public RuleTest getTarget() {

            return this.target;
        }
    }
}
