package com.dasffafa.fantasia.common.entity.projectile;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SheepFeatherEntity extends AbstractArrow {
    private final int MAX_AGE = 800;
    private LivingEntity target;

    private ItemStack itemStack;

    public SheepFeatherEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SheepFeatherEntity(Level pLevel) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), pLevel);
    }

    public SheepFeatherEntity(LivingEntity pShooter, Level pLevel) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), pLevel);
        this.setOwner(pShooter);
    }

    public SheepFeatherEntity(LivingEntity pShooter, Level pLevel, ItemStack itemStack) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), pLevel);
        this.setOwner(pShooter);
        this.itemStack = itemStack;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("target", target != null ? target.toString() : "null");

    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount >= MAX_AGE) this.discard();
        if (this.target == null) {
            if (this.getDeltaMovement().length() > 0.1) {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95));
            }
            if (this.getOwner() instanceof Player player) {
                LivingEntity lastHurtMob = player.getLastHurtMob();
                if (lastHurtMob != null && !lastHurtMob.isDeadOrDying()) {
                    this.setTarget(lastHurtMob);
                }
                LivingEntity lastHurtByMob = player.getLastHurtByMob();
                if (lastHurtByMob != null && !lastHurtByMob.isDeadOrDying()) {
                    this.setTarget(lastHurtByMob);
                }
            }
        } else if (this.tickCount % 4 == 0 && !this.target.isDeadOrDying()) {
            this.shoot(-this.getX() + target.getX(), -this.getY() + target.getEyeY(), -this.getZ() + target.getZ(), 0.8F, 1.0F);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(FantasiaItems.SHEEP_FEATHER.get(), 1);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (pResult.getEntity() != this.getOwner()) {
            pResult.getEntity().invulnerableTime = 0;
            int i = 0, j = 0, k = 0, l = 0;
            if (itemStack != null) {
                i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
                k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, itemStack);
                j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack);
                l = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, itemStack);
            }
            pResult.getEntity().hurt(new IndirectEntityDamageSource(Fantasia.MOD_ID + ".stink", pResult.getEntity(), this.getOwner()), 5 / (2 * i + 1));
            this.discard();
        }
    }
}

