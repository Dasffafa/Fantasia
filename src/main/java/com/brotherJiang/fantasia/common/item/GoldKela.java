/*
 * Copyright (c) 2021/8/3 下午2:51.
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class GoldKela extends Item {
    private static final Food foodProperty = (new Food.Builder())
            .saturation(999)
            .hunger(999)
            .setAlwaysEdible()
            .build();
    public GoldKela() {
        super(
                new Properties().maxStackSize(1).group(Fantasia.GROUP).food(foodProperty));
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity eater) {
        eater.playSound(FantasiaSoundTypes.GOLD_KELA_USE.get(), 1.0F, 1.0F);
        eater.addPotionEffect(new EffectInstance(Effects.REGENERATION,1200,1));
        eater.addPotionEffect(new EffectInstance(Effects.GLOWING,1200));
        eater.addPotionEffect(new EffectInstance(Effects.HASTE,1200));
        eater.addPotionEffect(new EffectInstance(Effects.STRENGTH,1200,2));
        eater.addPotionEffect(new EffectInstance(Effects.SATURATION,1200));
        eater.addPotionEffect(new EffectInstance(FantasiaEffects.HICCUP,1200,1));
        if (world.isRemote && eater instanceof PlayerEntity){
            ((PlayerEntity) eater).sendStatusMessage(new TranslationTextComponent("item.fantasia.gold_kela.use"),false);
        }
        return super.onItemUseFinish(stack, world, eater);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.fantasia.gold_kela.desc")
                .mergeStyle(TextFormatting.YELLOW,TextFormatting.BOLD));
    }
}
