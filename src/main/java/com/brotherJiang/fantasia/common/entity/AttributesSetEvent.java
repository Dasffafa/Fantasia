package com.brotherJiang.fantasia.common.entity;

import com.brotherJiang.fantasia.common.entity.living.FeatheredSheepEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributesSetEvent {
    @SubscribeEvent
    public static void setupAttributes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(FantasiaEntities.FEATHER_SHEEP.get(), FeatheredSheepEntity.setCustomAttributes().create());
        });
    }
}
