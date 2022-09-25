/*
 * Copyright (c) 2021/8/3 下午2:51.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */

package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;

import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class $class_name extends Item {
    private static final Food foodProperty = (new Food.Builder())
            .saturation(0)
            .hunger(0);

    public $class_name() {
        super(new Properties().group(Fantasia.GROUP).food(foodProperty));
    }
}
