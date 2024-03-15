package net.atired.thedefused.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ToxicEffect extends MobEffect{
    public ToxicEffect(MobEffectCategory mobEffectCategory, int color)
    {
        super(mobEffectCategory,color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.getHealth() > (pLivingEntity.getHealth()+pLivingEntity.getAbsorptionAmount())/3) {
            pLivingEntity.hurt(pLivingEntity.damageSources().magic(), (pLivingEntity.getHealth()+pLivingEntity.getAbsorptionAmount())/3);


         }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
    @Override


    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int i;
        i = 15 >> pAmplifier;
        if (i > 0) {
            return pDuration % i == 0;
        } else {
            return true;
        }
    }
}
