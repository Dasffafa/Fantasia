package com.dasffafa.fantasia.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class AetheriumOre extends Block {
    public static final IntegerProperty PURITY = IntegerProperty.create("purity", 1, 5);
    public AetheriumOre() {
        super(Properties.of(Material.STONE).explosionResistance(20));
    }

//    private BlockState getStateofPurity(int purity) {
////        return
//    }
    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return super.getLightEmission(state, level, pos);
    }
}
