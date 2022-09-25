/*
 * Copyright (c) 2021/7/30 下午9:36.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */
package com.brotherJiang.fantasia.common.effect;

import com.brotherJiang.fantasia.Fantasia;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FantasiaEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Fantasia.MODID);
    public static final Effect HICCUP = register("hiccup",new HiccupEffect());
    public static final Effect TIMED_BOMB = register("timed_bomb",new TimedBombEffect());
    public static final Effect SUDDEN_DEATH = register("sudden_death",new SuddenDeathEffect());
    public static final Effect FIRECRACKER_STRING = register("firecracker_string",new FirecrackerStringEffect());

    private static Effect register(String name, Effect effect) {
        EFFECTS.register(name, () -> effect);
        return effect;
    }
}
