package com.dasffafa.fantasia.common.enchantments;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Fantasia.MOD_ID);

    public static final RegistryObject<Enchantment> MULTIPLE_FEATHER = ENCHANTMENTS.register("multiple_feather", MultipleFeatherEnchantment::new);
}
