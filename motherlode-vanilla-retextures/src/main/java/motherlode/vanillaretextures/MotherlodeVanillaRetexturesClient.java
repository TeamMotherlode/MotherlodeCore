package motherlode.vanillaretextures;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import motherlode.base.Motherlode;

public class MotherlodeVanillaRetexturesClient implements ClientModInitializer {
    public static final String MODID = "motherlode-vanilla-retextures";

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(
                Motherlode.id(MODID, "vanilla-retextures"),
                "resourcepacks/vanilla-retextures",
                modContainer,
                true));
    }
}
