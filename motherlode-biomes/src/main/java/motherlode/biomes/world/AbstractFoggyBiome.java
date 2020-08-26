package motherlode.biomes.world;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.world.biome.Biome;

public abstract class AbstractFoggyBiome extends Biome {
    public AbstractFoggyBiome(Settings settings) {
        super(settings);
    }

    public abstract float getFogDensity(double posX, double posZ);
    public abstract GlStateManager.FogMode getFogMode();
}
