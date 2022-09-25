package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.effect.FantasiaEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.NotNull;

public class ExcrementSword extends SwordItem {
    public ExcrementSword() {
        super(FantasiaItemTier.EXCREMENT, 5, 0.8F, new Item.Properties().group(Fantasia.GROUP));
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, LivingEntity target, @NotNull LivingEntity attacker) {

        target.addPotionEffect(new EffectInstance(FantasiaEffects.HICCUP, 100, 0));
        target.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 0));

        return super.hitEntity(stack, target, attacker);
    }
}
