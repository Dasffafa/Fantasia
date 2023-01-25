package com.dasffafa.fantasia.common.painting;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class FantasiaPaintings {
    public static final DeferredRegister<Motive> PAINTINGS = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Fantasia.MOD_ID);

    public static final RegistryObject<Motive> ARKWIVES = PAINTINGS.register("arkwives", () -> new Motive(32,48));
    public static final RegistryObject<Motive> IRON_BALL = PAINTINGS.register("iron_ball_of_redemption", () -> new Motive(16,32));
    public static final RegistryObject<Motive> MOTHER = PAINTINGS.register("schrodinger_s_mother", () -> new Motive(32,32));
    public static final RegistryObject<Motive> JY_AND_MESTERIOUS_PERSON = PAINTINGS.register("mysterious_person_and_maid", () -> new Motive(32,48));

    public static final RegistryObject<Motive> STAGE_PHOTO = PAINTINGS.register("stage_photo", () -> new Motive(32, 64));
    public static final RegistryObject<Motive> LEMON_BADGE = PAINTINGS.register("lemon_badge", () -> new Motive(64, 64));
    public static final RegistryObject<Motive> RIO_BADGE = PAINTINGS.register("rio_badge", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> IMMORTAL_CHURCH_BADGE = PAINTINGS.register("immortal_church_badge", () -> new Motive(32, 32));
    public static final RegistryObject<Motive> GOLD_KELA_TRUCK = PAINTINGS.register("gold_kela_truck", () -> new Motive(32, 16));
}
