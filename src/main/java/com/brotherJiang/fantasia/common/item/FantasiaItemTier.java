package com.brotherJiang.fantasia.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public enum FantasiaItemTier implements IItemTier {
    EXCREMENT(0, 30, 0.1F, 5.0F,2),

    POOP(0,40,0.1F,3.5F,3),

    SHEEP_FEATHER(3, 400, 8F, 8.5F, 20) ;

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;

    FantasiaItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairMaterial() {
        return Ingredient.EMPTY;
    }
}
