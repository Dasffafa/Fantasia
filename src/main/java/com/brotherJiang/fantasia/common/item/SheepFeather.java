package com.brotherJiang.fantasia.common.item;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.entity.projectile.SheepFeatherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SheepFeather extends Item {
    public SheepFeather() {
        super(new Properties().group(Fantasia.GROUP));
    }

    public @NotNull ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            SheepFeatherEntity sheepFeatherEntity = new SheepFeatherEntity(worldIn, playerIn);
            sheepFeatherEntity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.7F, 0.4F);
            worldIn.addEntity(sheepFeatherEntity);
        }
        itemstack.shrink(1);
        return ActionResult.resultConsume(itemstack);
    }
}
