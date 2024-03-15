package net.atired.thedefused.effect;

import net.atired.thedefused.DefusedMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DefusedMod.MODID);

    public static final RegistryObject<MobEffect> TOXIC = MOB_EFFECTS.register("toxic",
            () -> new ToxicEffect(MobEffectCategory.HARMFUL, 9001202));
    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
