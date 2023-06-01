package com.dasffafa.fantasia.common.item;

import com.dasffafa.fantasia.Fantasia;
import com.dasffafa.fantasia.common.network.FantasiaNetworking;
import com.dasffafa.fantasia.common.network.SendPack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;

public class TestItem extends Item {
    public TestItem() {
        super(new Properties().tab(Fantasia.TAB).stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();

        return super.useOn(pContext);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player pPlayer, InteractionHand pUsedHand) {
        if (worldIn.isClientSide) {
            FantasiaNetworking.INSTANCE.sendToServer(new SendPack("From the Client"));
        }
        if (!worldIn.isClientSide) {
            FantasiaNetworking.INSTANCE.send(
                    PacketDistributor.PLAYER.with(
                            () -> (ServerPlayer) pPlayer
                    ),
                    new SendPack("From Server"));
        }
        return super.use(worldIn, pPlayer, pUsedHand);
    }
}
