package com.brotherJiang.fantasia.common.entity.living.ai;

import com.brotherJiang.fantasia.common.entity.living.FeatheredSheepEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class TakeOffGoal extends Goal {
    FeatheredSheepEntity featheredSheep;

    public TakeOffGoal(FeatheredSheepEntity featheredSheep) {
        this.featheredSheep = featheredSheep;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public void startExecuting() {
        featheredSheep.setTakeOffCoolDown(150);
        featheredSheep.addVelocity(0, 0.7, 0);
        featheredSheep.setFlying(true);
        net.minecraftforge.common.ForgeHooks.onLivingJump(featheredSheep);
//        Networking.sendToNearby(stalker.world, stalker, new PacketAnimEntity(stalker.getId(), FeatheredSheepEntity.Animations.FLY.ordinal()));
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (featheredSheep.getFlyingTime() < 20) {
            featheredSheep.addVelocity(0, 0.1, 0);
        }
    }

    @Override
    public boolean shouldExecute() {
        return featheredSheep.getAttackTarget() != null && !featheredSheep.isFlying() && featheredSheep.getTakeOffCoolDown() == 0;
    }
}
