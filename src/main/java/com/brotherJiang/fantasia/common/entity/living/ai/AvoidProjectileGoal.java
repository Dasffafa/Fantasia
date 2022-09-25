package com.brotherJiang.fantasia.common.entity.living.ai;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.ProjectileEntity;

public class AvoidProjectileGoal<T extends ProjectileEntity> extends Goal {
    @Override
    public boolean shouldExecute() {
        return false;
    }
//    protected final ProjectileEntity entity;
//    private final double avoidSpeed;
//    protected T avoidTarget;
//    protected final float avoidDistance;
//    protected Path path;
//    /**
//     * Class of entity this behavior seeks to avoid
//     */
//    protected final Class<T> classToAvoid;
//    protected final Predicate<ProjectileEntity> avoidTargetSelector;
//    private final EntityPredicate builtTargetSelector;
//
//    public AvoidProjectileGoal(ProjectileEntity entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double avoidSpeed) {
//        this(entityIn, classToAvoidIn, (entity) -> {
//            return true;
//        }, avoidDistanceIn, avoidSpeed, EntityPredicates.CAN_AI_TARGET::test);
//    }
//
//    public AvoidProjectileGoal(ProjectileEntity entityIn, Class<T> avoidClass, Predicate<ProjectileEntity> targetPredicate, float distance, double avoidSpeed, Predicate<ProjectileEntity> p_i48859_9_) {
//        this.entity = entityIn;
//        this.classToAvoid = avoidClass;
//        this.avoidTargetSelector = targetPredicate;
//        this.avoidDistance = distance;
//        this.avoidSpeed = avoidSpeed;
//        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
//        this.builtTargetSelector = (new EntityPredicate()).setDistance((double) distance).setCustomPredicate(p_i48859_9_.and(targetPredicate));
//    }
//
//    public AvoidProjectileGoal(ProjectileEntity entityIn, Class<T> avoidClass, float distance, double avoidSpeed, Predicate<LivingEntity> targetPredicate) {
//        this(entityIn, avoidClass, (entity) -> {
//            return true;
//        }, distance, avoidSpeed, targetPredicate);
//    }
//
//    /**
//     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
//     * method as well.
//     */
//    public boolean shouldExecute() {
//        this.avoidTarget = this.entity.world.getClosestEntity(this.classToAvoid, this.builtTargetSelector, this.entity, this.entity.getPosX(), this.entity.getPosY(), this.entity.getPosZ(), this.entity.getBoundingBox().grow((double) this.avoidDistance, 3.0D, (double) this.avoidDistance));
//        if (this.avoidTarget == null) {
//            return false;
//        } else {
//            Vector3d vector3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, this.avoidTarget.getPositionVec());
//            if (vector3d == null) {
//                return false;
//            } else if (this.avoidTarget.getDistanceSq(vector3d.x, vector3d.y, vector3d.z) < this.avoidTarget.getDistanceSq(this.entity)) {
//                return false;
//            } else {
//                this.path = this.navigation.pathfind(vector3d.x, vector3d.y, vector3d.z, 0);
//                return this.path != null;
//            }
//        }
//    }
//
//    /**
//     * Returns whether an in-progress EntityAIBase should continue executing
//     */
//    public boolean shouldContinueExecuting() {
//        return !this.navigation.noPath();
//    }
//
//    /**
//     * Execute a one shot task or start executing a continuous task
//     */
//    public void startExecuting() {
//        this.navigation.setPath(this.path, this.avoidSpeed);
//    }
//
//    /**
//     * Reset the task's internal state. Called when this task is interrupted by another one
//     */
//    public void resetTask() {
//        this.avoidTarget = null;
//    }
}