package motherlode.base;

import net.minecraft.util.Identifier;
import motherlode.base.api.AssetProcessor;
import motherlode.base.api.DataProcessor;
import motherlode.base.api.MotherlodeAssets;
import motherlode.base.api.Processor;
import motherlode.base.api.Registerable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import com.swordglowsblue.artifice.api.Artifice;

public class Motherlode implements ModInitializer {
    public static final String MODID = "motherlode";

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getEntrypointContainers("motherlode:init", ModInitializer.class)
            .forEach(container -> container.getEntrypoint().onInitialize());

        Artifice.registerData(Motherlode.id("data_pack"), pack ->
            MotherlodeAssets.getData().forEach(pair ->
                pair.getRight().accept(pack, pair.getLeft()))
        );
    }

    public static <T> T register(Registerable<?> registerable, Identifier id, T t, Processor<? super T> p, AssetProcessor assets, DataProcessor data) {
        registerable.register(id);

        if (p != null) p.accept(t);
        if (assets != null) MotherlodeAssets.addAssets(id, assets);
        if (data != null) MotherlodeAssets.addData(id, data);

        return t;
    }

    public static Identifier id(String namespace, String name) {
        return new Identifier(namespace, name);
    }

    public static Identifier id(String name) {
        return id(MODID, name);
    }
}
