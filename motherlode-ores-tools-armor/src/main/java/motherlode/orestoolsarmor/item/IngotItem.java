package motherlode.orestoolsarmor.item;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class IngotItem extends Item implements DataProcessor {

    public IngotItem(Settings settings) {
        super(settings);
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.ITEM_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .accept(pack, id);

        Identifier nugget = Motherlode.id(id.getNamespace(), id.getPath().replace("ingot", "nugget"));

        CommonData.ITEM_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, nugget.getPath()))
            .accept(pack, nugget);

        pack.addShapelessRecipe(nugget, recipe -> recipe
            .ingredientTag(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .result(nugget, 9));

        pack.addShapedRecipe(id, recipe ->
            recipe.pattern("***", "***", "***")
            .ingredientTag('*', Motherlode.id(CommonData.COMMON_NAMESPACE, nugget.getPath()))
            .result(id, 1));
    }
}
