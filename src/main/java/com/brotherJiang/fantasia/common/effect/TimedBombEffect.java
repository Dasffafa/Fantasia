/*
 * Copyright (c) 2021/7/30 下午9:36.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */
package com.brotherJiang.fantasia.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TimedBombEffect extends Effect {
    public TimedBombEffect() {
        super(EffectType.HARMFUL, 0xee2233);
    }
    @SubscribeEvent
    @SuppressWarnings("all")
    public static void onPotionExpiry(PotionEvent.PotionExpiryEvent event){
        if (event.getPotionEffect().getPotion().equals(FantasiaEffects.TIMED_BOMB)){
            World worldIn = event.getEntity().world;
            LivingEntity entityIn = event.getEntityLiving();
            int amplifierIn = event.getPotionEffect().getAmplifier() + 1;
            worldIn.createExplosion(null,entityIn.getPosX(),entityIn.getPosY(),entityIn.getPosZ(),2*amplifierIn, Explosion.Mode.BREAK);
        }
    }
}
