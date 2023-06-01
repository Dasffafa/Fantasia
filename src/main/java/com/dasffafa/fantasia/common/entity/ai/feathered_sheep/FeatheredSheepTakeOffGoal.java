package com.dasffafa.fantasia.common.entity.ai.feathered_sheep;

import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepStatus;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class FeatheredSheepTakeOffGoal extends Goal {
    private FeatheredSheepEntity featheredSheep;
    private int executedTicks;
    public FeatheredSheepTakeOffGoal(FeatheredSheepEntity featheredSheep) {
        this.featheredSheep = featheredSheep;
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return !featheredSheep.isFlying() && featheredSheep.getTakeOffCoolDown() == 0;
    }

    @Override
    public void start() {
        super.start();
        executedTicks = 0;
        featheredSheep.status = FeatheredSheepStatus.FLY_START;
    }

    @Override
    public void tick() {
        super.tick();

        if (executedTicks == 10) {
            featheredSheep.status = FeatheredSheepStatus.FLYING_SLOW;
        } else if (executedTicks > 10) {
            featheredSheep.setDeltaMovement(featheredSheep.getDeltaMovement().add(0,0.05,0));
        }
        executedTicks += 1;


    }

    @Override
    public boolean canContinueToUse() {
        return executedTicks >= 30;
    }
}
