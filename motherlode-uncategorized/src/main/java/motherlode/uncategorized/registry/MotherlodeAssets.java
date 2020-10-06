package motherlode.uncategorized.registry;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import motherlode.base.api.AssetProcessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class MotherlodeAssets implements AssetProcessor {

    public static final Map<Block, Supplier<String>> flatItemModelList = new HashMap<>();

    @Override
    public void accept(ArtificeResourcePack.ClientResourcePackBuilder pack, Identifier id) {

    }
}