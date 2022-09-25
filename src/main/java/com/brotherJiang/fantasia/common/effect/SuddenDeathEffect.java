/*
 * Copyright (c) 2021/7/30 下午9:36.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */
package com.brotherJiang.fantasia.common.effect;

import com.brotherJiang.fantasia.common.utils.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SuddenDeathEffect extends Effect {
    public SuddenDeathEffect() {
        super(EffectType.HARMFUL, 0x4f0277);
    }
    @SubscribeEvent
    @SuppressWarnings("all")
    public static void onPotionExpiry(PotionEvent.PotionExpiryEvent event){
        if (event.getPotionEffect().getPotion().equals(FantasiaEffects.SUDDEN_DEATH)){
            LivingEntity drinker = event.getEntityLiving();
            int amplifier = event.getPotionEffect().getAmplifier() + 1;
            drinker.attackEntityFrom(ModDamageSources.sudden_death,5*amplifier);
        }
    }
}
