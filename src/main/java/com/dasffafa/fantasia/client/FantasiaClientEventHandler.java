package com.dasffafa.fantasia.client;

import com.dasffafa.fantasia.client.renderer.FantasiaRenderers;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.utils.FantasiaNBTTags;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FantasiaClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        FantasiaRenderers.registerRenderer();

//        ItemBlockRenderTypes.setRenderLayer(FantasiaFluids.SHIT_FLUID.get(), RenderType.solid());
//        ItemBlockRenderTypes.setRenderLayer(FantasiaFluids.SHIT_FLOWING.get(), RenderType.solid());
        event.enqueueWork(FantasiaClientEventHandler::registerPropertyOverride);
    }

    public static void registerPropertyOverride() {
        /*
        This function will register properties that can be read and used
        while rendering item model, allowing changing item model
        according to NBT of the item , just as vanilla bow does.
        */

        ItemProperties.register(FantasiaItems.POOP_SWORD.get(), new ResourceLocation("glowing"), (itemStack, clientLevel, entity, i) -> {
            if (entity == null)return 0.0f;
            else return itemStack.getOrCreateTag().getBoolean(FantasiaNBTTags.POOP_SWORD_GLOWING) ? 1.0f : 0;
        });
        ItemProperties.register(FantasiaItems.SHEEP_FEATHER_SWORD.get(), new ResourceLocation("stored_attacks"),(itemStack,clientLevel, entity, i)->{
            if (entity == null) return 0.0f;
            else return itemStack.getOrCreateTag().getFloat(FantasiaNBTTags.STORED_ATTACK_TIMES);
        });
    }
}
