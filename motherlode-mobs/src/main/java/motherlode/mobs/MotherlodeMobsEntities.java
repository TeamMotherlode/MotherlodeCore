package motherlode.mobs;

import motherlode.base.Motherlode;
import motherlode.mobs.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class MotherlodeMobsEntities {
    public static final EntityType<ArmadilloEntity> ARMADILLO_ENTITY = register("armadillo", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ArmadilloEntity::new)
      .dimensions(EntityDimensions.fixed(0.6F, 0.7F)).build());

    public static final EntityType<LizardEntity> LIZARD_ENTITY = register("lizard", FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LizardEntity::new)
      .dimensions(EntityDimensions.fixed(0.6F, 0.7F)).build());

    public static final EntityType<RedstoneGolemEntity> REDSTONE_GOLEM_ENTITY = register("redstone_golem", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RedstoneGolemEntity::new)
      .dimensions(EntityDimensions.fixed(3.0F, 3.5F)).build());

    public static final EntityType<RedstoneMarauderEntity> REDSTONE_MARAUDER_ENTITY = register("redstone_marauder", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RedstoneMarauderEntity::new)
      .dimensions(EntityDimensions.fixed(1.2F, 2.0F)).build());

    public static final EntityType<RedstoneSentryEntity> REDSTONE_SENTRY_ENTITY = register("redstone_sentry", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RedstoneSentryEntity::new)
      .dimensions(EntityDimensions.fixed(1.3F, 1.8F)).build());

    public static void init() {
        //Registry.register(Registry.ENTITY_TYPE, new Identifier("motherlode", "armadillo"), ARMADILLO_ENTITY);
        FabricDefaultAttributeRegistry.register(ARMADILLO_ENTITY,
          ArmadilloEntity.createArmadilloAttributes());
        FabricDefaultAttributeRegistry.register(LIZARD_ENTITY,
          LizardEntity.createLizardAttributes());
        FabricDefaultAttributeRegistry.register(REDSTONE_GOLEM_ENTITY,
          RedstoneGolemEntity.createRedstoneGolemAttributes());
        FabricDefaultAttributeRegistry.register(REDSTONE_MARAUDER_ENTITY,
          RedstoneMarauderEntity.createRedstoneMarauderAttributes());
        FabricDefaultAttributeRegistry.register(REDSTONE_SENTRY_ENTITY,
          RedstoneSentryEntity.createRedstoneSentryAttributes());
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> entity) {
        return Registry.register(Registry.ENTITY_TYPE, Motherlode.id(name), entity);
    }
}
