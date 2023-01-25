package com.dasffafa.fantasia.common.effects;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

/*
 * FearEffect has the ability to let mobs flee from players
 * or let players unable to attack mobs.
 * This effect only acts as a mark for mobs;
 * when you want to add this effect to mobs,
 * you should invoke #setMobVulnerableToFear to the mob.
 * This could not be added by mob to mob.
 */
public class FearEffect extends MobEffect {
    public FearEffect() {
        super(MobEffectCategory.HARMFUL, 0xfff143);
    }

    private static boolean isWeakCreature(LivingEntity entity) {
        return entity.getMaxHealth() >= 30;
    }
    public static void setMobVulnerableToFear(@Nonnull LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof PathfinderMob pathfinderMob && isWeakCreature(pLivingEntity)) {
            Goal fleeFromPlayerGoal = new PanicGoal(pathfinderMob, 2.0f)
            {
                @Override
                public boolean canUse() {
                    return super.canUse() && pLivingEntity.hasEffect(FantasiaEffects.FEAR.get());
                }

                @Override
                protected boolean shouldPanic() {
                    return true;
                }
            };
            pathfinderMob.setNoAi(true);
            pathfinderMob.goalSelector.addGoal(0, fleeFromPlayerGoal);
        }
    }

    @SubscribeEvent
    public static void onPlayerAttackMob(AttackEntityEvent event) {
        Player player = event.getPlayer();
        if (player.hasEffect(FantasiaEffects.FEAR.get())) {
            event.setCanceled(true);
            player.sendMessage(new TranslatableComponent("chat.fantasia.fear.cannotAttack")
                            .withStyle(ChatFormatting.RED)
                            .withStyle(ChatFormatting.BOLD)
                    , player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onPotionExpiry(PotionEvent.PotionExpiryEvent event) {
        if (event.getEntityLiving() instanceof PathfinderMob mob) {
            mob.setNoAi(false);
        }
    }
}
