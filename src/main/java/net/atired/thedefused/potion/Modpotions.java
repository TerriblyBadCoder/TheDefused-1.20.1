package net.atired.thedefused.potion;

import net.atired.thedefused.DefusedMod;
import net.atired.thedefused.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Modpotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, DefusedMod.MODID);
    public static final RegistryObject<Potion> TOXIC =
            POTIONS.register("toxic",() -> new Potion(new MobEffectInstance(ModEffects.TOXIC.get(),200,0)));
    public static final RegistryObject<Potion> HASTE =
            POTIONS.register("haste",() -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED,3600,0)));
    public static final RegistryObject<Potion> MINING_FATIGUE =
            POTIONS.register("mining_fatigue",() -> new Potion(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,3600,0)));
    public static void register(IEventBus eventBus) { POTIONS.register(eventBus); }
}
