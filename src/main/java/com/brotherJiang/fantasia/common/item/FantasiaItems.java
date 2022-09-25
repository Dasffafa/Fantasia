/*
 * Copyright (c) 2021/7/30 下午3:58.
 * Author:BrotherJiang
 * This class is part of BrotherJiang's Element Magic.
 * This Mod is open-sourced under GPL 3.0 License ,see:https://gitee.com/Capybard/bj_magic2.
 */
package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.block.FantasiaBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
public class FantasiaItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Fantasia.MODID);

    //Following are Items
    public static final RegistryObject<Item> POOP_SWORD = ITEMS.register("poop_sword", PoopSword::new);
    public static final RegistryObject<Item> EXCREMENT_SWORD = ITEMS.register("excrement_sword", ExcrementSword::new);
    public static final RegistryObject<Item> SHEEP_FEATHER_SWORD = ITEMS.register("sheep_feather_sword", SHeepITemSword::new);
    public static final RegistryObject<Item> SHEEP_FEATHER = ITEMS.register("sheep_feather", SheepFeather::new);
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", TestItem::new);
    public static final RegistryObject<Item> DOUBLE_BANG_POTION = ITEMS.register("double_bang_potion", DoubleBangPotion::new);
    public static final RegistryObject<Item> SKY_MONKEY_POTION = ITEMS.register("sky_monkey_potion", SkyMonkeyPotion::new);
    public static final RegistryObject<Item> FIRECRACKERS_STRING_POTION = ITEMS.register("firecrackers_string_potion", FirecrackersStringPotion::new);
    public static final RegistryObject<Item> GOLD_KELA = ITEMS.register("gold_kela", GoldKela::new);
    public static final RegistryObject<Item> GOLD_KELA_MIXED_BONE_MEAL = ITEMS.register("gold_kela_mixed_bone_meal", GoldKelaMixedBoneMeal::new);
    public static final RegistryObject<Item> RED_OIL_NOODLES = ITEMS.register("red_oil_noodles", RedOilNoodles::new);


    //Followings are ItemBlocks
    public static final RegistryObject<Item> FIRST_BLOCK = ITEMS.register("first_block", () -> new BlockItem(FantasiaBlocks.FIRST_BLOCK.get(), new Item.Properties().group(Fantasia.GROUP)));
    public static final RegistryObject<Item> TEST_BLOCK = ITEMS.register("test_block", () -> new BlockItem(FantasiaBlocks.TEST_BLOCK.get(), new Item.Properties().group(Fantasia.GROUP)));
    public static final RegistryObject<Item> AETHERIUM_ORE = ITEMS.register("aetherium_ore", () -> new BlockItem(FantasiaBlocks.AETHERIUM_ORE.get() , new Item.Properties().group(Fantasia.GROUP)));
    public static final RegistryObject<Item> LITTLE_GIFT = ITEMS.register("little_gift", () -> new BlockItem(FantasiaBlocks.LITTLE_GIFT.get(), new Item.Properties().group(Fantasia.GROUP)));
};

