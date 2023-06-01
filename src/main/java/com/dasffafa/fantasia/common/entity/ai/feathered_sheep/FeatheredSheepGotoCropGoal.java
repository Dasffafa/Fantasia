package com.dasffafa.fantasia.common.entity.ai.feathered_sheep;

import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DeadBushBlock;

public class FeatheredSheepGotoCropGoal extends MoveToBlockGoal {
    private final FeatheredSheepEntity featheredSheep;

    public FeatheredSheepGotoCropGoal(FeatheredSheepEntity featheredSheep, double pSpeedModifier, int pSearchRange) {
        super(featheredSheep, pSpeedModifier, pSearchRange);
        this.featheredSheep = featheredSheep;
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        Block block = pLevel.getBlockState(pPos).getBlock();
        return block instanceof BushBlock && !(block instanceof DeadBushBlock);
    }
    @Override
    public void start() {
        super.start();
        featheredSheep.status = FeatheredSheepStatus.WANDERING;
    }
}
