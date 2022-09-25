/*
 * Copyright (c) 2021/7/30 下午10:02.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/bj_magic2.
 */
package com.brotherJiang.fantasia.common.utils;

import com.brotherJiang.fantasia.Fantasia;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class ModDamageSources {
    public static final DamageSource hiccup = new DamageSource(Fantasia.MODID+".hiccup").setDamageBypassesArmor();
    public static final DamageSource sudden_death = new DamageSource(Fantasia.MODID+".sudden_death").setMagicDamage();

    public static final DamageSource sheep_feather = new DamageSource(Fantasia.MODID + ".sheep_feather");
    public static DamageSource causeIndirectMagicDamage(String magicType,Entity source,Entity victim){
        return new IndirectEntityDamageSource(magicType, source, victim).setMagicDamage();
    }

}
