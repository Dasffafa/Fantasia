package com.dasffafa.fantasia.common.network;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendPack {
    private String message;


    public SendPack(String message) {
        this.message = message;
    }

    public SendPack(FriendlyByteBuf buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Fantasia.LOGGER.info(this.message);
            if (ctx.get().getSender() != null) {
                ctx.get().getSender().swing(InteractionHand.MAIN_HAND,true);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(message);
    }
}
