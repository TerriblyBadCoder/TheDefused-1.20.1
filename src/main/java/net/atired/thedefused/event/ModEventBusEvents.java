package net.atired.thedefused.event;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.particle.ModParticles;
import net.atired.thedefused.particle.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DefusedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ModParticles.SHALE_PARTICLES.get(), ShaleParticles.Provider::new);
        event.registerSpriteSet(ModParticles.COMBUSTION_PARTICLES.get(), CombustionParticles.Provider::new);
        event.registerSpriteSet(ModParticles.BASH_PARTICLES.get(), BashParticles.Provider::new);
        event.registerSpriteSet(ModParticles.VOLATILE_PARTICLES.get(), VolatileParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SKULL_PARTICLES.get(), SkullParticles.Provider::new);
        event.registerSpriteSet(ModParticles.VOID_PARTICLES.get(), VoidParticles.Provider::new);
        event.registerSpriteSet(ModParticles.COLLAPSE_PARTICLES.get(), CollapseParticles.Provider::new);
    }
}
