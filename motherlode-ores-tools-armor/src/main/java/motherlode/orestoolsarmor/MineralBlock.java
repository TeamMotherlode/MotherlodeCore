package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class MineralBlock extends Block implements DataProcessor {

    private final String mineral;

    public MineralBlock(Settings settings, String mineral) {
        super(settings);
        this.mineral = mineral;
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.BLOCK_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .accept(pack, id);

        Identifier mineral = Motherlode.id(CommonData.COMMON_NAMESPACE, this.mineral);

        pack.addShapedRecipe(id, recipe -> recipe
            .pattern("***", "***", "***")
            .ingredientTag('*', mineral)
            .result(id, 1));

        pack.addShapelessRecipe(Motherlode.id(id.getNamespace(), mineral.getPath()), recipe -> recipe
            .ingredientTag(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .result(mineral, 9)
        );
    }
}
