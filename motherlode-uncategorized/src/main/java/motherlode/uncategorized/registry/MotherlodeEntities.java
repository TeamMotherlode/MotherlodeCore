package motherlode.uncategorized.registry;

import motherlode.base.Motherlode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

public class MotherlodeEntities {
	public static void init() {

	}

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
		return Registry.register(Registry.ENTITY_TYPE, Motherlode.id(name), entity);
	}
}
