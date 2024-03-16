package net.atired.thedefused.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class Modfoods {
    public static final FoodProperties SHULKERMEAT = new FoodProperties.Builder().alwaysEat().fast().meat().nutrition(2).saturationMod(0.2f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 150), 0.5f).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 150, 2), 0.5f).build();
    public static final FoodProperties BEJEVELEDAPPLE = new FoodProperties.Builder().alwaysEat().nutrition(4).saturationMod(5f).effect(()-> new MobEffectInstance(MobEffects.ABSORPTION, 150, 0), 1f).build();
}
