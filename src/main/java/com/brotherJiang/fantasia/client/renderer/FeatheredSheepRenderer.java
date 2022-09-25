package com.brotherJiang.fantasia.client.renderer;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.client.model.living.FeatheredSheepModel;
import com.brotherJiang.fantasia.common.entity.living.FeatheredSheepEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;

public class FeatheredSheepRenderer extends GeoEntityRenderer<FeatheredSheepEntity> {
    public FeatheredSheepRenderer(EntityRendererManager renderManager) {
        super(renderManager, new FeatheredSheepModel());
    }

    @Override
    @Nonnull
    public ResourceLocation getEntityTexture(@Nonnull FeatheredSheepEntity entity) {
        return new ResourceLocation(Fantasia.MODID, "textures/entity/feathered_sheep.png");
    }
}
