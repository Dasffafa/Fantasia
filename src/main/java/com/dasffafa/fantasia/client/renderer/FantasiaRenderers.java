package com.dasffafa.fantasia.client.renderer;

import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault

public class FantasiaRenderers {
    public static void registerRenderer() {
        EntityRenderers.register(FantasiaEntities.FLYING_SHIT.get(), ThrownItemRenderer::new);
        EntityRenderers.register(FantasiaEntities.SHEEP_FEATHER.get(), SheepFeatherEntityRenderer::new);
        EntityRenderers.register(FantasiaEntities.PRIMED_DOUBLE_BANG_FIREWORK.get(), PrimedDoubleBangFireworkRenderer::new);
        EntityRenderers.register(FantasiaEntities.FEATHERED_SHEEP.get(), FeatheredSheepRenderer::new);
    }


}
