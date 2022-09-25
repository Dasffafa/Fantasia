package com.brotherJiang.fantasia.client.renderer;

import com.brotherJiang.fantasia.common.entity.projectile.SheepFeatherEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SheepFeatherRenderer implements IRenderFactory<SheepFeatherEntity> {
    @Override
    public EntityRenderer<? super SheepFeatherEntity> createRenderFor(EntityRendererManager manager) {
        ItemRenderer sheepFeatherRenderer = Minecraft.getInstance().getItemRenderer();
        return new SpriteRenderer<>(manager, sheepFeatherRenderer);
    }
}
