package com.brotherJiang.fantasia.common.block;
/*
 * Copyright (c) 2021/7/30 下午3:58.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/fantasia2.
 */

import com.brotherJiang.fantasia.Fantasia;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FantasiaBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Fantasia.MODID);

    //Followings are blocks
    public static final RegistryObject<Block> FIRST_BLOCK = BLOCKS.register("first_block", FirstBlock::new);
    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", TestBlock::new);
    public static final RegistryObject<Block> AETHERIUM_ORE = BLOCKS.register("aetherium_ore", AetheriumOre::new);
    public static final RegistryObject<Block> LITTLE_GIFT = BLOCKS.register("little_gift", LittleGiftBlock::new);
}