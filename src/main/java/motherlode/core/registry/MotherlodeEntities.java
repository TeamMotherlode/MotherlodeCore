package motherlode.core.registry;

import jdk.vm.ci.code.Register;
import motherlode.core.Motherlode;
import motherlode.core.entities.ArmadilloEntity;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MotherlodeEntities {

	public static final EntityType<ArmadilloEntity> ARMADILLO_ENTITY = register("armadillo", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArmadilloEntity::new)
	.size(EntityDimensions.fixed(0.6F, 0.7F)).build());

	public static void init() {
		//Registry.register(Registry.ENTITY_TYPE, new Identifier("motherlode", "armadillo"), ARMADILLO_ENTITY);
		FabricDefaultAttributeRegistry.register(ARMADILLO_ENTITY,
				ArmadilloEntity.createArmadilloAttributes());
	}

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
		return Registry.register(Registry.ENTITY_TYPE, Motherlode.id(name), entity);
	}
}
