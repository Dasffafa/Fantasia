package com.brotherJiang.fantasia.common.world.spawn;

import com.brotherJiang.fantasia.Fantasia;
import com.brotherJiang.fantasia.common.item.FantasiaItems;
import com.brotherJiang.fantasia.common.item.GoldKela;
import com.brotherJiang.fantasia.common.utils.FantasiaSoundTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fantasia.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GiveThemGoldKelaOnZombieSpawn {
    @SubscribeEvent
    public static void onZombieSpawnEvent(LivingSpawnEvent.SpecialSpawn evt){
        LivingEntity entity = evt.getEntityLiving();
        if (entity instanceof ZombieEntity && entity.getHeldItem(Hand.MAIN_HAND).getItem().equals(Items.AIR) && entity.getRNG().nextFloat() <= 0.01){
            entity.setHeldItem(Hand.MAIN_HAND,new ItemStack(FantasiaItems.GOLD_KELA.get(), 1));
            ((ZombieEntity) entity).targetSelector.addGoal(0, new NearestAttackableTargetGoal<ZombieEntity>((MobEntity) entity, ZombieEntity.class, true));
        }
    }

    @SubscribeEvent
    public static void onZombieWithGoldKelaAttack(LivingAttackEvent evt){
        LivingEntity entity = evt.getEntityLiving();
        if (entity instanceof ZombieEntity && entity.getHeldItem(Hand.MAIN_HAND).getItem() instanceof GoldKela && entity.world.rand.nextFloat() <= 0.33){
            entity.playSound(FantasiaSoundTypes.GOLD_KELA_CONFLICT.get(),0.5F, 1f);
        }
    }
}
