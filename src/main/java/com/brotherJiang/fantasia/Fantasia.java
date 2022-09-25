package com.brotherJiang.fantasia;

import com.brotherJiang.fantasia.common.block.FantasiaBlocks;
import com.brotherJiang.fantasia.common.blockentity.FantasiaTileEntities;
import com.brotherJiang.fantasia.common.effect.FantasiaEffects;
import com.brotherJiang.fantasia.common.effect.SuddenDeathEffect;
import com.brotherJiang.fantasia.common.effect.TimedBombEffect;
import com.brotherJiang.fantasia.common.entity.FantasiaEntities;
import com.brotherJiang.fantasia.common.item.FantasiaItems;
import com.brotherJiang.fantasia.common.itemGroup.FantasiaItemGroup;
import com.brotherJiang.fantasia.common.utils.FantasiaSoundTypes;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Fantasia.MODID)
public class Fantasia {
    public static final String MODID = "fantasia";
    public static final ItemGroup GROUP = new FantasiaItemGroup();

    public Fantasia() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FantasiaItems.ITEMS.register(modEventBus);
        FantasiaBlocks.BLOCKS.register(modEventBus);
        FantasiaEntities.ENTITIES.register(modEventBus);
        FantasiaTileEntities.TILE_ENTITIES.register(modEventBus);
        FantasiaEffects.EFFECTS.register(modEventBus);
        FantasiaSoundTypes.SOUNDS.register(modEventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(SuddenDeathEffect::onPotionExpiry);
        forgeBus.addListener(TimedBombEffect::onPotionExpiry);
    }
}
