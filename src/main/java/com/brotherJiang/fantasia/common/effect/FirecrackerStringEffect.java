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

import javax.annotation.Nonnull;
import java.util.Random;

public class FirecrackerStringEffect extends Effect {
    public FirecrackerStringEffect() {
        super(EffectType.HARMFUL, 0xff0000);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity drinker, int amplifier) {
        World world = drinker.getEntityWorld();
        Random rand = world.getRandom();
        drinker.hurtResistantTime = 0;
        world.createExplosion(null,
                drinker.getPosX()+3*(rand.nextFloat()-0.5),
                drinker.getPosY()+rand.nextFloat()-0.25,
                drinker.getPosZ()+3*(rand.nextFloat()-0.5),
                0.5F, Explosion.Mode.DESTROY);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % (10/(amplifier + 1)) == 0;
    }
}
