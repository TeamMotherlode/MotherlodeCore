package motherlode.materials;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import motherlode.base.Motherlode;
import motherlode.base.api.assets.CommonData;
import motherlode.base.api.assets.DataProcessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class MineralBlock extends Block implements DataProcessor {
    private final String mineral;

    public MineralBlock(Settings settings, String mineral) {
        super(settings);
        this.mineral = mineral;
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.BLOCK_TAG.apply(new Identifier(CommonData.COMMON_NAMESPACE, id.getPath()))
            .accept(pack, id);

        Identifier mineral = Motherlode.id(id, name -> this.mineral);

        pack.addShapedRecipe(id, recipe -> recipe
            .pattern("***", "***", "***")
            .ingredientTag('*', new Identifier(CommonData.COMMON_NAMESPACE, mineral.getPath()))
            .result(id, 1));

        pack.addShapelessRecipe(Motherlode.id(id, name -> mineral.getPath() + "_from_block"), recipe -> recipe
            .ingredientTag(new Identifier(CommonData.COMMON_NAMESPACE, id.getPath()))
            .result(mineral, 9)
        );
    }
}
