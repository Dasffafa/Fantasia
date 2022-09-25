package com.brotherJiang.fantasia.common.entity.living;

import com.brotherJiang.fantasia.common.config.FantasiaConfigHandler;
import com.brotherJiang.fantasia.common.entity.living.ai.TakeOffGoal;
import com.brotherJiang.fantasia.common.entity.projectile.SheepFeatherEntity;
import com.brotherJiang.fantasia.common.utils.SetBlockStateFlag;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nonnull;

public class FeatheredSheepEntity extends MonsterEntity implements IAnimatable, IRangedAttackMob {
    //If a feathered sheep's takeOffCoolDown is zero and escaped from fight，it will fly again when discovering enemy.
    int takeOffCoolDown;

    //The max SP required for using its skill.
    private static final int wideBombingSPRequirement = FantasiaConfigHandler.FEATHERED_SHEEP_WIDE_BOMB_SP.get();

    //How long has the FeatheredSheep flied.
    int flyingTime;

    private final AnimationFactory anim_factory = new AnimationFactory(this);

    private static final DataParameter<Boolean> isFlying = EntityDataManager.createKey(FeatheredSheepEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> wideBombingSP = EntityDataManager.createKey(FeatheredSheepEntity.class, DataSerializers.VARINT);

    public FeatheredSheepEntity(EntityType<? extends FeatheredSheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly_loop", true));
        } else {
            if (!isSprinting()) {
                if (this.getMotion().length() <= 0.03F) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
                } else {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("wander", true));
                }
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("charge", true));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void travel(@Nonnull Vector3d travelVector) {
        if (!this.isFlying()) {
            super.travel(travelVector);
            return;
        }
        // Copy of FlyingEntity
        if (this.isInWater()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
            float f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(ground).getSlipperiness(this.world, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(ground).getSlipperiness(this.world, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(f));
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        this.setSprinting(true);
        return false;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        BlockPos pos = getPosition();
        Block plant = world.getBlockState(pos).getBlock();
        if (plant instanceof BushBlock && !(plant instanceof DeadBushBlock)) {
            int flags = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
            if (world.getBlockState(pos.down()).matchesBlock(Blocks.DIRT)) {
                world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), flags);
            }
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState(), flags);
        }
        if (!world.isRemote) {
            if (getTakeOffCoolDown() > 0)
                setTakeOffCoolDown(getTakeOffCoolDown() - 1);

            if (this.isFlying() && this.isOnGround())
                this.setFlying(false);

            if (this.isFlying()) {
                flyingTime++;
            } else
                flyingTime = 0;
        }
        if (this.isFlying() && this.ticksExisted % 58 == 0) {
            double d4 = 2 * rand.nextDouble() - 1;
            LivingEntity target = this.getAttackTarget();
            if (target != null && this.getPosY() - target.getPosY() <= 8) {
                this.addVelocity(d4, d4 / 4 + 0.5, d4);
                this.getLookController().setLookPosition(target.getPositionVec());
            } else {
                if (this.getFlyingTime() >= 300) {
                    this.setFlying(false);
                    this.setTakeOffCoolDown(0);
                } else {
                    this.addVelocity(d4, d4 / 2 - 0.2, d4);
                }

            }

        }


    }

    @Override
    public boolean isBurning() {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.registerAttributes()
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.ARMOR, 4.0D)
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.8D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 5.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.3F);
    }

    @Override
    public void attackEntityWithRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        int currentSP = getWideBombingSP();
        if (currentSP < wideBombingSPRequirement) {
            setWideBombingSP(currentSP + 1);
            shootSheepFeather(target, 0, 0);
        } else {
            setWideBombingSP(0);
            double XScatterFactor = 2 * rand.nextDouble() + 0.5;
            double ZScatterFactor = 2 * rand.nextDouble() + 0.5;
            this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    shootSheepFeather(target, i * XScatterFactor, j * ZScatterFactor);
                }
            }
        }
    }

    private void shootSheepFeather(LivingEntity target, double XOffset, double ZOffset) {
        SheepFeatherEntity sheepFeatherEntity = new SheepFeatherEntity(this.world, this);
        sheepFeatherEntity.setAcceleration(0.15F);
        sheepFeatherEntity.setNoGravity(true);
        double d0 = target.getPosYEye() - (double) 1.1F;
        double d1 = target.getPosX() - this.getPosX() + XOffset;
        double d2 = d0 - sheepFeatherEntity.getPosY();
        double d3 = target.getPosZ() - this.getPosZ() + ZOffset;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        sheepFeatherEntity.shoot(d1, d2 + (double) f, d3, 0.2F, 2.0F);
        this.world.addEntity(sheepFeatherEntity);
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<FeatheredSheepEntity> controller = new AnimationController<FeatheredSheepEntity>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.anim_factory;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource source, float amount) {
        if (this.isFlying()) {
            setFlying(false);
            addVelocity(0, -0.2, 0);
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(isFlying, false);
        dataManager.register(wideBombingSP, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new TakeOffGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.6F) {
            @Override
            public void startExecuting() {
                setSprinting(false);
                super.startExecuting();
            }
        });
//        this.goalSelector.addGoal(2,new ChargeGoal(this));
        this.goalSelector.addGoal(1, new SwimGoal(this));
        //TODO: Let feathered sheep be able to avoid projectiles
//        this.goalSelector.addGoal(1, new );
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(2, new RangedAttackOnlyIfFlyingGoal(this, 2, 30, 15));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, FeatheredSheepEntity.class, true));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        dataManager.set(isFlying, compound.getBoolean("is_flying"));
        dataManager.set(wideBombingSP, compound.getInt("sp"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("is_flying", dataManager.get(isFlying));
        compound.putInt("sp", dataManager.get(wideBombingSP));
    }

    public boolean isFlying() {
        return this.dataManager.get(isFlying);
    }

    public void setFlying(boolean flying) {
        this.dataManager.set(isFlying, flying);
    }

    public int getWideBombingSP() {
        return this.dataManager.get(wideBombingSP);
    }

    public void setWideBombingSP(int SP) {
        this.dataManager.set(wideBombingSP, SP);
    }

    public int getTakeOffCoolDown() {
        return takeOffCoolDown;
    }

    public void setTakeOffCoolDown(int takeOffCoolDown) {
        this.takeOffCoolDown = takeOffCoolDown;
    }

    public int getFlyingTime() {
        return flyingTime;
    }

    public void setFlyingTime(int flyingTime) {
        this.flyingTime = flyingTime;
    }

    enum Animation {
        IDLE,
        WANDER,
        CHARGE,
        FLY,
        DIE
    }

    static class RangedAttackOnlyIfFlyingGoal extends RangedAttackGoal {
        private FeatheredSheepEntity featheredSheep;

        public RangedAttackOnlyIfFlyingGoal(FeatheredSheepEntity attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
            super(attacker, movespeed, maxAttackTime, maxAttackDistanceIn);
            this.featheredSheep = attacker;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && featheredSheep.isFlying();
        }
    }
}