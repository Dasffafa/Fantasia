package com.dasffafa.fantasia.common.entity.projectile;

import com.dasffafa.fantasia.common.entity.FantasiaEntities;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SheepFeatherEntity extends AbstractFantasiaProjectile implements ItemSupplier {
    public SheepFeatherEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SheepFeatherEntity(Level pLevel) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), pLevel);
    }

    public SheepFeatherEntity(LivingEntity pShooter, Level pLevel) {
        super(FantasiaEntities.SHEEP_FEATHER.get(), pLevel);
        this.setOwner(pShooter);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(FantasiaItems.SHEEP_FEATHER.get());
    }
}

