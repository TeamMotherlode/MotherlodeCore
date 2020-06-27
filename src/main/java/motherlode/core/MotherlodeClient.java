package motherlode.core;

import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register(MotherlodeBlocks.defaultStateList, MotherlodeBlocks.defaultModelList, MotherlodeBlocks.defaultItemModelList);
	}
}