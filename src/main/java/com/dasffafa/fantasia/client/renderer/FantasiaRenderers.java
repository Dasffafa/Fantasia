package com.dasffafa.fantasia.client.renderer;

import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.item.FantasiaNBTTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FantasiaRenderers {
    @SubscribeEvent
    public static void registerRenderer(FMLClientSetupEvent event) {
        EntityRenderers.register(FantasiaEntities.FLYING_SHIT.get(), ThrownItemRenderer::new);
        EntityRenderers.register(FantasiaEntities.SHEEP_FEATHER.get(), ThrownItemRenderer::new);
        EntityRenderers.register(FantasiaEntities.PRIMED_DOUBLE_BANG_FIREWORK.get(), PrimedDoubleBangFireworkRenderer::new);
//        EntityRenderers.register(FantasiaEntities.FEATHERED_SHEEP.get(), FeatheredSheepRenderer::new);
        event.enqueueWork(FantasiaRenderers::registerPropertyOverride);
    }

    public static void registerPropertyOverride() {
        // use lambda function to link the NBT fullness value to a suitable property override value
        ItemProperties.register(FantasiaItems.POOP_SWORD.get(), new ResourceLocation("glowing"), (itemStack, clientLevel, entity, i) -> {
            if (entity == null) {
                return 0.0f;
            } else {
                return itemStack.getOrCreateTag().getBoolean(FantasiaNBTTags.POOP_SWORD_GLOWING) ? 1.0f : 0;
            }
        });
        ItemProperties.register(FantasiaItems.SHEEP_FEATHER_SWORD.get(), new ResourceLocation("stored_attacks"),(itemStack,clientLevel, entity, i)->{
            if (entity == null) return 0.0f;
            else return itemStack.getOrCreateTag().getFloat(FantasiaNBTTags.STORED_ATTACK_TIMES);
        });
    }
}
