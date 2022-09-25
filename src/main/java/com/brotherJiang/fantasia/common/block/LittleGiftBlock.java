package com.brotherJiang.fantasia.common.block;

import com.brotherJiang.fantasia.common.blockentity.LittleGiftTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LittleGiftBlock extends Block {
    public LittleGiftBlock() {
        super(Properties.create(Material.WOOL).hardnessAndResistance(2));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LittleGiftTileEntity();
    }

    @Nonnull
    @Override
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public ActionResultType onBlockActivated(
            @Nonnull BlockState state,
            @Nonnull World worldIn,
            @Nonnull BlockPos pos,
            @Nonnull PlayerEntity player,
            @Nonnull Hand handIn,
            @Nonnull BlockRayTraceResult hit) {
        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND){
            LittleGiftTileEntity tileEntity = (LittleGiftTileEntity) worldIn.getTileEntity(pos);
            int counter = tileEntity.increase();
            TranslationTextComponent message = new TranslationTextComponent("chat.fantasia.little_gift.counter", counter);
            player.sendStatusMessage(message,false);
        }
        return ActionResultType.SUCCESS;
    }
}
