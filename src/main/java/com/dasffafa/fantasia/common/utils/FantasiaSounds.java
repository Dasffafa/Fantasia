package com.dasffafa.fantasia.common.utils;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Fantasia.MOD_ID);
    public static final RegistryObject<SoundEvent> HICCUP = SOUNDS.register("hiccup",() -> new SoundEvent(new ResourceLocation(Fantasia.MOD_ID, "hiccup")));
    public static final RegistryObject<SoundEvent> SKY_MONKEY = SOUNDS.register("sky_monkey",() -> new SoundEvent(new ResourceLocation(Fantasia.MOD_ID, "sky_monkey")));
    public static final RegistryObject<SoundEvent> SHIT_HIT = SOUNDS.register("shit_hit",() -> new SoundEvent(new ResourceLocation(Fantasia.MOD_ID, "shit_hit")));
    public static final RegistryObject<SoundEvent> SHEEP_FLAP = SOUNDS.register("sheep_wing", () -> new SoundEvent(new ResourceLocation(Fantasia.MOD_ID, "sheep_wing")));
    public static final RegistryObject<SoundEvent> GOLD_KELA_CONFLICT = SOUNDS.register("gold_kela_conflict", () -> new SoundEvent(new ResourceLocation(Fantasia.MOD_ID, "gold_kela_conflict")));
    public static final RegistryObject<SoundEvent> GOLD_KELA_USE = SOUNDS.register("gold_kela_use", () -> new SoundEvent(new ResourceLocation(Fantasia.MOD_ID, "gold_kela_use")));
}
