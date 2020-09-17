package motherlode.base;

import com.swordglowsblue.artifice.api.Artifice;
import motherlode.base.api.MotherlodeAssets;
import motherlode.base.api.MotherlodeData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Motherlode implements ModInitializer {

    public static final String MODID = "motherlode";

    @Override
    public void onInitialize() {

        MotherlodeAssets.EVENT.register(pack -> MotherlodeAssets.getGenerators().forEach(
                p -> p.getRight().accept(pack, p.getLeft())));

        MotherlodeData.EVENT.register(pack -> MotherlodeData.getGenerators().forEach(
                p -> p.getRight().accept(pack, p.getLeft())));

        Artifice.registerData(Motherlode.id("data_pack"), pack -> {
            MotherlodeData.EVENT.invoker().registerData(pack);
        });
    }
    public static Identifier id(String namespace, String name) { return new Identifier(namespace, name); }
    public static Identifier id(String name) {
        return id(MODID, name);
    }
}