package com.dasffafa.fantasia.common.item.tools;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.projectile.FlyingShitEntity;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.item.FantasiaNBTTags;
import com.dasffafa.fantasia.common.network.FantasiaNetworking;
import com.dasffafa.fantasia.common.utils.SpellHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SHeepITemSword extends SwordItem {
    public SHeepITemSword() {
        super(FantasiaItemTiers.SHEEP_FEATHER, 2, 0.7F, new Properties().tab(Fantasia.TAB));
    }

    public boolean hasInformedBloodThirst(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getBoolean(FantasiaNBTTags.INFORMED_BLOODTHIRST);
    }

    public void setInformedBloodThirst(ItemStack stack, boolean flag) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putBoolean(FantasiaNBTTags.INFORMED_BLOODTHIRST, flag);
    }

    public float getAccumulatedAttack(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getFloat(FantasiaNBTTags.STORED_ATTACK_TIMES);
    }

    public void setAccumulatedAttack(ItemStack stack, float times) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putFloat(FantasiaNBTTags.STORED_ATTACK_TIMES, times);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        SpellHelper.attackEntityWithoutResistance(pTarget, DamageSource.indirectMobAttack(pTarget, pAttacker), Mth.sqrt(getAccumulatedAttack(pStack)));
        CompoundTag tag = pStack.getOrCreateTag();
        float cur = tag.getFloat(FantasiaNBTTags.STORED_ATTACK_TIMES);
        tag.putFloat(FantasiaNBTTags.STORED_ATTACK_TIMES, cur + 1);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity.tickCount % 20 == 0) {
            if (pEntity instanceof Player player) {
                List<LivingEntity> nearbyEntities = SpellHelper.getNearbyEntities(player, 2);

                if (nearbyEntities.size() != 0 && player.getItemInHand(InteractionHand.MAIN_HAND).is(FantasiaItems.SHEEP_FEATHER_SWORD.get())) {

                    if (!pLevel.isClientSide) {
                        player.swing(InteractionHand.MAIN_HAND);
                        FantasiaNetworking.INSTANCE.send(
                                PacketDistributor.TRACKING_CHUNK.with(() -> pLevel.getChunkAt(player.getOnPos())), "fantasia.networking.sheep_feather_sword_bloodthirst");
                        hurtEnemy(pStack, nearbyEntities.get(0), player);
                        nearbyEntities.get(0).hurt(DamageSource.indirectMobAttack(pEntity, player), getDamage());
                    }

                }
            } else {
                pEntity.hurt(DamageSource.indirectMobAttack(pEntity, (LivingEntity) pEntity), pStack.getDamageValue());
            }
        } else if (pEntity.tickCount % 10 == 9) {
            setAccumulatedAttack(pStack, 0.99f * getAccumulatedAttack(pStack));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.tickCount % 6 == 0 && getAccumulatedAttack(stack) >= 0.5) {
            setAccumulatedAttack(stack, getAccumulatedAttack(stack) - 0.5f);
            if (!pPlayer.level.isClientSide) {
                FlyingShitEntity flyingShit = new FlyingShitEntity(pPlayer, pPlayer.level);
                flyingShit.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 6.0F);
                pPlayer.level.addFreshEntity(flyingShit);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents
                .add(new TranslatableComponent("item.fantasia.sheep_item_sword.desc1"));
        pTooltipComponents
                .add(new TranslatableComponent("item.fantasia.sheep_item_sword.stored_attacks", getAccumulatedAttack(pStack)));
        pTooltipComponents
                .add(new TranslatableComponent("item.fantasia.sheep_item_sword.attack_bonus", (Mth.sqrt(getAccumulatedAttack(pStack)))));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
}
