package motherlode.uncategorized.registry;

import motherlode.base.Motherlode;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class MotherlodeEnchantments {
	public static void init() {
		// CALLED TO MAINTAIN REGISTRY ORDER
	}

	protected static <T extends Enchantment> T register(String name, T enchantment) {
		return Registry.register(Registry.ENCHANTMENT, Motherlode.id(name), enchantment);
	}
}
