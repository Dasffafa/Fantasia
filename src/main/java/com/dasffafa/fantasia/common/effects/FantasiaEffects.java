package com.dasffafa.fantasia.common.effects;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Fantasia.MOD_ID);

    public static final RegistryObject<MobEffect> HICCUP = EFFECTS.register("hiccup", () -> new HiccupEffect(false));
    public static final RegistryObject<MobEffect> GOLD_KELA_HICCUP = EFFECTS.register("gold_kela_hiccup", () -> new HiccupEffect(true));
    public static final RegistryObject<MobEffect> FEAR = EFFECTS.register("fear", FearEffect::new);

}
