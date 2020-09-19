package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MotherlodeOreBlock extends OreBlock implements Consumer<Biome>, DataProcessor {

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
    public void accept(Biome biome) {
        if(dimension.getBiomeCategory().test(biome.getCategory())) {
            biome.addFeature(dimension.getGenerationStepFeature(), Feature.ORE.configure(
             new OreFeatureConfig(dimension.getTarget(), getDefaultState(), this.veinSize)).createDecoratedFeature(Decorator.COUNT_RANGE.configure(
             new RangeDecoratorConfig(this.veinsPerChunk, this.minY, this.minY, this.maxY))));
        }
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
