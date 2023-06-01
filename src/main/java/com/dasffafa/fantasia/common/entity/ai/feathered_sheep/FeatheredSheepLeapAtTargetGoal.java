package com.dasffafa.fantasia.common.entity.ai.feathered_sheep;

import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepStatus;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;

public class FeatheredSheepLeapAtTargetGoal extends LeapAtTargetGoal {
    private final FeatheredSheepEntity featheredSheep;
    public FeatheredSheepLeapAtTargetGoal(FeatheredSheepEntity pMob, float pYd) {
        super(pMob, pYd);
        this.featheredSheep = pMob;
    }

    @Override
    public void start() {
        super.start();
        this.featheredSheep.status = FeatheredSheepStatus.CHARGING;
    }
}
