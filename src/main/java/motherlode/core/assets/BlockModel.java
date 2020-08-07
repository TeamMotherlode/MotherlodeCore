package motherlode.core.assets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.util.TriConsumer;
import static com.swordglowsblue.artifice.api.ArtificeResourcePack.ClientResourcePackBuilder;

@Environment(EnvType.CLIENT)
public enum BlockModel {
    DEFAULT(BlockModelRegisters.DEFAULT),
    PLANT(BlockModelRegisters.PLANT),
    CROSS(BlockModelRegisters.CROSS),
    STAIR(BlockModelRegisters.STAIR),
    SLAB(BlockModelRegisters.SLAB),
    PILLAR(BlockModelRegisters.PILLAR),
    PAINTABLE(BlockModelRegisters.PAINTABLE),
    PLATFORM(BlockModelRegisters.PLATFORM),
    SHOVELABLE(BlockModelRegisters.SHOVELABLE),
    MANUAL(null);

    TriConsumer<ClientResourcePackBuilder, String, Boolean> modelRegister;

    BlockModel(TriConsumer<ClientResourcePackBuilder, String, Boolean> modelRegister) {
        this.modelRegister = modelRegister;
    }

    public void register(ClientResourcePackBuilder pack, String id, Boolean bool) {
        if (this != MANUAL)
            modelRegister.accept(pack, id, bool);
    }




}
