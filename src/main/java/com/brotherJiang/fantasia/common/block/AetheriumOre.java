/*
 * Copyright (c) 2021/7/30 下午3:58.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */

package com.brotherJiang.fantasia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class AetheriumOre extends Block {
    private static final IntegerProperty PURITY = IntegerProperty.create("purity", 1, 5);

    public AetheriumOre() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3));
        this.setDefaultState(getPurityStateByInt(5));
    }

    private BlockState getPurityStateByInt(int purity) {
        return this.stateContainer.getBaseState().with(PURITY, purity);
    }

    private void scheduleOxidizing(@Nonnull World world, @Nonnull BlockPos pos) {
        world.getPendingBlockTicks().scheduleTick(pos, this, 40);
    }

    private static void makeParticles(World world, BlockPos pos,BlockState state) {
        //Copied from vanilla RedstoneOreBlock.java
        // & modified it slightly in order to meet Aetherium 's features better.
        Random random = world.rand;

        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                Direction.Axis direction_axis = direction.getAxis();
                double d1 = direction_axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getXOffset() : (double) random.nextFloat();
                double d2 = direction_axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getYOffset() : (double) random.nextFloat();
                double d3 = direction_axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getZOffset() : (double) random.nextFloat();
                for (int i=0; i <= state.get(PURITY); i++){
                    world.addParticle(ParticleTypes.SOUL,
                            (double) pos.getX() + d1,
                            (double) pos.getY() + d2,
                            (double) pos.getZ() + d3,
                            2*(random.nextFloat()-0.5), -1D, 2*(random.nextFloat()-0.5));
                }
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PURITY);
        super.fillStateContainer(builder);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (state.get(PURITY) > 1) {
            world.setBlockState(pos, getPurityStateByInt(state.get(PURITY) - 1));
            scheduleOxidizing(world, pos);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        makeParticles(world,pos,state);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(PURITY) * 3;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockClicked(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player) {
        scheduleOxidizing(world, pos);
    }

    @Override
    public void onEntityWalk(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        scheduleOxidizing(world, pos);
    }
}


