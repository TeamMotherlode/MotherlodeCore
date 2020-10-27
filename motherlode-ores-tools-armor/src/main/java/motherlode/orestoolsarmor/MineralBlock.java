package motherlode.orestoolsarmor;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

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

        Identifier mineral = Motherlode.id(id.getNamespace(), this.mineral);

        pack.addShapedRecipe(id, recipe -> recipe
            .pattern("***", "***", "***")
            .ingredientTag('*', Motherlode.id(CommonData.COMMON_NAMESPACE, mineral.getPath()))
            .result(id, 1));

        pack.addShapelessRecipe(Motherlode.id(id.getNamespace(), mineral.getPath() + "_from_block"), recipe -> recipe
            .ingredientTag(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .result(mineral, 9)
        );
    }
}
