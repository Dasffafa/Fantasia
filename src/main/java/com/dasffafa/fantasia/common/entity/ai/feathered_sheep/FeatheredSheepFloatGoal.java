package com.dasffafa.fantasia.common.entity.ai.feathered_sheep;

import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepStatus;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class FeatheredSheepFloatGoal extends FloatGoal {
    private FeatheredSheepEntity featheredSheep;
    public FeatheredSheepFloatGoal(FeatheredSheepEntity featheredSheep) {
        super(featheredSheep);
    }

    @Override
    public void start() {
        super.start();
        featheredSheep.status = FeatheredSheepStatus.WANDERING;
    }
}