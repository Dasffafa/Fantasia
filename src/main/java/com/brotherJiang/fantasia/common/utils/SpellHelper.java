/*
 * Copyright (c) 2021/7/30 下午10:02.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/bj_magic2.
 */
package com.brotherJiang.fantasia.common.utils;

import com.brotherJiang.fantasia.common.item.FantasiaItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SpellHelper {
    private SpellHelper() { }

    public static void attackEntityWithoutResistance(Entity entity, DamageSource source,float amount){
        if (entity.isLiving()){
            Vector3d prevMotion = entity.getMotion();
            entity.hurtResistantTime = 0;
            entity.attackEntityFrom(source,amount);
            entity.hurtResistantTime = 0;
            entity.setMotion(prevMotion);
        }
    }
    public static void attackEntityWithoutKnockingBack(Entity entity, DamageSource source,float amount){
        if (entity.isLiving()){
            Vector3d prevMotion = entity.getMotion();
            entity.attackEntityFrom(source,amount);
            entity.setMotion(prevMotion);
        }
    }
    public static void harvestBlockWithItemDrops(World world, Entity harvester,BlockPos blockPos) {
        BlockState blockstate = world.getBlockState(blockPos);
        if (blockstate.isAir(world, blockPos)) return;

        FluidState ifluidstate = world.getFluidState(blockPos);
        world.playEvent(2001, blockPos, Block.getStateId(blockstate));
        TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(blockPos) : null;
        Block.spawnDrops(blockstate, world, blockPos, tileentity, harvester, new ItemStack(FantasiaItems.DOUBLE_BANG_POTION.get(), 1));  // FORTUNE and SILK TOUCH enchantments affect drops

        int flags = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
        world.setBlockState(blockPos, ifluidstate.getBlockState(), flags);
    }

    public static double getDistanceXZ(BlockPos pos1,BlockPos pos2){
        return MathHelper.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2));
    }
}
