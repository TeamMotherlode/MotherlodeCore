package motherlode.orestoolsarmor;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.Registerable;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class MotherlodeOreBlock extends OreBlock implements Registerable<ConfiguredFeature<?, ?>>, DataProcessor {
    private final int minExperience;
    private final int maxExperience;
    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;
    private final Target target;
    private final String mineral;

    public MotherlodeOreBlock(int miningLevel, String mineral) {
        this(miningLevel, Target.OVERWORLD, mineral);
    }

    public MotherlodeOreBlock(int miningLevel, Target target, String mineral) {
        this(0, 0, 8, 1, 0, 50, target, miningLevel, mineral);
    }

    public MotherlodeOreBlock(int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, Target target, int miningLevel, String mineral) {
        super(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel));

        this.minExperience = minExperience;
        this.maxExperience = maxExperience;

        this.veinSize = veinSize;
        this.veinsPerChunk = veinsPerChunk;
        this.minY = minY;
        this.maxY = maxY;

        this.target = target;

        this.mineral = mineral;
    }

    protected int getExperienceWhenMined(Random random) {
        if (maxExperience != 0) {
            return MathHelper.nextInt(random, minExperience, maxExperience);
        }
        return 0;
    }

    public Target getDimension() {
        return this.target;
    }

    @Override
    public void register(Identifier id) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, Feature.ORE.configure(
            new OreFeatureConfig(target.getRuleTest(), getDefaultState(), this.veinSize)).decorate(Decorator.RANGE.configure(
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

    @SuppressWarnings("deprecation")
    public enum Target {
        OVERWORLD(
            Motherlode.id(MotherlodeModule.MODID, "overworld_ores"),
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Feature.UNDERGROUND_ORES,
            OreFeatureConfig.Rules.BASE_STONE_OVERWORLD),
        NETHER(
            Motherlode.id(MotherlodeModule.MODID, "nether_ores"),
            BiomeSelectors.foundInTheNether(),
            GenerationStep.Feature.UNDERGROUND_DECORATION,
            OreFeatureConfig.Rules.BASE_STONE_NETHER),
        THE_END(
            Motherlode.id(MotherlodeModule.MODID, "the_end_ores"),
            BiomeSelectors.foundInTheEnd(),
            GenerationStep.Feature.UNDERGROUND_ORES,
            new BlockMatchRuleTest(Blocks.END_STONE));

        private final Identifier id;
        private final Predicate<BiomeSelectionContext> biomeSelector;
        private final GenerationStep.Feature feature;
        private final RuleTest ruleTest;

        Target(Identifier id, Predicate<BiomeSelectionContext> biomeSelector, GenerationStep.Feature feature, RuleTest ruleTest) {
            this.id = id;
            this.biomeSelector = biomeSelector;
            this.feature = feature;
            this.ruleTest = ruleTest;
        }

        public RuleTest getRuleTest() {
            return this.ruleTest;
        }

        public void addOres(List<Identifier> blocks) {
            BiomeModifications.create(this.id)
                .add(ModificationPhase.ADDITIONS, this.biomeSelector, context -> {
                    for (Identifier id : blocks) {
                        context.getGenerationSettings().addFeature(this.feature, RegistryKey.of(
                            Registry.CONFIGURED_FEATURE_WORLDGEN, id)
                        );
                    }
                });
        }
    }
}
