package motherlode.enderinvasion;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ModInitializer;
import motherlode.base.Motherlode;
import motherlode.enderinvasion.component.EnderInvasionChunkComponent;
import motherlode.enderinvasion.component.EnderInvasionComponent;
import motherlode.enderinvasion.impl.EnderInvasionImpl;
import motherlode.enderinvasion.recipe.MotherlodeSpreadRecipes;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;
import org.apache.logging.log4j.Level;

public class MotherlodeModule implements ModInitializer, LevelComponentInitializer, ChunkComponentInitializer {
    public static final String MODID = "motherlode-ender-invasion";

    private static final EnderInvasion ENDER_INVASION = new EnderInvasion(new EnderInvasionImpl());

    @Override
    public void onInitialize() {
        getEnderInvasion().initializeEnderInvasion();
        MotherlodeEnderInvasionBlocks.init();
        MotherlodeEnderInvasionTags.init();
        MotherlodeSpreadRecipes.register();
    }

    public static EnderInvasion getEnderInvasion() {
        return ENDER_INVASION;
    }

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry registry) {
        registry.register(EnderInvasion.STATE, worldProperties -> new EnderInvasionComponent.Impl(EnderInvasionComponent.State.PRE_ECHERITE));
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(EnderInvasion.CHUNK_STATE, chunk -> new EnderInvasionChunkComponent.Impl(EnderInvasionChunkComponent.State.PRE_ECHERITE));
    }

    public static void log(Level level, CharSequence message) {
        Motherlode.log(level, "Motherlode Ender Invasion", message);
    }

    public static void log(Level level, Object message) {
        Motherlode.log(level, "Motherlode Ender Invasion", String.valueOf(message));
    }

    public static Identifier id(String name) {
        return Motherlode.id(MODID, name);
    }
}
