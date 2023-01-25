package com.dasffafa.fantasia.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class SplashingShitParticle extends TextureSheetParticle {
    protected SplashingShitParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.8F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;

        this.quadSize = 0.3F * random.nextFloat();
        this.lifetime = 100;
        this.gravity = 2F + 2F * random.nextFloat();
        this.setSpriteFromAge(spriteSet);

        this.rCol = 0.99F + 0.01F*random.nextFloat();
        this.gCol = 0.99F + 0.01F*random.nextFloat();
        this.bCol = 0.99F + 0.01F*random.nextFloat();
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = Math.max(1,5 - 5 * (age / lifetime));

    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new SplashingShitParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }

}
