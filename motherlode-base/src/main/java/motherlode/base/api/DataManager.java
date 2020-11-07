package motherlode.base.api;

import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import motherlode.base.Motherlode;

/**
 * Using this interface, one can more easily register data. At the moment, only adding ores is possible with this.
 * An implementation of it is returned by {@link Motherlode#getDataManager()}.
 *
 * <P>Not to be confused with a {@link DataProcessor}, which can be be registered using a {@link AssetsManager}.</p>
 */
public interface DataManager {
    /**
     * Creates and registers a {@link ConfiguredFeature} to generate an ore using the specified options.
     * @param id The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param target The {@link OreTarget} to specify the biomes that the ore will generate in, the blocks that the ore can replace and the {@link net.minecraft.world.gen.GenerationStep.Feature} used.
     * @param state The ore will generate with this {@link BlockState}.
     * @param veinSize Each ore vein will have about this amount of ores.
     * @param veinsPerChunk The average amount of ore veins per chunk.
     * @param minY The minimum Y level that that this ore will generate in.
     * @param maxY The maximum Y level that that this ore will generate in.
     */
    void addOre(Identifier id, OreTarget target, BlockState state, int veinSize, int veinsPerChunk, int minY, int maxY);

    /**
     * Creates and registers a {@link ConfiguredFeature} to generate an ore using the specified options.
     * @param id The {@link Identifier} to register the {@code ConfiguredFeature} under.
     * @param target The {@link OreTarget} to specify the biomes that the ore will generate in, the blocks that the ore can replace and the {@link net.minecraft.world.gen.GenerationStep.Feature} used.
     * @param state The ore will generate with this {@link BlockState}.
     * @param veinSize Each ore vein will have about this amount of ores.
     * @param decorators This can be used to {@link net.minecraft.world.gen.decorator.Decoratable decorate} the created {@code ConfiguredFeature}.
     */
    void addOre(Identifier id, OreTarget target, BlockState state, int veinSize, Function<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> decorators);
}
