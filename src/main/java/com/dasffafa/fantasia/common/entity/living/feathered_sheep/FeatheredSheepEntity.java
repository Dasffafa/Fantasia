package com.dasffafa.fantasia.common.entity.living.feathered_sheep;

import com.dasffafa.fantasia.common.entity.ai.feathered_sheep.FeatheredSheepFloatGoal;
import com.dasffafa.fantasia.common.entity.ai.feathered_sheep.FeatheredSheepGotoCropGoal;
import com.dasffafa.fantasia.common.entity.ai.feathered_sheep.FeatheredSheepLeapAtTargetGoal;
import com.dasffafa.fantasia.common.utils.FantasiaSounds;
import com.dasffafa.fantasia.common.utils.SetBlockStateFlag;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public class FeatheredSheepEntity extends PathfinderMob implements IAnimatable, RangedAttackMob {
    public static final EntityDataAccessor<Boolean> isFlying = SynchedEntityData.defineId(FeatheredSheepEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> takeOffCoolDown = SynchedEntityData.defineId(FeatheredSheepEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> flyTime = SynchedEntityData.defineId(FeatheredSheepEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> currentBombingSp = SynchedEntityData.defineId(FeatheredSheepEntity.class, EntityDataSerializers.INT);
    private final int MAX_TAKEOFF_COOLDOWN = 240;
    private final int MAX_FLY_TIME = 400;
    private final int MAX_BOMBING_SP = 3;
    private final AnimationFactory animationFactory = GeckoLibUtil.createFactory(this);
    public FeatheredSheepStatus status;

    public FeatheredSheepEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45f)
                .add(Attributes.FLYING_SPEED, 0.35f)
                .add(Attributes.ATTACK_KNOCKBACK, 8.0f)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(isFlying, false);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FeatheredSheepFloatGoal(this));
//        this.goalSelector.addGoal(1, new FeatheredSheepChargeGoal(this, 1.5f));

        this.goalSelector.addGoal(1, new FeatheredSheepLeapAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this,1.2f,false));
        this.goalSelector.addGoal(2, new FeatheredSheepGotoCropGoal(this, 0.7F, 10));
        this.targetSelector.addGoal(0,new HurtByTargetGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Pig.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, FeatheredSheepEntity.class, true));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {

    }

    @Override
    public void travel(Vec3 travelVector) {
        if (!this.isFlying()) {
            super.travel(travelVector);
            return;
        }
        // Copy of FlyingEntity
        if (this.isInWater()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            float f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(f));
        }

        this.calculateEntityAnimation(this, false);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
            if (!this.isFlying())
                super.checkFallDamage(pY, pOnGround, pState, pPos);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            this.status = FeatheredSheepStatus.IDLING;
            if (tickCount % 4 == 0) {
                BlockPos pos = this.getOnPos();
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        BlockPos pos1 = pos.offset(i, 0, j);
                        Block block = level.getBlockState(pos1.above()).getBlock();
                        Block block1 = level.getBlockState(pos1).getBlock();
                        int flags = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
                        if (block instanceof BushBlock && block1 instanceof GrassBlock) {
                            level.setBlock(pos1.above(), Blocks.DEAD_BUSH.defaultBlockState(), flags);
                            level.setBlock(pos1, Blocks.DIRT.defaultBlockState(), flags);
                        }
                    }
                }
            }
            if (status == FeatheredSheepStatus.FLYING_SLOW && tickCount % 14 == 0) {
                playSound(
                        FantasiaSounds.SHEEP_FLAP.get(),
                        Mth.randomBetween(random, 0.8f, 1.2f),
                        Mth.randomBetween(random, 0.8f, 1.2f)
                );
            } else if (status == FeatheredSheepStatus.FLYING_FAST && tickCount % 7 == 0) {
                playSound(
                        FantasiaSounds.SHEEP_FLAP.get(),
                        Mth.randomBetween(random, 0.8f, 1.2f),
                        Mth.randomBetween(random, 1.5F, 2F)
                );
            } else if (status == FeatheredSheepStatus.WANDERING && tickCount % 21 == 0) {
                playSound(
                        FantasiaSounds.SHEEP_FLAP.get(),
                        Mth.randomBetween(random, 0.3f, 0.6f),
                        Mth.randomBetween(random, 0.3f, 0.6f)
                );
            }
        } else {
//            System.out.println("asd");
        }
    }


    @Override
    public void animateHurt() {

    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.getAnimatable() instanceof FeatheredSheepEntity featheredSheep) {
            if (featheredSheep.status == null) {
                featheredSheep.status = FeatheredSheepStatus.FLYING_FAST;
            }
            switch (featheredSheep.status) {
                case IDLING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
                case DYING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("die"));
                case FLYING_SLOW -> event.getController().setAnimation(new AnimationBuilder().addAnimation("fly_loop"));
                case FLYING_FAST -> event.getController().setAnimation(new AnimationBuilder().addAnimation("skill"));
                case CHARGING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("charge"));
                case SWOOPING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("swoop"));
                case WANDERING -> event.getController().setAnimation(new AnimationBuilder().addAnimation("wander"));
                case FLY_START -> event.getController().setAnimation(new AnimationBuilder().addAnimation("fly_start"));
                case FLY_END -> event.getController().setAnimation(new AnimationBuilder().addAnimation("fly_end"));
            }
        }
        return PlayState.CONTINUE;
    }

    public boolean isFlying() {
        return entityData.get(isFlying);
    }

    public void setFlying(boolean to) {
        entityData.set(isFlying, to);
    }

    public int getTakeOffCoolDown() {
        return entityData.get(takeOffCoolDown);
    }

    public void setTakeOffCoolDown(int to) {
        entityData.set(takeOffCoolDown, to);
    }

    public int getFlyTime() {
        return entityData.get(flyTime);
    }

    public void setFlyTime(int to) {
        entityData.set(flyTime, to);
    }

    public int getCurrentBombingSp() {
        return entityData.get(currentBombingSp);
    }

    public void setCurrentBombingSp(int to) {
        entityData.set(currentBombingSp, to);
    }

}

