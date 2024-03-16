package net.atired.thedefused.item.custom;

import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BejeveledApple extends Item {
    public BejeveledApple(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        System.out.println(pLevel);
        Object[] array = pLevel.getEntitiesOfClass(LivingEntity.class,
                new AABB(pLivingEntity.getPosition(1).add(-5,-2,-5)
                        ,pLivingEntity.getPosition(1).add(5,2,5))).toArray();

        Vec3 pos;
        Vec3 diff;
        if(array.length!=0)

            for(Object a : array)
            {
                pos = ((LivingEntity)a).getPosition(1);
                diff = pos.subtract(pLivingEntity.getPosition(1)).normalize();
                if(((LivingEntity)a) != pLivingEntity)
                {
                    ((LivingEntity)a).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,80,4));
                    ((LivingEntity)a).addEffect(new MobEffectInstance(MobEffects.BLINDNESS,70,0));
                    ((LivingEntity)a).setDeltaMovement(diff.x,diff.y + 0.5,diff.z + 0.5);
                    if(pLevel.isClientSide() ) {
                        for (int i = 0; i < 5; i++)
                            pLevel.addParticle(ParticleTypes.SMOKE, pos.x + Math.random() - 0.5, pos.y + Math.random() - 0.5, pos.z + Math.random() - 0.5,(Math.random() - 0.5)/4 , 0.1, (Math.random() - 0.5)/4);
                    }
                }

            }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);

    }
}
