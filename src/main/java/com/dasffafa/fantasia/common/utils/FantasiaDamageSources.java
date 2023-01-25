package com.dasffafa.fantasia.common.utils;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.world.damagesource.DamageSource;

public class FantasiaDamageSources {
    public static final DamageSource HICCUP = new DamageSource(Fantasia.MOD_ID+".hiccup").setNoAggro();
    public static final DamageSource SUDDEN_DEATH = new DamageSource(Fantasia.MOD_ID+".sudden_death").setMagic();

    public static final DamageSource SHEEP_FEATHER = new DamageSource(Fantasia.MOD_ID + ".sheep_feather");

    public static final DamageSource STINK = new DamageSource(Fantasia.MOD_ID + ".stink");

    public static final DamageSource GOLD_KELA = new DamageSource(Fantasia.MOD_ID + ".gold_kela");
}