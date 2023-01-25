package com.dasffafa.fantasia.common.effects;

import com.dasffafa.fantasia.common.utils.FantasiaSounds;
import com.dasffafa.fantasia.common.utils.FantasiaDamageSources;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class HiccupEffect extends MobEffect {
    private final boolean causedByGoldKela;
    public HiccupEffect(boolean causedByGoldKela) {
        super(MobEffectCategory.HARMFUL, 0x123456);
        this.causedByGoldKela = causedByGoldKela;
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        amplifier++;
        entityLivingBaseIn.playSound(FantasiaSounds.HICCUP.get(), 1.0F, 1.0F);
        Random random = entityLivingBaseIn.getRandom();

        switch (random.nextInt(10)) {
            case 1:
                if (entityLivingBaseIn instanceof Player player) {
                    player.drop(player.getItemInHand(InteractionHand.MAIN_HAND), true, false);
                    player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.AIR));
                    if (player.level.isClientSide) {
                        player.displayClientMessage(new TranslatableComponent("effect.fantasia.hiccup.drop"), true);
                    }
                }
            case 2,3,4,5:
                if (causedByGoldKela) {
                    entityLivingBaseIn.hurt(FantasiaDamageSources.GOLD_KELA, 4 * amplifier);
                }
                entityLivingBaseIn.hurt(FantasiaDamageSources.HICCUP, 4 * amplifier);
        }
        entityLivingBaseIn.setDeltaMovement(
                entityLivingBaseIn.getDeltaMovement().add(
                        0.3 * amplifier * random.nextFloat() - 0.15,
                        0.25 * amplifier,
                        0.3 * amplifier * random.nextFloat() - 0.15
                )
        );
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 40 == 0;
    }
}
