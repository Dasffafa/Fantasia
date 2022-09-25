package com.brotherJiang.fantasia.common.entity.living.ai;

import com.brotherJiang.fantasia.common.item.GoldKela;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class UseGoldKelaGoal extends Goal {
    private final LivingEntity goldKelaUser;
    private int eatTicks;
    private int currentEatTicks;
    public UseGoldKelaGoal(LivingEntity goldKelaUser, int eatTicks) {
        this.goldKelaUser = goldKelaUser;
        this.eatTicks = eatTicks;
        this.currentEatTicks = 0;
        //While eating gold kela, living entities cannot do everything but after their successful eating KABOOM!!
        setMutexFlags(EnumSet.of(Flag.MOVE,Flag.JUMP,Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        return goldKelaUser.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GoldKela
                && goldKelaUser.getHealth() <= 0.33F * goldKelaUser.getMaxHealth();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }

    @Override
    public void tick() {
        super.tick();
        //TODO:Finish UseKelaGoal
//        if ()
    }
}
