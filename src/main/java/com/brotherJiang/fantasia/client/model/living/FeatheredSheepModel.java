package com.brotherJiang.fantasia.client.model.living;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.entity.living.FeatheredSheepEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FeatheredSheepModel extends AnimatedGeoModel<FeatheredSheepEntity> {

    @Override
    public ResourceLocation getModelLocation(FeatheredSheepEntity object) {
        return new ResourceLocation(Fantasia.MODID, "geo/feathered_sheep.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FeatheredSheepEntity object) {
        return new ResourceLocation(Fantasia.MODID, "textures/entity/feathered_sheep.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FeatheredSheepEntity animatable) {
        return new ResourceLocation(Fantasia.MODID, "animations/feathered_sheep.animation.json");
    }
}
