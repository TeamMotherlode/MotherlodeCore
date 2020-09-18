package motherlode.orestoolsarmor;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class MineralBlock extends Block implements DataProcessor {

    public MineralBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {

        Identifier ingot = Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath().replace("block", "ingot"));

        pack.addShapedRecipe(id, recipe -> recipe
            .pattern("***", "***", "***")
            .ingredientTag('*', ingot)
            .result(id, 1));

        pack.addShapelessRecipe(Motherlode.id(id.getNamespace(), ingot.getPath()), recipe -> recipe
            .ingredientTag(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .result(ingot, 9)
        );
    }
}
