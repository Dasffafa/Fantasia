package com.dasffafa.fantasia.client.renderer;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.client.models.FeatheredSheepModel;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;

public class FeatheredSheepRenderer extends GeoEntityRenderer<FeatheredSheepEntity> {
    public FeatheredSheepRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FeatheredSheepModel());
    }

    @Override
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull FeatheredSheepEntity entity) {
        return new ResourceLocation(Fantasia.MOD_ID, "textures/entity/feathered_sheep.png");
    }
}
