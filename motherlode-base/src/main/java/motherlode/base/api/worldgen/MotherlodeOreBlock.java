package motherlode.base.api.worldgen;

import java.util.function.Function;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.IntRange;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import motherlode.base.Motherlode;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.assets.DataProcessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class MotherlodeOreBlock extends OreBlock implements DataProcessor {
    private static final IntRange NULL_INT_RANGE = IntRange.between(0, 0);

    private final int veinSize;
    private final Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> decorators;
    private final OreTarget target;
    private final String mineral;

    public MotherlodeOreBlock(int miningLevel, String mineral) {
        this(FeatureTargets.OVERWORLD, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, int miningLevel, String mineral) {
        this(target, 8, 10, 4, 64, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, int veinSize, int veinsPerChunk, int minY, int maxY, int miningLevel, String mineral) {
        this(target, NULL_INT_RANGE, veinSize, veinsPerChunk, minY, maxY, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, IntRange experienceRange, int veinSize, int veinsPerChunk, int minY, int maxY, int miningLevel, String mineral) {
        this(target, experienceRange, veinSize, f -> f.rangeOf(YOffset.fixed(minY), YOffset.fixed(maxY)), miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, int veinSize, Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> decorators, int miningLevel, String mineral) {
        this(target, NULL_INT_RANGE, veinSize, decorators, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, IntRange experienceRange, int veinSize, Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> decorators, int miningLevel, String mineral) {
        super(FabricBlockSettings.of(Material.STONE).strength(Math.min(3f, miningLevel), Math.min(3f, miningLevel + 1)).requiresTool().breakByTool(FabricToolTags.PICKAXES, miningLevel), experienceRange);

        this.veinSize = veinSize;
        this.decorators = decorators;

        this.target = target;

        this.mineral = mineral;
    }

    public int getVeinSize() {
        return this.veinSize;
    }

    public Function<ConfiguredFeature<OreFeatureConfig, ?>, ConfiguredFeature<?, ?>> getDecoratorsFunction() {
        return this.decorators;
    }

    public OreTarget getTarget() {
        return this.target;
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
}
