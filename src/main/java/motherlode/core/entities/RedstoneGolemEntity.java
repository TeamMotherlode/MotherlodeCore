package motherlode.core.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class RedstoneGolemEntity extends GolemEntity {


    public RedstoneGolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new GoToEntityTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.9D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, LivingEntity.class, 3, false, false, (livingEntity) -> {
            return livingEntity instanceof PlayerEntity || (livingEntity instanceof Monster);
        }));

    }

    public static DefaultAttributeContainer.Builder createRedstoneGolemAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30D)
                .add(EntityAttributes.GENERIC_ARMOR, 5.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0D);
    }

    public boolean canTarget(EntityType<?> type) {
        if (type == EntityType.PLAYER) {
            return true;
        } else {
            return super.canTarget(type);
        }
    }







}
