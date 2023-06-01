package com.dasffafa.fantasia.common.item;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.entity.projectile.SheepFeatherEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SheepFeatherItem extends Item {
    public SheepFeatherItem() {
        super(new Properties().tab(Fantasia.TAB).stacksTo(16));
    }

    public static SheepFeatherEntity createSheepFeather(Level pLevel, Player pPlayer) {
        return createSheepFeather(pLevel, pPlayer, new ItemStack(FantasiaItems.SHEEP_FEATHER.get(), 1));
    }

    public static SheepFeatherEntity createSheepFeather(Level pLevel, Player pPlayer, @Nullable ItemStack stack) {
        SheepFeatherEntity sheepFeather;
        if (stack != null) {
            sheepFeather = new SheepFeatherEntity(pPlayer, pLevel, stack);
        } else {
            sheepFeather = new SheepFeatherEntity(pPlayer, pLevel);
        }
        sheepFeather.setPos(pPlayer.getX(), pPlayer.getEyeY(), pPlayer.getZ());
        LivingEntity lastHurtByMob = pPlayer.getLastHurtByMob();
        if (lastHurtByMob != null && !lastHurtByMob.isDeadOrDying()) {
            sheepFeather.setTarget(lastHurtByMob);
        } else {
            LivingEntity lastHurtMob = pPlayer.getLastHurtMob();
            if (lastHurtMob != null && !lastHurtMob.isDeadOrDying()) {
                sheepFeather.setTarget(lastHurtMob);
            }
        }
        return sheepFeather;
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide) {
            SheepFeatherEntity sheepFeather = createSheepFeather(pLevel, pPlayer);
            sheepFeather.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2.5F, 0F);
            pLevel.addFreshEntity(sheepFeather);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.consume(itemstack);
    }
}
