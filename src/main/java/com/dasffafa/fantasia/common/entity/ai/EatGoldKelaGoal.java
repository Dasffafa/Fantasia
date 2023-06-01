package com.dasffafa.fantasia.common.entity.ai;

import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.item.GoldKela;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EatGoldKelaGoal extends Goal {
    private final LivingEntity entity;
    private int eatTime = 0;
    public EatGoldKelaGoal(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public void start() {
        super.start();
        this.eatTime = 0;
    }

    @Override
    public boolean canUse() {
//        如果目标的手里拿着金坷垃，就会试图在2.5秒内吃掉金坷垃
        return this.entity.getItemInHand(InteractionHand.MAIN_HAND).is(FantasiaItems.GOLD_KELA.get())
                && this.entity.getHealth() / this.entity.getMaxHealth() <= 0.75;
    }

    @Override
    public void tick() {
        super.tick();
        this.eatTime++;
        if (this.eatTime >= 50) {
            GoldKela.applyGoldKelaEffect(this.entity);
        }
        if (this.eatTime % 10 == 0) {
            this.entity.level.playSound(
                    null,
                    this.entity.getOnPos(),
                    SoundEvents.GENERIC_EAT,
                    SoundSource.HOSTILE,
                    1.0F,
                    Mth.randomBetween(this.entity.getRandom(),0.5f,1.5f));
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.entity.getItemInHand(InteractionHand.MAIN_HAND).is(FantasiaItems.GOLD_KELA.get()) && this.eatTime < 50;
    }
}
