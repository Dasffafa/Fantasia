package com.dasffafa.fantasia.client.particle;

import com.dasffafa.fantasia.Fantasia;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FantasiaParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Fantasia.MOD_ID);
    public static final RegistryObject<SimpleParticleType> WIND_PARTICLE = PARTICLES.register("wind_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPLASHING_SHIT_PARTICLE = PARTICLES.register("splashing_shit_particles", () -> new SimpleParticleType(true));
}