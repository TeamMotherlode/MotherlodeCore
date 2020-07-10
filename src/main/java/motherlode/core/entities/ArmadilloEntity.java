package motherlode.core.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ArmadilloEntity extends AnimalEntity {

//    private boolean is_scared;
    //private ArmadilloEntity.ArmadilloFleeGoal<PlayerEntity> fleeGoal;
   // private ArmadilloEntity.ArmadilloEscapeDangerGoal escapeDangerGoal;

    /*
    Constructs ArmadilloEntity
     */
    public ArmadilloEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    /*Entity AI goals. The lower the priority, the more the entity prioritizes accomplishing the goal*/
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ArmadilloEscapeDangerGoal(this,3.0));
        this.goalSelector.add(2, new ArmadilloFleeGoal<>(this, PlayerEntity.class, 5.0F, 2, 3));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.5F));
    }

    /*Builds attributes for ArmadilloEntity. Called when ArmadilloEntity is registered*/
    public static DefaultAttributeContainer.Builder createArmadilloAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
                .add(EntityAttributes.GENERIC_ARMOR, 5.0D);
    }

//    public void changeArmor(){
//        this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(10.0D);
//    }
//
//    public void changeSpeed(){
//        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(2.5D);
//    }
//
//    public void setScared(boolean value){
//        is_scared = value;
//    }

    /*custom AI goal that extends FleeEntityGoal*/
    static class ArmadilloFleeGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
        private final ArmadilloEntity armadilloEntity;
        private final TargetPredicate withinRangePredicate;

        /*constructor*/
        public ArmadilloFleeGoal(ArmadilloEntity mob, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
            this.armadilloEntity = mob;
            this.withinRangePredicate = (new TargetPredicate()).setBaseMaxDistance(distance).setPredicate(inclusionSelector.and(extraInclusionSelector));
        }

        /*The armadillo will only run away when it feels threatened, such as when the player wields a damaging tool*/
        public boolean canStart() {
            //sets targetEntity
            this.targetEntity = this.mob.world.getClosestEntityIncludingUngeneratedChunks(this.classToFleeFrom, this.withinRangePredicate, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().expand((double)this.fleeDistance, 3.0D, (double)this.fleeDistance));
            if(targetEntity != null) {
                /*checks if player is holding a tool*/
                if (this.targetEntity.getMainHandStack().getItem() instanceof ToolItem) {
                    return super.canStart();
                }
                else {
                    return false;
                }
            }
            return false;
        }

        /*Determines if the armadillo should continue fleeing from the player.*/
        public boolean shouldContinue() {
            //Sets armor to 10 if superclass method returns true, sets to regular 5 if returns false
            if(super.shouldContinue()) {
                armadilloEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(10.0D);
            }
            else {
                armadilloEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(5.0D);
            }
            return super.shouldContinue();
        }

    }

    //Custom AI goal that extends EscapeDangerGoal
    static class ArmadilloEscapeDangerGoal extends EscapeDangerGoal{
        private final ArmadilloEntity armadilloEntity;

        //constructor
        public ArmadilloEscapeDangerGoal(ArmadilloEntity mob, double speed) {
            super(mob, speed);
            armadilloEntity = mob;
        }
        public boolean canStart() {
            return super.canStart();
        }

        public boolean shouldContinue() {
            //Sets armor to 10 if superclass method returns true, sets to regular 5 if returns false
            if(super.shouldContinue()) {
                armadilloEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(10.0D);
            }
            else {
                armadilloEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(5.0D);
            }
            return super.shouldContinue();
        }

        //Overrides superclass method in order to give the armadillo more area to work with during pathfinding
        protected boolean findTarget() {
            Vec3d vec3d = TargetFinder.findTarget(this.mob, 12, 8);
            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                return true;
            }
        }


    }


    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return null;
    }
}
