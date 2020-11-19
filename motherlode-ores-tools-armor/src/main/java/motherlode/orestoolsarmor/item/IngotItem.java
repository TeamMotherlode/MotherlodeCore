package motherlode.orestoolsarmor.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import motherlode.base.api.CommonData;
import motherlode.base.Motherlode;
import motherlode.base.api.DataProcessor;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class IngotItem extends Item implements DataProcessor {
    private final String nugget;
    private final boolean nuggetRecipes;

    public IngotItem(Settings settings) {
        this(settings, "nugget");
    }

    public IngotItem(Settings settings, String nugget) {
        this(settings, nugget, true);
    }

    public IngotItem(Settings settings, boolean nuggetRecipes) {
        this(settings, "nugget", nuggetRecipes);
    }

    public IngotItem(Settings settings, String nugget, boolean nuggetRecipes) {
        super(settings);
        this.nugget = nugget;
        this.nuggetRecipes = nuggetRecipes;
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        CommonData.ITEM_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
            .accept(pack, id);

        Identifier nugget = Motherlode.id(id.getNamespace(), id.getPath().replace("ingot", this.nugget));

        CommonData.ITEM_TAG.apply(Motherlode.id(CommonData.COMMON_NAMESPACE, nugget.getPath()))
            .accept(pack, nugget);

        if (this.nuggetRecipes) {
            pack.addShapelessRecipe(nugget, recipe -> recipe
                .ingredientTag(Motherlode.id(CommonData.COMMON_NAMESPACE, id.getPath()))
                .result(nugget, 9));

            pack.addShapedRecipe(id, recipe ->
                recipe.pattern("***", "***", "***")
                    .ingredientTag('*', Motherlode.id(CommonData.COMMON_NAMESPACE, nugget.getPath()))
                    .result(id, 1));
        }
    }
}
