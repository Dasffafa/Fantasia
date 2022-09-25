package com.brotherJiang.fantasia.common.utils;


import com.brotherJiang.fantasia.Fantasia;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FantasiaSoundTypes {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Fantasia.MODID);
    public static final RegistryObject<SoundEvent> HICCUP = SOUNDS.register("hiccup",() -> new SoundEvent(new ResourceLocation(Fantasia.MODID, "hiccup")));
    public static final RegistryObject<SoundEvent> SKY_MONKEY = SOUNDS.register("sky_monkey",() -> new SoundEvent(new ResourceLocation(Fantasia.MODID, "sky_monkey")));
    public static final RegistryObject<SoundEvent> SHIT_HIT = SOUNDS.register("shit_hit",() -> new SoundEvent(new ResourceLocation(Fantasia.MODID, "shit_hit")));
    public static final RegistryObject<SoundEvent> SHEEP_FLAP = SOUNDS.register("sheep_wing", () -> new SoundEvent(new ResourceLocation(Fantasia.MODID, "sheep_wing")));
    public static final RegistryObject<SoundEvent> GOLD_KELA_CONFLICT = SOUNDS.register("gold_kela_conflict", () -> new SoundEvent(new ResourceLocation(Fantasia.MODID, "gold_kela_conflict")));
    public static final RegistryObject<SoundEvent> GOLD_KELA_USE = SOUNDS.register("gold_kela_use", () -> new SoundEvent(new ResourceLocation(Fantasia.MODID, "gold_kela_use")));
}

