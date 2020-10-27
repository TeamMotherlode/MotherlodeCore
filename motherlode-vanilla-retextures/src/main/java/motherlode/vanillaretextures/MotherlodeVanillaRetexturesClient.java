package motherlode.vanillaretextures;

import motherlode.base.Motherlode;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;

public class MotherlodeVanillaRetexturesClient implements ClientModInitializer {
    public static final String MODID = "motherlode-vanilla-retextures";

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(
                Motherlode.id(MODID, "vanilla-retextures"),
                "resourcepacks/vanilla-retextures",
                modContainer,
                true);
        });
    }
}
