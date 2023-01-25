package com.dasffafa.fantasia.common.item.tools;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.client.particle.FantasiaParticles;
import com.dasffafa.fantasia.common.entity.projectile.FlyingShitEntity;
import com.dasffafa.fantasia.common.item.FantasiaNBTTags;
import com.dasffafa.fantasia.common.utils.SpellHelper;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PoopSword extends SwordItem {
    public PoopSword() {
        super(FantasiaItemTiers.POOP, 1, 0.2f, new Properties().tab(Fantasia.TAB));
    }
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity swinger) {
        if (stack.getUseDuration() == stack.getMaxDamage() && swinger instanceof Player && swinger.level.isClientSide ) {
            swinger.sendMessage(new TranslatableComponent("item.fantasia.poop_sword.on_swing"),swinger.getUUID());
        }

        if (isGlowing(stack)) {
            List<LivingEntity> nearbyEntities = SpellHelper.getNearbyEntities(swinger, 8.0D);
            genSplashingShitParticles(swinger.level,swinger);
            for (LivingEntity entity : nearbyEntities) {
                stack.hurtAndBreak(2, swinger, (p_43076_) -> p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                FlyingShitEntity.applyShitEffect(entity);
                genSplashingShitParticlesTo(swinger.level,swinger,entity);
                entity.hurt(new IndirectEntityDamageSource(Fantasia.MOD_ID+".stink",entity,swinger),5);
            }
        }
        return true;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return isGlowing(pStack);
    }

    public boolean isGlowing(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getBoolean(FantasiaNBTTags.POOP_SWORD_GLOWING);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        CompoundTag tag = itemInHand.getOrCreateTag();
        if (isGlowing(itemInHand)) {
            pPlayer.displayClientMessage(new TranslatableComponent("item.fantasia.poop_sword.toggle_off"),true);
        } else {
            pPlayer.displayClientMessage(new TranslatableComponent("item.fantasia.poop_sword.toggle_on"),true);
        }
        tag.putBoolean(FantasiaNBTTags.POOP_SWORD_GLOWING,!isGlowing(itemInHand));

        return InteractionResultHolder.success(itemInHand);
    }

    private static void genSplashingShitParticles(Level pLevel, LivingEntity pPlayer) {
        float pX = pPlayer.getXRot();
        float pY = pPlayer.getYRot();
        float pZ = 0.00F;
        float fx = -Mth.sin(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        float fy = -Mth.sin((pX + pZ) * ((float) Math.PI / 180F));
        float fz = Mth.cos(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        for (int i = 0; i < 30; i++) {
            pLevel.addParticle(
                    FantasiaParticles.SPLASHING_SHIT_PARTICLE.get(),
                    pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ(),
                    3* pLevel.getRandom().nextFloat() * (fx),
                    fy+ pLevel.getRandom().nextFloat(),
                    3* pLevel.getRandom().nextFloat() * (fz)
            );
        }
    }

    private static void genSplashingShitParticlesTo(Level pLevel, LivingEntity pPlayer, LivingEntity pTarget) {
        Vector3f vec = new Vector3f(
                (float) (pTarget.getX() - pPlayer.getX()),
                (float) (pTarget.getY() - pPlayer.getY()),
                (float) (pTarget.getZ() - pPlayer.getZ())
        );
        vec.normalize();
        for (int i = 0; i < 30; i++) {
            pLevel.addParticle(
                    FantasiaParticles.SPLASHING_SHIT_PARTICLE.get(),
                    pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ(),
                    3* pLevel.getRandom().nextFloat() * (vec.x()),
                    vec.y()+ pLevel.getRandom().nextFloat(),
                    3* pLevel.getRandom().nextFloat() * (vec.z())
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("item.fantasia.poop_sword.desc").withStyle(ChatFormatting.BLUE));
    }
}
