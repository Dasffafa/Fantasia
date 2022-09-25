/*
 * Copyright (c) 2021/7/31 下午8:53.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/bj_magic2.
 */
package com.brotherJiang.fantasia.common.entity.projectile;

import com.brotherJiang.fantasia.common.utils.Quaternion;
import com.brotherJiang.fantasia.common.utils.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class ProjectileBase extends DamagingProjectileEntity {
    /*
     * Default by 0,this parameter is used to describe how many times it can bounce while hitting
     * ground.*/
    private static final DataParameter<Integer> REFLECT_ABILITY = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.VARINT);
    /*
     * Default by 0, this parameter describes how many degrees it can rotate in ONE SECOND.
     * This means it should be divided by 20 when you want to update entity per tick.
     */
    private static final DataParameter<Float> HOMING_ABILITY = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.FLOAT);
    /*
     * Default by 0, this parameter describes how many enemies it can pierce.
     * For example, the projectile will pierce one enemy and vanish after impacting the second one
     * if this param is set to 1.
     */
    private static final DataParameter<Integer> PIERCE_ABILITY = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.VARINT);
    private static final DataParameter<Float> SPEED = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> BREAK_ABILITY = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.FLOAT);
    //m/s²
    private static final DataParameter<Float> ACCELERATION = EntityDataManager.createKey(ProjectileBase.class, DataSerializers.FLOAT);
    private final int MAX_LIFE = 200;//10 seconds
    public Entity target;
    private Entity shooter;

    public ProjectileBase(EntityType<? extends ProjectileBase> entityType, LivingEntity shooter, World worldIn) {
        super(entityType, shooter, 0, 0, 0, worldIn);
        this.shooter = shooter;
    }

    @OnlyIn(Dist.CLIENT)
    public ProjectileBase(EntityType<? extends ProjectileBase> entityType, double x, double y, double z, World worldIn) {
        super(entityType, x, y, z, 0, 0, 0, worldIn);
    }

    public ProjectileBase(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(REFLECT_ABILITY, 0);
        dataManager.register(HOMING_ABILITY, 0.0F);
        dataManager.register(PIERCE_ABILITY, 0);
        dataManager.register(DAMAGE, 0.0F);
        dataManager.register(SPEED, 2.5F);
        dataManager.register(BREAK_ABILITY, 0.2F);//Is able to destroy a leaf
        dataManager.register(ACCELERATION, 0.0F);

    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        dataManager.set(REFLECT_ABILITY, compound.getInt("reflect_ability"));
        dataManager.set(HOMING_ABILITY, compound.getFloat("is_homing"));
        dataManager.set(PIERCE_ABILITY, compound.getInt("pierce_ability"));
        dataManager.set(DAMAGE, compound.getFloat("damage"));
        dataManager.set(SPEED, compound.getFloat("speed"));
        dataManager.set(BREAK_ABILITY, compound.getFloat("break_ability"));
        dataManager.set(ACCELERATION, compound.getFloat("acceleration"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("reflect_ability",dataManager.get(REFLECT_ABILITY));
        compound.putFloat("is_homing",dataManager.get(HOMING_ABILITY));
        compound.putInt("pierce_ability",dataManager.get(PIERCE_ABILITY));
        compound.putFloat("damage", dataManager.get(DAMAGE));
        compound.putFloat("speed", dataManager.get(SPEED));
        compound.putFloat("break_ability", dataManager.get(BREAK_ABILITY));
        compound.putFloat("acceleration", dataManager.get(ACCELERATION));
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @Nonnull
    protected abstract IParticleData getParticle();

    @Override
    public boolean isBurning() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    protected void makeParticles(){};

    protected void onImpactWithBlock(BlockRayTraceResult result) {
        BlockPos collidedPos = result.getPos();
//
        float collidedBlockHardness = world.getBlockState(collidedPos).getBlockHardness(world, collidedPos);
        if (0 < collidedBlockHardness && collidedBlockHardness<= getBreakAbility()) {
            SpellHelper.harvestBlockWithItemDrops(world, this, collidedPos);
            setBreakAbility(getBreakAbility()-collidedBlockHardness);
        } else {
            makeParticles();
            if (!world.isRemote) {
                affectBlock(world, collidedPos);
                world.setEntityState(this, (byte) 3);
                remove();
            }
        }
    }

    protected void onImpactWithEntity(EntityRayTraceResult result) {
        Entity collidedEntity = result.getEntity();
        if (!world.isRemote && collidedEntity != this.shooter) {
            affectEntity(collidedEntity);
            setPierceAbility(getPierceAbility()-1);
            if (getPierceAbility() < 0) {
                world.setEntityState(this, (byte) 3);
                remove();
            }
        } else {
            makeParticles();
        }
    }

    /**
     * @param entity The affected entity.
     *               The process of attacking entity should be called inside this func.
     **/
    protected abstract void affectEntity(Entity entity);
    /**
     * @param world world of the affected block.
     * @param pos The pos of the affected block.
     **/
    protected abstract void affectBlock(World world, BlockPos pos);

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        //Don't impact on another projectiles to diminish themselves
        if (result instanceof EntityRayTraceResult && ((EntityRayTraceResult) result).getEntity() instanceof LivingEntity) {
            onImpactWithEntity((EntityRayTraceResult) result);
        } else if (result instanceof BlockRayTraceResult) {
            onImpactWithBlock((BlockRayTraceResult) result);
        }
    }

    @Override
    public void tick() {
        super.tick();

        //Destroy this projectile if it exists too long
        if (ticksExisted >= MAX_LIFE) {
            if (!world.isRemote) {
                world.setEntityState(this, (byte) 3);
                remove();
            }
        }
        //Handle homing
        if (target != null && getHomingAbility() > 0){
            Vector3d currentMotion = getMotion();
            Vector3d targetMotion = new Vector3d(
                    -getPosX()+target.getPosX(),
                    -getPosY()+target.getPosY()+target.getEyeHeight(),
                    -getPosZ()+target.getPosZ())
                    .normalize().scale(currentMotion.length());
            setMotion(Quaternion.moveQ(currentMotion,targetMotion,getHomingAbility()));
        }
        //Handle acceleration
        if (getAcceleration() != 0){
            Vector3d motion = getMotion();
            setMotion(motion.normalize().scale(motion.length()+getAcceleration()));
        }
        //Handle gravity
        if (!hasNoGravity()){
            addVelocity(0, -getGravity(), 0);
        }
    }

    //Getters and Setters

    public float getSpeed() {
        return dataManager.get(SPEED);
    }
    public void setSpeed(float speed){
        dataManager.set(SPEED,speed);
        this.setMotion(getMotion().normalize().scale(speed));
    }

    public float getDamage() {
        return dataManager.get(DAMAGE);
    }
    public void setDamage(float damage) {
        dataManager.set(DAMAGE, damage);
    }
    public float getAcceleration() {
        return dataManager.get(ACCELERATION);
    }
    public void setAcceleration(float acceleration) {
        dataManager.set(ACCELERATION, acceleration);
    }
    public float getHomingAbility() {
        return dataManager.get(DAMAGE);
    }
    public void setHomingAbility(float homingAbility) {
        dataManager.set(DAMAGE, homingAbility);
    }
    public float getBreakAbility() {
        return dataManager.get(BREAK_ABILITY);
    }

    public void setBreakAbility(float breakAbility) {
        dataManager.set(BREAK_ABILITY, breakAbility);
    }

    public int getPierceAbility() {
        return dataManager.get(PIERCE_ABILITY);
    }

    public void setPierceAbility(int pierceAbility) {
        dataManager.set(PIERCE_ABILITY, pierceAbility);
    }

    public Entity getShooter() {
        return shooter;
    }

    public void setShooter(Entity shooter) {
        this.shooter = shooter;
    }

    /**
     * This method describes how it is affected by gravity.
     * return positive value to let it fall while flying.
     * may be rewritten in later versions.
     **/
    public float getGravity() {
        return 0.03F;
    }

}