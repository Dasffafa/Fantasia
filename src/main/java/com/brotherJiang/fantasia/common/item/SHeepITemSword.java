package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResultType;

public class SHeepITemSword extends SwordItem {
    public SHeepITemSword() {
        super(FantasiaItemTier.SHEEP_FEATHER, 8, 1.6f, new Properties().group(Fantasia.GROUP));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }
}