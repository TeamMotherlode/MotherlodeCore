package motherlode.mobs.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderNearTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class RedstoneSentryEntity extends GolemEntity {
    public RedstoneSentryEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9D, 16.0F));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, LivingEntity.class, 3, false, false,
            livingEntity -> livingEntity instanceof PlayerEntity || livingEntity instanceof Monster));
    }

    public static DefaultAttributeContainer.Builder createRedstoneSentryAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.50D)
            .add(EntityAttributes.GENERIC_ARMOR, 1.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0D)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0F);
    }

    public boolean canTarget(EntityType<?> type) {
        if (type == EntityType.PLAYER) {
            return true;
        } else {
            return super.canTarget(type);
        }
    }
}
