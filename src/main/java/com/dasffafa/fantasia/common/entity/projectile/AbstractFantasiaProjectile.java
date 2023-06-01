package com.dasffafa.fantasia.common.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFantasiaProjectile extends Projectile {
    // How many times the projectile will reflect on hitting block.
    protected int reflectAbility;
    // How many monsters the projectile will pierce.
    protected int pierceAbility;
    // Set a non-zero value for the projectile to enable homing.
    protected float maxHomingAnglePerSecond;
    // Break blocks with explosion resistance of breakAbility in total.
    protected float breakAbility;
    protected float damage;
    protected float speed;
    protected float acceleration;


    protected AbstractFantasiaProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    protected AbstractFantasiaProjectile(EntityType<? extends Projectile> pEntityType, LivingEntity pShooter,Level pLevel) {
        super(pEntityType, pLevel);
        this.setOwner(pShooter);
    }

    protected void registerParams() {
        this.reflectAbility = 0;
    }
    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("reflectAbility",reflectAbility);
        pCompound.putInt("pierceAbility",pierceAbility);
        pCompound.putFloat("damage",damage);
        pCompound.putFloat("maxHomingAnglePerSecond", maxHomingAnglePerSecond);
        pCompound.putFloat("speed",speed);
        pCompound.putFloat("acceleration",acceleration);
        pCompound.putFloat("breakAbility",breakAbility);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.reflectAbility = pCompound.getInt("reflectAbility");
        this.pierceAbility = pCompound.getInt("pierceAbility");
        this.damage = pCompound.getFloat("damage");
        this.maxHomingAnglePerSecond = pCompound.getFloat("maxHomingAnglePerSecond");
        this.speed = pCompound.getFloat("speed");
        this.acceleration = pCompound.getFloat("acceleration");
        this.breakAbility = pCompound.getFloat("breakAbility");
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        BlockPos pos = pResult.getBlockPos();

        float collidedBlockDestroyTime = level.getBlockState(pos).getBlock().defaultDestroyTime();

    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if (entity instanceof LivingEntity livingentity) {
            entity.hurt(DamageSource.indirectMobAttack(this,livingentity),this.damage);
        }


    }

    @Override
    protected void defineSynchedData() {}
}
