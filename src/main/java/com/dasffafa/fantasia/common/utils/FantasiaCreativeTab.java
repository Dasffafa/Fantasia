package com.dasffafa.fantasia.common.utils;

import com.dasffafa.fantasia.common.item.FantasiaItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FantasiaCreativeTab extends CreativeModeTab {
    public FantasiaCreativeTab() {
        super("fantasia");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(FantasiaItems.GOLD_KELA.get(), 1);
    }
}
