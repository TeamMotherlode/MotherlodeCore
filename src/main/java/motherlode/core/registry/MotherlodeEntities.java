package motherlode.core.registry;

import motherlode.core.Motherlode;
import motherlode.core.entities.ArmadilloEntity;
import motherlode.core.entities.LizardEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class MotherlodeEntities {

	public static final EntityType<ArmadilloEntity> ARMADILLO_ENTITY = register("armadillo", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ArmadilloEntity::new)
	.size(EntityDimensions.fixed(0.6F, 0.7F)).build());


	public static final EntityType<LizardEntity> LIZARD_ENTITY = register("lizard", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LizardEntity::new)
			.size(EntityDimensions.fixed(0.6F, 0.7F)).build());

	public static void init() {
		//Registry.register(Registry.ENTITY_TYPE, new Identifier("motherlode", "armadillo"), ARMADILLO_ENTITY);
		FabricDefaultAttributeRegistry.register(ARMADILLO_ENTITY,
				ArmadilloEntity.createArmadilloAttributes());
		FabricDefaultAttributeRegistry.register(LIZARD_ENTITY,
				LizardEntity.createLizardAttributes());
	}

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
		return Registry.register(Registry.ENTITY_TYPE, Motherlode.id(name), entity);
	}
}
