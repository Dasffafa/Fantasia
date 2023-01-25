package com.dasffafa.fantasia.common.events;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.item.FantasiaItems;
import com.dasffafa.fantasia.common.item.GoldKela;
import com.dasffafa.fantasia.common.utils.FantasiaSounds;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=Fantasia.MOD_ID)
public class FantasiaEvents {
    /*
     * There is a 1% chance that generated zombie will have GoldKela in hand.
     * Zombies without GoldKela will fight Zombies with GoldKela for them.
     */
    @SubscribeEvent
    public static void onZombieSpawnEvent(final LivingSpawnEvent.SpecialSpawn evt) {
        LivingEntity entity = evt.getEntityLiving();
        if (entity instanceof Zombie zombie) {
            float v = zombie.getRandom().nextFloat();
            if (zombie.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(Items.AIR) && v <= 0.2) {
                zombie.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FantasiaItems.GOLD_KELA.get(), 1));
                zombie.setDropChance(EquipmentSlot.MAINHAND,0.8f);
                zombie.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>((Mob) entity, Zombie.class, true));
            }
        }
    }

    /*
     * When zombies with GoldKela attacks, it will play sound of traditional GoldKela Conflict.
     */
    @SubscribeEvent
    public static void onZombieWithGoldKelaAttack(final LivingAttackEvent evt) {
        LivingEntity entity = evt.getEntityLiving();
        if (entity instanceof Zombie zombie) {
            float v = entity.getRandom().nextFloat();
            if (zombie.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GoldKela && v <= 0.33) {
                entity.playSound(FantasiaSounds.GOLD_KELA_CONFLICT.get(), 0.5F, 1f);
            }
        }
    }
}
