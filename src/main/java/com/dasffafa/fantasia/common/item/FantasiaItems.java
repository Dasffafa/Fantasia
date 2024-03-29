package com.dasffafa.fantasia.common.item;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.fluid.FantasiaFluids;
import com.dasffafa.fantasia.common.item.tools.ExcrementSword;
import com.dasffafa.fantasia.common.item.tools.PoopSword;
import com.dasffafa.fantasia.common.item.tools.SHeepITemSword;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Fantasia.MOD_ID);

    public static final RegistryObject<Item> GOLD_KELA = ITEMS.register("gold_kela", GoldKela::new);
    public static final RegistryObject<Item> GOLD_KELA_MIXED_BONE_MEAL = ITEMS.register("gold_kela_mixed_bone_meal", GoldKelaMixedBoneMeal::new);
    public static final RegistryObject<Item> SHEEP_FEATHER_SWORD = ITEMS.register("sheep_item_sword", SHeepITemSword::new);
    public static final RegistryObject<Item> EXCREMENT_SWORD = ITEMS.register("excrement_sword", ExcrementSword::new);

    public static final RegistryObject<Item> POOP_SWORD = ITEMS.register("poop_sword", PoopSword::new);
    public static final RegistryObject<Item> FLYING_SHIT = ITEMS.register("flying_shit", FlyingShitItem::new);
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", TestItem::new);
    public static final RegistryObject<Item> SHEEP_FEATHER = ITEMS.register("sheep_feather", SheepFeatherItem::new);

    public static final RegistryObject<Item> FEATHERED_SHEEP_SPAWN_EGG = ITEMS.register("feathered_sheep_spawn_egg",
            () -> new ForgeSpawnEggItem(FantasiaEntities.FEATHERED_SHEEP,0x4c2724, 0xbe9c94,
                    new Item.Properties().tab(Fantasia.TAB)));

    public static final RegistryObject<Item> SHIT_BUCKET = ITEMS.register("shit_bucket", () -> new BucketItem(
            FantasiaFluids.SHIT_FLUID,
            new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)
    ));

    public static final RegistryObject<Item> SHASHLIK_FOR_DEMON = ITEMS.register("shashlik_for_demon", ShashlikForDemon::new);

}
