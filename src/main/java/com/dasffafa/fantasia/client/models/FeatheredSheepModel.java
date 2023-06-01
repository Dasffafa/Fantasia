package com.dasffafa.fantasia.client.models;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.living.feathered_sheep.FeatheredSheepEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeatheredSheepModel extends AnimatedGeoModel<FeatheredSheepEntity> {

    @Override
    public ResourceLocation getModelLocation(FeatheredSheepEntity object) {
        return new ResourceLocation(Fantasia.MOD_ID, "geo/feathered_sheep.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FeatheredSheepEntity object) {
        return new ResourceLocation(Fantasia.MOD_ID, "textures/entity/feathered_sheep.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FeatheredSheepEntity animatable) {
        return new ResourceLocation(Fantasia.MOD_ID, "animations/feathered_sheep.animation.json");
    }
}
