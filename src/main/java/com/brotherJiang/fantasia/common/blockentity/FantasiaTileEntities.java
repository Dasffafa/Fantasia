package com.brotherJiang.fantasia.common.blockentity;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.block.FantasiaBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FantasiaTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Fantasia.MODID);

    public static final RegistryObject<TileEntityType<LittleGiftTileEntity>> LITTLE_GIFT = TILE_ENTITIES.register("little_gift", () -> TileEntityType.Builder.create(LittleGiftTileEntity::new, FantasiaBlocks.LITTLE_GIFT.get()).build(null));
}
