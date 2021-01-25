package motherlode.buildingblocks;

import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import motherlode.base.api.assets.DataProcessor;
import motherlode.buildingblocks.recipe.SawmillingRecipe;
import motherlode.buildingblocks.recipe.SawmillingRecipeType;
import motherlode.base.Motherlode;
import motherlode.buildingblocks.screen.SawmillScreenHandler;
import motherlode.buildingblocks.screen.StonecutterScreenHandler;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer {
    public static final String MODID = "motherlode-building-blocks";

    public static final Identifier INTERACT_WITH_SAWMILL_STAT;
    public static final ScreenHandlerType<SawmillScreenHandler> SAWMILL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(MotherlodeModule.id("sawmill"), SawmillScreenHandler::new);
    public static final RecipeType<SawmillingRecipe> SAWMILLING_RECIPE_TYPE = register("sawmilling", new SawmillingRecipeType());
    public static final RecipeSerializer<SawmillingRecipe> SAWMILLING_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, MotherlodeModule.id("sawmilling"), new CuttingRecipe.Serializer<>(SawmillingRecipe::new));

    public static final ScreenHandlerType<StonecutterScreenHandler> STONECUTTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(Motherlode.id("minecraft", "stonecutter"), StonecutterScreenHandler::new);

    static {
        Identifier interactWithSawmillIdentifier = MotherlodeModule.id("interact_with_sawmill");
        INTERACT_WITH_SAWMILL_STAT = Registry.register(Registry.CUSTOM_STAT, interactWithSawmillIdentifier, interactWithSawmillIdentifier);
        Stats.CUSTOM.getOrCreateStat(interactWithSawmillIdentifier);
    }

    @Override
    public void onInitialize() {
        MotherlodeBuildingBlocks.init();
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Building Blocks", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Building Blocks", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }

    private static <T extends RecipeType<?> & DataProcessor> T register(String name, T recipeType) {
        return register(name, recipeType, recipeType);
    }

    private static <T extends RecipeType<?>> T register(String name, T recipeType, DataProcessor data) {
        return Motherlode.register(
            id -> Registry.register(Registry.RECIPE_TYPE, id, recipeType),
            MotherlodeModule.id(name),
            recipeType,
            data
        );
    }
}
