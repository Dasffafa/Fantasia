/*
 * Copyright (c) 2021/8/3 下午3:10.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */

package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GoldKelaMixedBoneMeal extends Item {
    public GoldKelaMixedBoneMeal() {
        super(new Properties().group(Fantasia.GROUP));
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockPos blockpos1 = blockpos.offset(context.getFace());
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                BlockPos targetPos = blockpos.add(i, 0, j);
                if (applyBonemeal(context.getItem(), world, targetPos, context.getPlayer())) {
                    if (!world.isRemote) {
                        world.playEvent(2005, targetPos, 0);
                    }

//                    return ActionResultType.func_233537_a_(world.isRemote);
                } else {
                    BlockState blockstate = world.getBlockState(targetPos);
                    boolean flag = blockstate.isSolidSide(world, targetPos, context.getFace());
                    if (flag && growSeagrass(context.getItem(), world, blockpos1, context.getFace())) {
                        if (!world.isRemote) {
                            world.playEvent(2005, blockpos1, 0);
                        }
                    }
                }
            }
        }
        context.getItem().shrink(1);
        return ActionResultType.SUCCESS;
    }
//    onItem

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return true;
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.fantasia.gold_kela_mixed_bone_meal.desc")
                .mergeStyle(TextFormatting.YELLOW,TextFormatting.BOLD));
    }
    /*Copied from vanilla,removed stack.shrink(1);.*/
    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos, net.minecraft.entity.player.PlayerEntity player) {

        BlockState blockstate = worldIn.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)blockstate.getBlock();
            if (igrowable.canGrow(worldIn, pos, blockstate, worldIn.isRemote)) {
                if (worldIn instanceof ServerWorld) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, pos, blockstate)) {
                        igrowable.grow((ServerWorld)worldIn, worldIn.rand, pos, blockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }
    /*Copied from vanilla,removed stack.shrink(1);.*/
    public static boolean growSeagrass(@Nonnull ItemStack stack, World worldIn, BlockPos pos, @Nullable Direction side) {
        if (worldIn.getBlockState(pos).matchesBlock(Blocks.WATER) && worldIn.getFluidState(pos).getLevel() == 8) {
            if (!(worldIn instanceof ServerWorld)) {
                return true;
            } else {
                label80:
                for(int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.getDefaultState();

                    for(int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).hasOpaqueCollisionShape(worldIn, blockpos)) {
                            continue label80;
                        }
                    }

                    Optional<RegistryKey<Biome>> optional = worldIn.func_242406_i(blockpos);
                    if (Objects.equals(optional, Optional.of(Biomes.WARM_OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_WARM_OCEAN))) {
                        if (i == 0 && side != null && side.getAxis().isHorizontal()) {
                            blockstate = BlockTags.WALL_CORALS.getRandomElement(worldIn.rand).getDefaultState().with(DeadCoralWallFanBlock.FACING, side);
                        } else if (random.nextInt(4) == 0) {
                            blockstate = BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random).getDefaultState();
                        }
                    }

                    if (blockstate.getBlock().isIn(BlockTags.WALL_CORALS)) {
                        for(int k = 0; !blockstate.isValidPosition(worldIn, blockpos) && k < 4; ++k) {
                            blockstate = blockstate.with(DeadCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.random(random));
                        }
                    }

                    if (blockstate.isValidPosition(worldIn, blockpos)) {
                        BlockState blockstate1 = worldIn.getBlockState(blockpos);
                        if (blockstate1.matchesBlock(Blocks.WATER) && worldIn.getFluidState(blockpos).getLevel() == 8) {
                            worldIn.setBlockState(blockpos, blockstate, 3);
                        } else if (blockstate1.matchesBlock(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((IGrowable)Blocks.SEAGRASS).grow((ServerWorld)worldIn, random, blockpos, blockstate1);
                        }
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }
}

