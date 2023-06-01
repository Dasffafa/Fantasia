package com.dasffafa.fantasia.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpellHelper {
    private SpellHelper() { }
    public static void attackEntityWithoutResistance(LivingEntity entity, DamageSource source, float amount){
        Vec3 prevMotion = entity.getDeltaMovement();
        entity.invulnerableTime = 0;
        entity.hurt(source,amount);
        entity.invulnerableTime= 0;
        entity.setDeltaMovement(prevMotion);
    }

    public static void attackEntityWithoutKnockingBack(LivingEntity entity, DamageSource source,float amount){
        Vec3 prevMotion = entity.getDeltaMovement();
        entity.hurt(source,amount);
        entity.setDeltaMovement(prevMotion);
    }
    public static void harvestBlockWithItemDrops(Level world, LivingEntity harvester, BlockPos blockPos, ItemStack harvestBy) {
        BlockState blockstate = world.getBlockState(blockPos);
        if (blockstate.isAir()) return;

        FluidState ifluidstate = world.getFluidState(blockPos);
        world.levelEvent(2001, blockPos, Block.getId(blockstate));
        BlockEntity tileentity = blockstate.hasBlockEntity() ? world.getBlockEntity(blockPos) : null;
        Block.dropResources(blockstate, world, blockPos, tileentity, harvester, harvestBy);  // FORTUNE and SILK TOUCH enchantments affect drops

        int flags = SetBlockStateFlag.get(SetBlockStateFlag.BLOCK_UPDATE, SetBlockStateFlag.SEND_TO_CLIENTS);
        world.setBlock(blockPos, ifluidstate.createLegacyBlock(), flags);
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
//        from.level.getNearbyEntities()
    }

//    public int getBlockDestroyPoints(BlockState state) {
//        if (state.getBlock().canHarvestBlock())
//    }
}
