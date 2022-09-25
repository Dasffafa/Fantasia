/*
 * Copyright (c) 2021/7/30 下午3:58.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */
package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.effect.FantasiaEffects;
import com.brotherJiang.fantasia.common.utils.FantasiaSoundTypes;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SkyMonkeyPotion extends Item {
    private static final Supplier<EffectInstance> effectSupplier = () ->
            new EffectInstance(FantasiaEffects.TIMED_BOMB,40,2);
    private static final Food foodProperty = (new Food.Builder())
            .saturation(0)
            .hunger(0)
            .effect(effectSupplier,1)
            .setAlwaysEdible()
            .build();
    public SkyMonkeyPotion() {
        super(new Properties().food(foodProperty).group(Fantasia.GROUP));
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity drinker) {
        if (!world.isRemote && drinker instanceof PlayerEntity){
            ((PlayerEntity) drinker).sendStatusMessage(new TranslationTextComponent("item.fantasia.sky_monkey_potion.use"),false);
        }
        Vector3d motion = drinker.getMotion();
        drinker.setMotion(motion.add(0,5,0));
        drinker.playSound(FantasiaSoundTypes.SKY_MONKEY.get(),1.0F,1.0F);
        return super.onItemUseFinish(stack, world, drinker);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.fantasia.sky_monkey_potion.desc")
                .mergeStyle(TextFormatting.BLUE,TextFormatting.ITALIC));
    }

    @Override
    @Nonnull
    public UseAction getUseAction(@Nonnull ItemStack stack) {
        return UseAction.DRINK;
    }
}
