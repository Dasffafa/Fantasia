package com.dasffafa.fantasia.common.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class FantasiaNetworking {
    private static final String VERSION = "1.0";
    private static final String SHEEP_FEATHER_SWORD_AUTO_ATTACK = "sheep_feather_sword_auto_attack";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("fantasia", SHEEP_FEATHER_SWORD_AUTO_ATTACK),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION));
        INSTANCE.registerMessage(
                nextID(),
                SendPack.class,
                SendPack::toBytes,
                SendPack::new,
                SendPack::handler
        );
    }
}
