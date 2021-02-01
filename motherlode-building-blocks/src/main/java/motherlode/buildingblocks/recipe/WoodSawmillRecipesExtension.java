package motherlode.buildingblocks.recipe;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import motherlode.base.Motherlode;
import motherlode.base.api.varianttype.MotherlodeVariantType;
import motherlode.buildingblocks.MotherlodeModule;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class WoodSawmillRecipesExtension implements MotherlodeVariantType.Extension<Block> {
    @Override
    public void registerExtension(Identifier id) {
    }

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {
        List<Pair<String, String>> logAndWoodPairs = Arrays.asList(new Pair<>("log", "wood"), new Pair<>("wood", "log"));

        // Log and wood recipes
        for (Pair<String, String> pair : logAndWoodPairs) {
            String log = pair.getLeft();
            String wood = pair.getRight();
            // in the second iteration log="wood" and wood="log"
            add(pack, Motherlode.id(id, name -> name + "_" + log + "_to_sticks"), Motherlode.id(id, name -> name + "_" + log), new Identifier("minecraft", "stick"), 8);
            add(pack, Motherlode.id(id, name -> name + "_" + log + "_to_bowls"), Motherlode.id(id, name -> name + "_" + log), new Identifier("minecraft", "bowl"), 4);
            add(pack, Motherlode.id(id, name -> name + "_" + log + "_to_crafting_table"), Motherlode.id(id, name -> name + "_" + log), new Identifier("minecraft", "crafting_table"));

            add(pack, id, name -> name + "_" + log + "_to_planks", name -> name + "_" + log, name -> name + "_planks", 4);
            add(pack, id, name -> name + "_" + log + "_to_stripped_" + log, name -> name + "_" + log, name -> "stripped_" + name + "_" + log);
            add(pack, id, name -> name + "_" + log + "_to_" + wood, name -> name + "_" + log, name -> name + "_" + wood);
            add(pack, id, name -> name + "_" + log + "_to_stripped_" + wood, name -> name + "_" + log, name -> "stripped_" + name + "_" + wood);
            add(pack, id, name -> name + "_" + log + "_to_slabs", name -> name + "_" + log, name -> name + "_slab", 8);
            add(pack, id, name -> name + "_" + log + "_to_pressure_plates", name -> name + "_" + log, name -> name + "_pressure_plate", 2);
            add(pack, id, name -> name + "_" + log + "_to_fences", name -> name + "_" + log, name -> name + "_fence", 3);
            add(pack, id, name -> name + "_" + log + "_to_trapdoors", name -> name + "_" + log, name -> name + "_trapdoor", 2);
            add(pack, id, name -> name + "_" + log + "_to_fence_gates", name -> name + "_" + log, name -> name + "_fence_gate");
            add(pack, id, name -> name + "_" + log + "_to_stairs", name -> name + "_" + log, name -> name + "_stairs", 4);
            add(pack, id, name -> name + "_" + log + "_to_buttons", name -> name + "_" + log, name -> name + "_button", 4);
            add(pack, id, name -> name + "_" + log + "_to_doors", name -> name + "_" + log, name -> name + "_door", 2);
            add(pack, id, name -> name + "_" + log + "_to_signs", name -> name + "_" + log, name -> name + "_sign", 2);
            add(pack, id, name -> name + "_" + log + "_to_boat", name -> name + "_" + log, name -> name + "_boat");
            add(pack, Motherlode.id(id, name -> name + "_" + log + "_to_mulch"), Motherlode.id(id, name -> name + "_" + log), MotherlodeModule.id("mulch"));
        }

        // Plank recipes
        add(pack, Motherlode.id(id, name -> name + "_" + "planks_to_sticks"), Motherlode.id(id, name -> name + "_planks"), new Identifier("minecraft", "stick"), 2);
        add(pack, Motherlode.id(id, name -> name + "_" + "planks_to_bowls"), Motherlode.id(id, name -> name + "_planks"), new Identifier("minecraft", "bowl"));

        add(pack, id, name -> name + "_planks_to_slabs", name -> name + "_planks", name -> name + "_slab", 2);
        add(pack, id, name -> name + "_planks_to_stairs", name -> name + "_planks", name -> name + "_stairs");
        add(pack, id, name -> name + "_planks_to_button", name -> name + "_planks", name -> name + "_button");
    }

    @Override
    public void registerOnClient(Identifier id) {
    }

    @Override
    public Block[] variants() {
        return new Block[0];
    }

    private static void add(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id, UnaryOperator<String> recipeId, UnaryOperator<String> ingredient, UnaryOperator<String> result, int count) {
        add(pack, new Identifier(id.getNamespace(), recipeId.apply(id.getPath())), new Identifier(id.getNamespace(), ingredient.apply(id.getPath())), new Identifier(id.getNamespace(), result.apply(id.getPath())), count);
    }

    private static void add(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id, UnaryOperator<String> recipeId, UnaryOperator<String> ingredient, UnaryOperator<String> result) {
        add(pack, new Identifier(id.getNamespace(), recipeId.apply(id.getPath())), new Identifier(id.getNamespace(), ingredient.apply(id.getPath())), new Identifier(id.getNamespace(), result.apply(id.getPath())), 1);
    }

    private static void add(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id, Identifier ingredient, Identifier result) {
        add(pack, id, ingredient, result, 1);
    }

    private static void add(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id, Identifier ingredient, Identifier result, int count) {
        pack.addStonecuttingRecipe(id, recipe -> recipe
            .type(MotherlodeModule.id("sawmilling"))
            .ingredientItem(ingredient)
            .result(result)
            .count(count)
        );
    }
}
