package motherlode.biomes.world.feature.structure;

import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import com.mojang.serialization.Codec;

public class CampStructureFeature extends JigsawFeature {
    public CampStructureFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }
}
