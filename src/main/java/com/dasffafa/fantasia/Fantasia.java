package com.dasffafa.fantasia;

import com.dasffafa.fantasia.client.particle.FantasiaParticles;
import com.dasffafa.fantasia.common.block.FantasiaBlocks;
import com.dasffafa.fantasia.common.effects.FantasiaEffects;
import com.dasffafa.fantasia.common.effects.FearEffect;
import com.dasffafa.fantasia.common.enchantments.FantasiaEnchantments;
import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.fluid.FantasiaFluids;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.painting.FantasiaPaintings;
import com.dasffafa.fantasia.common.utils.FantasiaCreativeTab;
import com.dasffafa.fantasia.common.utils.FantasiaSounds;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(Fantasia.MOD_ID)
public class Fantasia {
    public static final String MOD_ID = "fantasia";
    public static final CreativeModeTab TAB = new FantasiaCreativeTab();
    public static final Logger LOGGER = LogManager.getLogger();

    public Fantasia(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FantasiaSounds.SOUNDS.register(modEventBus);
        FantasiaItems.ITEMS.register(modEventBus);
        FantasiaBlocks.BLOCKS.register(modEventBus);
        FantasiaEntities.ENTITIES.register(modEventBus);
//        FantasiaTileEntities.TILE_ENTITIES.register(modEventBus);
        FantasiaEffects.EFFECTS.register(modEventBus);

        FantasiaPaintings.PAINTINGS.register(modEventBus);
        FantasiaFluids.FLUIDS.register(modEventBus);
        FantasiaEnchantments.ENCHANTMENTS.register(modEventBus);
        FantasiaParticles.PARTICLES.register(modEventBus);


        GeckoLib.initialize();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(FearEffect::onPlayerAttackMob);
        forgeBus.addListener(FearEffect::onPotionExpiry);
//        forgeBus.addListener(SuddenDeathEffect::onPotionExpiry);
//        forgeBus.addListener(TimedBombEffect::onPotionExpiry);
    }
}
