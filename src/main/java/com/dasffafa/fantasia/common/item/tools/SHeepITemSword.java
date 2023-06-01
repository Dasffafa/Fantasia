package com.dasffafa.fantasia.common.item.tools;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.projectile.SheepFeatherEntity;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.utils.FantasiaNBTTags;
import com.dasffafa.fantasia.common.item.SheepFeatherItem;
import com.dasffafa.fantasia.common.utils.SpellHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SHeepITemSword extends SwordItem {
    public SHeepITemSword() {
        super(FantasiaItemTiers.SHEEP_FEATHER, 2,-2.0F, new Properties().tab(Fantasia.TAB));
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
                    player.swing(InteractionHand.MAIN_HAND);
                    if (!pLevel.isClientSide) {
                        hurtEnemy(pStack, nearbyEntities.get(0), player);
                        nearbyEntities.get(0).hurt(DamageSource.indirectMobAttack(pEntity, player), getDamage());
                    }
                }
            } else {
                // if sheep feather sword is held by a non-player entity it will
                // automatically attack it because mobs cannot control the sword as players do.
                pEntity.hurt(DamageSource.indirectMobAttack(pEntity, (LivingEntity) pEntity), pStack.getDamageValue());
            }
        } else if (pEntity.tickCount % 10 == 9) {
            setAccumulatedAttack(pStack, 0.99f * getAccumulatedAttack(pStack));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.swing(pUsedHand);
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        int multishotLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        if (getAccumulatedAttack(stack) >= 0.5) {
            setAccumulatedAttack(stack, getAccumulatedAttack(stack) - 0.23f);
            if (!pPlayer.level.isClientSide) {
                for (int i = 0; i < multishotLevel + 1; i++) {
                    SheepFeatherEntity sheepFeather = SheepFeatherItem.createSheepFeather(pLevel, pPlayer);
                    sheepFeather.pickup = AbstractArrow.Pickup.DISALLOWED;
                    sheepFeather.setNoGravity(true);
                    sheepFeather.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.5F, 40F);
                    pLevel.addFreshEntity(sheepFeather);
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents
                .add(new TranslatableComponent("item.fantasia.sheep_item_sword.desc1").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD));
        pTooltipComponents
                .add(new TranslatableComponent("item.fantasia.sheep_item_sword.stored_attacks", Mth.floor(getAccumulatedAttack(pStack))).withStyle(ChatFormatting.RED));
        pTooltipComponents
                .add(new TranslatableComponent("item.fantasia.sheep_item_sword.attack_bonus", (Mth.floor(Mth.sqrt(getAccumulatedAttack(pStack))))).withStyle(ChatFormatting.RED));
    }

    @Override
    public Component getName(ItemStack pStack) {
        return new TranslatableComponent(this.getDescriptionId(pStack))
                .withStyle(ChatFormatting.YELLOW);
    }
}
