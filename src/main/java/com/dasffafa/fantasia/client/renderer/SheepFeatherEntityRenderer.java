package com.dasffafa.fantasia.client.renderer;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.projectile.SheepFeatherEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SheepFeatherEntityRenderer extends ArrowRenderer<SheepFeatherEntity> {
    public SheepFeatherEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(SheepFeatherEntity pEntity) {
        return new ResourceLocation(Fantasia.MOD_ID,"textures/entity/projectiles/sheep_feather.png");
    }

}
