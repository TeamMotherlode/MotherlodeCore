package motherlode.orestoolsarmor;

import java.util.Random;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.OreTarget;
import motherlode.base.api.OreTargets;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class MotherlodeOreBlock extends OreBlock implements DataProcessor {
    private final int minExperience;
    private final int maxExperience;
    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;
    private final OreTarget target;
    private final String mineral;

    public MotherlodeOreBlock(int miningLevel, String mineral) {
        this(OreTargets.OVERWORLD, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, int miningLevel, String mineral) {
        this(target, 8, 10, 4, 64, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, int veinSize, int veinsPerChunk, int minY, int maxY, int miningLevel, String mineral) {
        this(target, 0, 0, veinSize, veinsPerChunk, minY, maxY, miningLevel, mineral);
    }

    public MotherlodeOreBlock(OreTarget target, int minExperience, int maxExperience, int veinSize, int veinsPerChunk, int minY, int maxY, int miningLevel, String mineral) {
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
        if (maxExperience != 0)
            return MathHelper.nextInt(random, minExperience, maxExperience);

        return 0;
    }

    public void addOre(Identifier id) {
        Motherlode.addOre(id, this.target, this.getDefaultState(), this.veinSize, this.veinsPerChunk, this.minY, this.maxY);
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
