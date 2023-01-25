package com.dasffafa.fantasia.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SpellHelper {
    private SpellHelper() { }
    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void attackEntityWithoutResistance(LivingEntity entity, DamageSource source, float amount){
        Vec3 prevMotion = entity.getDeltaMovement();
        entity.invulnerableTime = 0;
        entity.hurt(source,amount);
        entity.invulnerableTime= 0;
        entity.setDeltaMovement(prevMotion);
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void attackEntityWithoutKnockingBack(LivingEntity entity, DamageSource source,float amount){
        Vec3 prevMotion = entity.getDeltaMovement();
        entity.hurt(source,amount);
        entity.setDeltaMovement(prevMotion);
    }
    public static void harvestBlockWithItemDrops(Level world, LivingEntity harvester, BlockPos blockPos) {
        BlockState blockstate = world.getBlockState(blockPos);
        if (blockstate.isAir()) return;

        FluidState ifluidstate = world.getFluidState(blockPos);
//        world.blockEvent(blockPos, world.getBlockState(blockPos).getBlock(),2001);
//        TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(blockPos) : null;
//        Block.spawnDrops(blockstate, world, blockPos, tileentity, harvester, new ItemStack(FantasiaItems.DOUBLE_BANG_POTION.get(), 1));  // FORTUNE and SILK TOUCH enchantments affect drops
//
//        int flags = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
//        world.setBlockState(blockPos, ifluidstate.getBlockState(), flags);
    }

    public static double getDistanceXZ(BlockPos pos1,BlockPos pos2){
        return Mth.sqrt((float) (Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2)));
    }
    public static List<LivingEntity> getNearbyEntities(LivingEntity from, double distance) {
        return from.level.getNearbyEntities(
                LivingEntity.class,
                TargetingConditions.DEFAULT.range(distance),
                from,
                from.getBoundingBox().inflate(distance)
        );
    }
}
