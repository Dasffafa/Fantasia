/*
 * Copyright (c) 2021/7/30 下午9:36.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */
package com.brotherJiang.fantasia.common.effect;

import com.brotherJiang.fantasia.common.utils.FantasiaSoundTypes;
import com.brotherJiang.fantasia.common.utils.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.Random;

public class HiccupEffect extends Effect {

    public HiccupEffect() {
        super(EffectType.HARMFUL, 0X123456);
    }
    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        amplifier ++;
        entityLivingBaseIn.playSound(FantasiaSoundTypes.HICCUP.get(), 1.0F,1.0F);
        Random random = new Random();

        entityLivingBaseIn.setMotion(
                entityLivingBaseIn.getMotion().add(
                0.3*amplifier*random.nextFloat()-0.15 ,0.25*amplifier,0.3*amplifier*random.nextFloat()-0.15));
        switch (random.nextInt(5)){
            case 1:
                if (entityLivingBaseIn instanceof PlayerEntity){
                    PlayerEntity player = (PlayerEntity) entityLivingBaseIn;
                    player.dropItem(player.getHeldItemMainhand(),true,false);
                    player.setHeldItem(Hand.MAIN_HAND,new ItemStack(Items.AIR));
                    if (player.world.isRemote){
                        player.sendMessage(new TranslationTextComponent("effect.fantasia.hiccup.drop"),player.getUniqueID());
                    }
                }
            case 2:
                entityLivingBaseIn.attackEntityFrom(ModDamageSources.hiccup,3*amplifier);
        }

        super.performEffect(entityLivingBaseIn, amplifier);
    }

    @Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 40 == 0;
    }
}