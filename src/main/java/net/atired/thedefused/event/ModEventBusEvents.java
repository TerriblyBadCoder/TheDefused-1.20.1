package net.atired.thedefused.event;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.particle.ModParticles;
import net.atired.thedefused.particle.custom.CombustionParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DefusedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        System.out.println("Yooo");
        Minecraft.getInstance().particleEngine.register(ModParticles.COMBUSTION_PARTICLES.get(), CombustionParticles.Provider::new);
    }
}
