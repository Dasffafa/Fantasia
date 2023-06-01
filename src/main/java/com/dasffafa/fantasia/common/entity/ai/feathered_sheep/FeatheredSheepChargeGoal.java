package com.dasffafa.fantasia.common.entity.ai.feathered_sheep;

import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FeatheredSheepChargeGoal extends Goal {
    private float speedModifier;
    private FeatheredSheepEntity featheredSheep;

    private BlockPos targetPos;
    public FeatheredSheepChargeGoal(FeatheredSheepEntity featheredSheep, float speedModifier) {
        this.featheredSheep = featheredSheep;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE,Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return !featheredSheep.isFlying() && featheredSheep.getTarget() != null && !featheredSheep.getTarget().isDeadOrDying();
    }

    @Override
    public void start() {
        super.start();
        if (featheredSheep.getTarget() != null) targetPos = featheredSheep.getTarget().getOnPos();
        else return;

        featheredSheep.status = FeatheredSheepStatus.CHARGING;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }
}
