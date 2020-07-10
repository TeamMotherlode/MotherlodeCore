package motherlode.core.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class LizardEntity extends AnimalEntity {


    public LizardEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    /*Entity AI goals. The lower the priority, the more the entity prioritizes accomplishing the goal*/
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this,2.0));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createLizardAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return null;
    }
}
