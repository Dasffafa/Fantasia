package com.dasffafa.fantasia.common.entity.projectile;

import com.dasffafa.fantasia.common.block.FantasiaBlocks;
import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PrimedDoubleBangFirework extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedDoubleBangFirework.class, EntityDataSerializers.INT);
    private LivingEntity owner = null;

    public PrimedDoubleBangFirework(EntityType<? extends PrimedDoubleBangFirework> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PrimedDoubleBangFirework(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(FantasiaEntities.PRIMED_DOUBLE_BANG_FIREWORK.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double) ((float) Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double) 0.2F, -Math.cos(d0) * 0.02D);
//        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(FantasiaBlocks.DOUBLE_BANG_BLOCKITEM.get(), 1);
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 120);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short) this.getFuse());
    }

    public void explode1() {
        this.level.explode(null, this.getX(), this.getY(), this.getZ(), 2.0F, false, Explosion.BlockInteraction.NONE);
    }

    public void explode2() {
        this.level.explode(null, this.getX(), this.getY(), this.getZ(), 4.0F, false, Explosion.BlockInteraction.NONE);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i == 0) {
            this.discard();
            this.explode2();
        } else if (i == 20) {
            if (!this.level.isClientSide) {
                this.explode1();
                this.setDeltaMovement(this.getDeltaMovement().add(new Vec3(Mth.randomBetween(this.random,-0.2f,0.2f), 1, Mth.randomBetween(this.random,-0.2f,0.2f))));
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.2D, this.getZ(), 0.0D, 0.0D, 0.0D);
                this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 1.3D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
