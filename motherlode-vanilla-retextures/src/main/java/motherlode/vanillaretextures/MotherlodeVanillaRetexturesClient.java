package motherlode.vanillaretextures;

import net.minecraft.util.Identifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

public class MotherlodeVanillaRetexturesClient implements ClientModInitializer {
    public static final String MODID = "motherlode-vanilla-retextures";

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer(MODID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(
                new Identifier(MODID, "vanilla-retextures"),
                modContainer,
                ResourcePackActivationType.DEFAULT_ENABLED));
    }
}
