package motherlode.buildingblocks.recipe;

import java.util.Arrays;
import java.util.List;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import motherlode.base.api.assets.DataProcessor;
import motherlode.buildingblocks.MotherlodeModule;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;


public class SawmillingRecipeType implements RecipeType<SawmillingRecipe>, DataProcessor {

    @Override
    public String toString() {
        return MotherlodeModule.id("sawmilling").toString();
    }

    private void add(ArtificeResourcePack.ServerResourcePackBuilder pack, String name, Identifier ingredient, Identifier result, int count) {
        pack.addStonecuttingRecipe(MotherlodeModule.id(name), stonecuttingRecipeBuilder -> stonecuttingRecipeBuilder
            .type(MotherlodeModule.id("sawmilling"))
            .ingredientItem(ingredient)
            .result(result)
            .count(count)
        );
    }

    private void add(ArtificeResourcePack.ServerResourcePackBuilder pack, String name, Identifier ingredient, Identifier result) {
        add(pack, name, ingredient, result, 1);
    }

    @Override
    public void accept(ArtificeResourcePack.ServerResourcePackBuilder pack, Identifier id) {

        // Log and wood recipes
        List<Pair<String, String>> logAndWoodPairs = Arrays.asList(new Pair<>("log", "wood"), new Pair<>("wood", "log"));
        for (Pair<String, String> pair : logAndWoodPairs) {
            String log = pair.getLeft();
            String wood = pair.getRight();
            // in the second iteration log="wood" and wood="log"
            add(pack, "oak_" + log + "_to_sticks", new Identifier("oak_" + log), new Identifier("stick"), 8);
            add(pack, "oak_" + log + "_to_planks", new Identifier("oak_" + log), new Identifier("oak_planks"), 4);
            add(pack, "oak_" + log + "_to_stripped_" + log, new Identifier("oak_" + log), new Identifier("stripped_oak_" + log));
            add(pack, "oak_" + log + "_to_" + wood, new Identifier("oak_" + log), new Identifier("oak_" + wood));
            add(pack, "oak_" + log + "_to_stripped_" + wood, new Identifier("oak_" + log), new Identifier("stripped_oak_" + wood));
            add(pack, "oak_" + log + "_to_slabs", new Identifier("oak_" + log), new Identifier("oak_slab"), 8);
            add(pack, "oak_" + log + "_to_pressure_plates", new Identifier("oak_" + log), new Identifier("oak_pressure_plate"), 2);
            add(pack, "oak_" + log + "_to_fences", new Identifier("oak_" + log), new Identifier("oak_fence"), 3);
            add(pack, "oak_" + log + "_to_trapdoors", new Identifier("oak_" + log), new Identifier("oak_trapdoor"), 2);
            add(pack, "oak_" + log + "_to_fence_gates", new Identifier("oak_" + log), new Identifier("oak_fence_gate"), 4);
            add(pack, "oak_" + log + "_to_stairs", new Identifier("oak_" + log), new Identifier("oak_stairs"), 4);
            add(pack, "oak_" + log + "_to_buttons", new Identifier("oak_" + log), new Identifier("oak_button"), 4);
            add(pack, "oak_" + log + "_to_doors", new Identifier("oak_" + log), new Identifier("oak_door"), 2);
            add(pack, "oak_" + log + "_to_signs", new Identifier("oak_" + log), new Identifier("oak_sign"), 2);
            add(pack, "oak_" + log + "_to_boat", new Identifier("oak_" + log), new Identifier("oak_boat"));
        }

        // Plank recipes
        add(pack, "oak_planks_to_sticks", new Identifier("oak_planks"), new Identifier("stick"), 2);
        add(pack, "oak_planks_to_slabs", new Identifier("oak_planks"), new Identifier("oak_slab"), 2);
        add(pack, "oak_planks_to_fence_gates", new Identifier("oak_planks"), new Identifier("oak_fence_gate"));
        add(pack, "oak_planks_to_stairs", new Identifier("oak_planks"), new Identifier("oak_stairs"));
        add(pack, "oak_planks_to_button", new Identifier("oak_planks"), new Identifier("oak_button"));
    }
}
