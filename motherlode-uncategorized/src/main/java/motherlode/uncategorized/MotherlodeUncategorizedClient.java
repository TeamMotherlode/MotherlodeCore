package motherlode.uncategorized;

import motherlode.uncategorized.registry.MotherlodeAssets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MotherlodeUncategorizedClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		motherlode.base.api.MotherlodeAssets.addAssets(null, new MotherlodeAssets());
	}
}
