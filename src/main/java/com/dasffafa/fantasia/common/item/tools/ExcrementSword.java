package com.dasffafa.fantasia.common.item.tools;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.projectile.FlyingShitEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ExcrementSword extends SwordItem {
    public ExcrementSword() {
        super(FantasiaItemTiers.EXCREMENT, 2, 1.2F, new Item.Properties().tab(Fantasia.TAB));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        FlyingShitEntity.applyShitEffect(pTarget);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("item.fantasia.excrement_sword.desc")
                .withStyle(ChatFormatting.BLUE));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
