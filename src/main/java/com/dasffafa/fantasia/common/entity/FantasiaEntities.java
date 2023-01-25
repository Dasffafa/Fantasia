package com.dasffafa.fantasia.common.entity;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.living.FeatheredSheepEntity;
import com.dasffafa.fantasia.common.entity.projectile.FlyingShitEntity;
import com.dasffafa.fantasia.common.entity.projectile.PrimedDoubleBangFirework;
import com.dasffafa.fantasia.common.entity.projectile.SheepFeatherEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Fantasia.MOD_ID);

    public static final RegistryObject<EntityType<FlyingShitEntity>> FLYING_SHIT = ENTITIES.register("flying_shit",
            () -> EntityType.Builder.<FlyingShitEntity>of(FlyingShitEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).build("flying_shit"));

    public static final RegistryObject<EntityType<SheepFeatherEntity>> SHEEP_FEATHER = ENTITIES.register("sheep_feather",
            () -> EntityType.Builder.<SheepFeatherEntity>of(SheepFeatherEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).build("sheep_feather"));

    public static final RegistryObject<EntityType<PrimedDoubleBangFirework>> PRIMED_DOUBLE_BANG_FIREWORK = ENTITIES.register("primed_double_bang_firework",
            () -> EntityType.Builder.<PrimedDoubleBangFirework>of(PrimedDoubleBangFirework::new, MobCategory.MISC)
                    .sized(0.6F, 1.2F).build("primed_double_bang_firework"));
    //    );
    public static final RegistryObject<EntityType<FeatheredSheepEntity>> FEATHERED_SHEEP = ENTITIES.register("feathered_sheep",
            () -> EntityType.Builder.of(FeatheredSheepEntity::new, MobCategory.MONSTER)
                    .sized(1.8F, 1.2F).build("feathered_sheep"));
}