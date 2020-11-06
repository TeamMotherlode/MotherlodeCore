package motherlode.base.api;

import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public interface DataManager {
    void addOre(Identifier id, OreTarget target, BlockState state, int veinSize, int veinsPerChunk, int minY, int maxY);
    void addOre(Identifier id, OreTarget target, BlockState state, int veinSize, Function<ConfiguredFeature<?, ?>, ConfiguredFeature<?, ?>> decorators);
}
