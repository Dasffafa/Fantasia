package com.brotherJiang.fantasia.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FantasiaConfigHandler {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.IntValue FEATHERED_SHEEP_HEALTH;
    public static ForgeConfigSpec.IntValue FEATHERED_SHEEP_CHARGE_DAMAGE;
    public static ForgeConfigSpec.IntValue FEATHERED_SHEEP_BOMB_DAMAGE;
    public static ForgeConfigSpec.DoubleValue FEATHERED_SHEEP_GEN_RATE;
    public static ForgeConfigSpec.BooleanValue FEATHERED_SHEEP_DAMAGES_TERRAIN;
    public static ForgeConfigSpec.BooleanValue FEATHERED_SHEEP_DAMAGES_BUSH;
    public static ForgeConfigSpec.IntValue FEATHERED_SHEEP_WIDE_BOMB_SP;

    static {
        ForgeConfigSpec.Builder FEATHERED_SHEEP_BUILDER = new ForgeConfigSpec.Builder();
        FEATHERED_SHEEP_BUILDER.comment("Set the basic params of feathered sheep").push("feathered_sheep");
        FEATHERED_SHEEP_HEALTH = FEATHERED_SHEEP_BUILDER.comment("The maximum health of the feathered sheep").defineInRange("feathered_sheep_max_hp", 30, 0, Integer.MAX_VALUE);
        FEATHERED_SHEEP_CHARGE_DAMAGE = FEATHERED_SHEEP_BUILDER.defineInRange("feathered_sheep_charge_damage",10,0, Integer.MAX_VALUE);
        FEATHERED_SHEEP_BOMB_DAMAGE = FEATHERED_SHEEP_BUILDER.defineInRange("feathered_sheep_bomb_damage",8,0, Integer.MAX_VALUE);
        FEATHERED_SHEEP_GEN_RATE = FEATHERED_SHEEP_BUILDER.defineInRange("feathered_sheep_gen_rate",0.1,0, Double.MAX_VALUE);
        FEATHERED_SHEEP_DAMAGES_TERRAIN = FEATHERED_SHEEP_BUILDER.define("feathered_sheep_damages_terrain", true);
        FEATHERED_SHEEP_DAMAGES_BUSH= FEATHERED_SHEEP_BUILDER.define("feathered_sheep_damages_bush", true);
        FEATHERED_SHEEP_WIDE_BOMB_SP = FEATHERED_SHEEP_BUILDER.defineInRange("feathered_sheep_bomb_damage",3,0, Integer.MAX_VALUE);
        FEATHERED_SHEEP_BUILDER.pop();
        SERVER_CONFIG = FEATHERED_SHEEP_BUILDER.build();
    }
}
