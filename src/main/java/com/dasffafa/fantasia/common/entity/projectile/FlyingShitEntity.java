package com.dasffafa.fantasia.common.entity.projectile;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.effects.FantasiaEffects;
import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.utils.FantasiaDamageSources;
import com.dasffafa.fantasia.common.utils.FantasiaSounds;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FlyingShitEntity extends ThrowableItemProjectile implements ItemSupplier {
    public FlyingShitEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setItem(new ItemStack(FantasiaItems.FLYING_SHIT.get()));
    }

    public FlyingShitEntity(Level pLevel) {
        super(FantasiaEntities.FLYING_SHIT.get(), pLevel);
        this.setItem(new ItemStack(FantasiaItems.FLYING_SHIT.get()));
    }

    public FlyingShitEntity(LivingEntity pShooter, Level pLevel) {
        super(FantasiaEntities.FLYING_SHIT.get(), pShooter, pLevel);
        this.setItem(new ItemStack(FantasiaItems.FLYING_SHIT.get()));
    }

    @Override
    protected Item getDefaultItem() {
        return FantasiaItems.FLYING_SHIT.get();
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            var particle = new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
            this.level.playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    FantasiaSounds.SHIT_HIT.get(),
                    SoundSource.PLAYERS,
                    1.0f, 1.0f
            );
            for (var i = 0; i < 16; ++i) {
                this.level.addParticle(particle,
                        this.getX(), this.getY(), this.getZ(),
                        0.2 * (0.5 - this.random.nextDouble()),
                        0.3 * this.random.nextDouble(),
                        0.2 * (0.5 - this.random.nextDouble())
                );
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(),FantasiaSounds.SHIT_HIT.get(),SoundSource.VOICE,1.0f,1.0f);
        this.level.broadcastEntityEvent(this, (byte) 3);
        if (result.getType() == HitResult.Type.ENTITY) {
            var entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                applyShitEffect(livingEntity);
                if (this.getOwner() != null) {
                    livingEntity.hurt(new IndirectEntityDamageSource(Fantasia.MOD_ID+".stink", entity, this.getOwner()),5);
                } else {
                    livingEntity.hurt(FantasiaDamageSources.STINK, 5);
                }
            }
        }
        this.discard();
    }

    public static void applyShitEffect(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(FantasiaEffects.HICCUP.get(), 120, 0));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER,120,0));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 120, 0));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 0));
    }
}
