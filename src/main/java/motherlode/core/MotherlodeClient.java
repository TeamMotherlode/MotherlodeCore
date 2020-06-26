package motherlode.core;

import motherlode.core.registry.MotherlodeAssets;
import motherlode.core.registry.MotherlodeBlocks;
import net.fabricmc.api.ClientModInitializer;

public class MotherlodeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MotherlodeAssets.register(MotherlodeBlocks.defaultStateList, MotherlodeBlocks.defaultModelList, MotherlodeBlocks.defaultItemModelList);
	}
}