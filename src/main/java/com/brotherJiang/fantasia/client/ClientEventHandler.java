package com.brotherJiang.fantasia.client;

import com.brotherJiang.fantasia.client.renderer.FeatheredSheepRenderer;
import com.brotherJiang.fantasia.client.renderer.SheepFeatherRenderer;
import com.brotherJiang.fantasia.common.entity.FantasiaEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetUpEvent(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(FantasiaEntities.FEATHER_SHEEP.get(), FeatheredSheepRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FantasiaEntities.SHEEP_FEATHER.get(), new SheepFeatherRenderer());
    }
}
