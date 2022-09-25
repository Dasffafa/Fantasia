package com.brotherJiang.fantasia.common.itemGroup;

import com.brotherJiang.fantasia.common.item.FantasiaItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class FantasiaItemGroup extends ItemGroup {
    public FantasiaItemGroup() {
        super("fantasia");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(FantasiaItems.GOLD_KELA_MIXED_BONE_MEAL.get());
    }
}
