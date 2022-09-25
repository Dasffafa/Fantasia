/*
 * Copyright (c) 2021/7/30 下午3:58.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */

package com.brotherJiang.fantasia.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TestBlock extends Block {
    public TestBlock() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(3));
    }
}


