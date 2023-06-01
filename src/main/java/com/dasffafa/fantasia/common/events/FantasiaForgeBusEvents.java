package com.dasffafa.fantasia.common.events;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.client.particle.FantasiaParticles;
import com.dasffafa.fantasia.client.particle.SplashingShitParticle;
import com.dasffafa.fantasia.client.particle.WindParticle;
import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.network.FantasiaNetworking;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Fantasia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FantasiaForgeBusEvents {

    @SubscribeEvent
    public static void onRegisterParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(FantasiaParticles.WIND_PARTICLE.get(),WindParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(FantasiaParticles.SPLASHING_SHIT_PARTICLE.get(), SplashingShitParticle.Provider::new);
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        FantasiaNetworking.registerMessage();
    }

    @SubscribeEvent
    public static void onEntityAttributeSetup(EntityAttributeCreationEvent evt) {
        evt.put(FantasiaEntities.FEATHERED_SHEEP.get(), FeatheredSheepEntity.setAttributes());
    }

}
