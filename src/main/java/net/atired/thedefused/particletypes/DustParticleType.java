package net.atired.thedefused.particletypes;

import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleType;

public class DustParticleType extends ParticleType<AnotherDustParticleOptions> {

    public DustParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, AnotherDustParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<AnotherDustParticleOptions> codec() {
        return AnotherDustParticleOptions.CODEC;
    }

}