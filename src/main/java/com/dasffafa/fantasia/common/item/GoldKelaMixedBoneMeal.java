package com.dasffafa.fantasia.common.item;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static net.minecraft.world.item.BoneMealItem.growWaterPlant;
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GoldKelaMixedBoneMeal extends Item {
    public GoldKelaMixedBoneMeal() {
        super(new Properties().tab(Fantasia.TAB));
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        boolean successfullyUsed = false;
        Level world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.offset(context.getClickedFace().getNormal());
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                BlockPos targetPos = blockpos.offset(i, 0, j);
                if (applyBonemeal(world, targetPos)) {
                    successfullyUsed = true;
                    if (!world.isClientSide()) {
                        world.levelEvent(2005, targetPos, 0);
                    }
                } else {
                    BlockState blockstate = world.getBlockState(targetPos);
                    boolean flag = blockstate.isSolidRender(world, targetPos);
                    if (flag && growWaterPlant(context.getItemInHand(), world, blockpos1, context.getClickedFace())) {
                        if (!world.isClientSide) {
                            world.levelEvent(2005, blockpos1, 0);
                        }
                    }
                }
            }
        }
        if (!successfullyUsed) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.SUCCESS;
    }
    public static boolean applyBonemeal(Level pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (blockstate.getBlock() instanceof BonemealableBlock bonemealableblock) {
            if (bonemealableblock.isValidBonemealTarget(pLevel, pPos, blockstate, pLevel.isClientSide)) {
                if (pLevel instanceof ServerLevel) {
                    if (bonemealableblock.isBonemealSuccess(pLevel, pLevel.random, pPos, blockstate)) {
                        bonemealableblock.performBonemeal((ServerLevel) pLevel, pLevel.random, pPos, blockstate);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("item.fantasia.gold_kela_mixed_bone_meal.desc")
                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
    }

    @Nonnull
    @Override
    public Component getName(ItemStack pStack) {
        return new TranslatableComponent(this.getDescriptionId(pStack))
                .withStyle(ChatFormatting.YELLOW);
    }


    //    onItem
}
