package com.dasffafa.fantasia.common.item;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.effects.FantasiaEffects;
import com.dasffafa.fantasia.common.utils.FantasiaSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GoldKela extends Item {
    private static final FoodProperties goldKelaProperty = new FoodProperties.Builder()
            .saturationMod(999)
            .nutrition(999)
            .alwaysEat()
            .build();
    public GoldKela() {
        super(new Properties().tab(Fantasia.TAB).stacksTo(16).food(goldKelaProperty));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level world, LivingEntity eater) {
        world.playSound(null,
                eater.getX(),
                eater.getY(),
                eater.getZ(),
                FantasiaSounds.GOLD_KELA_USE.get(),
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );

        applyGoldKelaEffect(eater);
        if (world.isClientSide){
            eater.sendMessage(new TranslatableComponent("item.fantasia.gold_kela.use"), eater.getUUID());
        }
        pStack.shrink(1);
        return pStack;
    }

    public static void applyGoldKelaEffect(LivingEntity eater) {
        eater.playSound(FantasiaSounds.GOLD_KELA_USE.get(), 1.0F, 1.0F);
        eater.addEffect(new MobEffectInstance(MobEffects.REGENERATION,1200,1));
        eater.addEffect(new MobEffectInstance(MobEffects.GLOWING,1200));
        eater.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,1200));
        eater.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,1200,2));
        eater.addEffect(new MobEffectInstance(MobEffects.SATURATION,1200));
        eater.addEffect(new MobEffectInstance(FantasiaEffects.GOLD_KELA_HICCUP.get(),1200,1));
        eater.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("item.fantasia.gold_kela.desc")
                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
        pTooltipComponents.add(new TranslatableComponent("item.fantasia.gold_kela.copyright"));
    }

    @Nonnull
    @Override
    public Component getName(ItemStack pStack) {
        return new TranslatableComponent(this.getDescriptionId(pStack))
                .withStyle(ChatFormatting.GOLD);
    }
}
