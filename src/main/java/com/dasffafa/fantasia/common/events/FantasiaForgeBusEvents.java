package com.dasffafa.fantasia.common.events;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.client.particle.FantasiaParticles;
import com.dasffafa.fantasia.client.particle.SplashingShitParticle;
import com.dasffafa.fantasia.client.particle.WindParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fantasia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FantasiaForgeBusEvents {

    @SubscribeEvent
    public static void onRegisterParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(FantasiaParticles.WIND_PARTICLE.get(),WindParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(FantasiaParticles.SPLASHING_SHIT_PARTICLE.get(), SplashingShitParticle.Provider::new);
    }
}
