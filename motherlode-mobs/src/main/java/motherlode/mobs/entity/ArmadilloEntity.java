package motherlode.mobs.entity;

import net.minecraft.entity.Entity;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;
import java.util.List;
import java.util.function.Predicate;

public class ArmadilloEntity extends AnimalEntity implements IAnimatedEntity {

    private boolean is_scared = false;
//    private final ArmadilloEntity.ArmadilloFleeGoal<PlayerEntity> fleeGoal =
//            new ArmadilloFleeGoal<>(this, PlayerEntity.class, 0F, 0, 0);
    // private ArmadilloEntity.ArmadilloEscapeDangerGoal escapeDangerGoal;

    EntityAnimationManager manager = new EntityAnimationManager();

    EntityAnimationController<ArmadilloEntity> walkController =
      new EntityAnimationController<>(this, "walkController", 10, this::walk_animationPredicate);

    private static final Predicate<LivingEntity> PLAYER_ENTITY_FILTER = (livingEntity) -> {
        if (livingEntity == null) {
            return false;
        } else return livingEntity instanceof PlayerEntity;
    };

//    EntityAnimationController curlUpController =
//            new EntityAnimationController(this, " curlUpController", 20, this::curlUpAnimationPredicate);


    private <E extends Entity> boolean walk_animationPredicate(AnimationTestEvent<E> event)
    {
        boolean is_idle = this.getVelocity().getX() == 0.0 && this.getVelocity().getZ() == 0.0;
        if(!is_scared && !(is_idle)) {
            walkController.setAnimation(new AnimationBuilder().addAnimation("walk"));
            return true;
        }
        else if(is_scared){
            walkController.setAnimation(new AnimationBuilder().addAnimation("scared", false));
            return true;
        }
        return false;

    }


    public void registerAnimationControllers(){
        manager.addAnimationController(walkController);
    }


    /*
    Constructs ArmadilloEntity
     */
    public ArmadilloEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        registerAnimationControllers();
    }

    /*Entity AI goals. The lower the priority, the more the entity prioritizes accomplishing the goal*/
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ArmadilloEscapeDangerGoal(this,1.4));
        this.goalSelector.add(2, new ArmadilloFleeGoal<>(this, PlayerEntity.class, 0F, 0, 0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 3.5F));
    }


    /*Builds attributes for ArmadilloEntity. Called when ArmadilloEntity is registered*/
    public static DefaultAttributeContainer.Builder createArmadilloAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
          .add(EntityAttributes.GENERIC_ARMOR, 5.0D);
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    public void setScared(boolean value){
        is_scared = value;
    }

    public void mobTick() {
        super.mobTick();
        Box threatZone = this.getBoundingBox().expand(10);
        List<ServerPlayerEntity> list = this.world.getEntitiesByClass(ServerPlayerEntity.class, threatZone,
          PLAYER_ENTITY_FILTER);
        for (ServerPlayerEntity serverPlayerEntity: list) {
            if (serverPlayerEntity.getMainHandStack().getItem() instanceof ToolItem) {
                this.setScared(true);
            }
        }

    }


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
            this.targetEntity = this.mob.world.getClosestEntityIncludingUngeneratedChunks
              (this.classToFleeFrom, this.withinRangePredicate, this.mob,
                this.mob.getX(), this.mob.getY(), this.mob.getZ(),
                this.mob.getBoundingBox().expand(this.fleeDistance, 3.0D, (double)this.fleeDistance));
            if(targetEntity != null) {
                /*checks if player is holding a tool*/
                if (this.targetEntity.getMainHandStack().getItem() instanceof ToolItem) {
                    armadilloEntity.setScared(true);
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
                armadilloEntity.setScared(true);
                armadilloEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(10.0D);
            }
            else {
                armadilloEntity.setScared(false);
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
                armadilloEntity.setScared(true);
                armadilloEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR).setBaseValue(10.0D);
            }
            else {
                armadilloEntity.setScared(false);
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
    public PassiveEntity createChild(ServerWorld world, PassiveEntity mate) {
        return null;
    }
}
