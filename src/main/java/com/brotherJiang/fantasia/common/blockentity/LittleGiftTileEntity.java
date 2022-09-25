package com.brotherJiang.fantasia.common.blockentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class LittleGiftTileEntity extends TileEntity {
    private int count = 0;
    public LittleGiftTileEntity(){
        super(FantasiaTileEntities.LITTLE_GIFT.get());
    }

    public int increase(){
        count++;
        markDirty();
        return count;
    }
}
