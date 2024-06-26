package net.atired.thedefused.particle;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.particletypes.DustParticleType;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DefusedMod.MODID);
    public static final RegistryObject<SimpleParticleType> SHALE_PARTICLES = PARTICLE_TYPES.register("shale_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VOID_PARTICLES = PARTICLE_TYPES.register("void_spark_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BASH_PARTICLES = PARTICLE_TYPES.register("bash_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> COLLAPSE_PARTICLES = PARTICLE_TYPES.register("collapse_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> COMBUSTION_PARTICLES = PARTICLE_TYPES.register("combustion_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> VOLATILE_PARTICLES = PARTICLE_TYPES.register("volatile_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<DustParticleType> SKULL_PARTICLES = PARTICLE_TYPES.register("skull_particles", () -> new DustParticleType(true));
    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
