package com.brotherJiang.fantasia.common.entity;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.entity.living.FeatheredSheepEntity;
import com.brotherJiang.fantasia.common.entity.projectile.SheepFeatherEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Fantasia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FantasiaEntities{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Fantasia.MODID);

    //Followings are LivingEntities
    public static final RegistryObject<EntityType<FeatheredSheepEntity>> FEATHER_SHEEP = ENTITIES.register("feathered_sheep", () -> EntityType.Builder.create(FeatheredSheepEntity::new, EntityClassification.MONSTER).size(1.4F, 1.5F).build("feathered_sheep"));

    //Followings are projectiles
    public static final RegistryObject<EntityType<SheepFeatherEntity>> SHEEP_FEATHER = ENTITIES.register("sheep_feather", () -> EntityType.Builder.<SheepFeatherEntity>create((entityType, world) -> new SheepFeatherEntity(world), EntityClassification.MISC).size(0.25F, 0.25F).build("sheep_feather"));
    @SubscribeEvent
    public static void onSetupAttributesEvent(@NotNull EntityAttributeCreationEvent event){
        event.put(FantasiaEntities.FEATHER_SHEEP.get(),FeatheredSheepEntity.setCustomAttributes().create());
    }
}
