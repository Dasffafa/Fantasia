package com.dasffafa.fantasia.common.item.tools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public enum FantasiaItemTiers implements Tier {
    EXCREMENT(0, 30, 0.1F, 2F, 2),

    POOP(0, 40, 0.1F, 1.5F, 3),

    SHEEP_FEATHER(3, 400, 3F, 5.5F, 20);

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;

    private Ingredient repairMaterial;

    FantasiaItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    FantasiaItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Ingredient repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial != null ? this.repairMaterial : Ingredient.EMPTY;
    }
}
