package com.dasffafa.fantasia.common.item;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ShashlikForDemon extends Item {
    private static final FoodProperties ShashlikProperty = new FoodProperties.Builder()
            .saturationMod(999)
            .nutrition(999)
            .alwaysEat()
            .build();
    public ShashlikForDemon() {
        super(new Properties().tab(Fantasia.TAB).stacksTo(16).food(ShashlikProperty));
    }
}
