package com.dasffafa.fantasia.common.block;

import com.dasffafa.fantasia.common.item.GoldKelaMixedBoneMeal;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ShitBlock extends Block {
    public ShitBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT)
                .explosionResistance(20f)
                .friction(0.4F)
                .destroyTime(3)
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        int nourishedCrops = 0;
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = 0; y <= 2; y++) {
                    var targetPos = new BlockPos(x, y, z);
                    if (pLevel.getBlockState(targetPos).getBlock() instanceof CropBlock) {
                        GoldKelaMixedBoneMeal.applyBonemeal(pLevel, pPos);
                        nourishedCrops++;
                        if (nourishedCrops > 5) {
                            return;
                        }
                    }
                }
            }
        }
    }


    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.FLINT_AND_STEEL) || itemstack.is(Items.FIRE_CHARGE)) {
            onCaughtFire(pState, pLevel, pPos, pHit.getDirection(), pPlayer);
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 11);
            pPlayer.invulnerableTime = 0;
            pLevel.explode(pPlayer, pPos.getX(), pPos.getY(), pPos.getZ(), 5.0F, true, Explosion.BlockInteraction.BREAK);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
