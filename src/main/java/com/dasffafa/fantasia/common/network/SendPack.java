package com.dasffafa.fantasia.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class SendPack {
    private String message;
    private static final Logger LOGGER = LogManager.getLogger();

    public SendPack(String message) {
        this.message = message;
    }

    public SendPack(FriendlyByteBuf buffer) {
        message = buffer.readUtf(Short.MAX_VALUE);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LOGGER.info(this.message);
        });
        ctx.get().setPacketHandled(true);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(message);
    }
}
