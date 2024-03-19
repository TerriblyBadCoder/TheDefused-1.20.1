package net.atired.thedefused.mixin;


import net.atired.thedefused.enchantment.ModEnchantments;
import net.atired.thedefused.particle.ModParticles;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.openjdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public class ShieldItemMixin {

    
    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    private InteractionResultHolder<ItemStack> chargeNtarge(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<Boolean> cir)
    {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(!pPlayer.getCooldowns().isOnCooldown(itemstack.getItem()) & itemstack.getEnchantmentLevel(ModEnchantments.TACKLER.get()) != 0)
        {
            int Elevel = itemstack.getEnchantmentLevel(ModEnchantments.TACKLER.get());
            pPlayer.getCooldowns().addCooldown(itemstack.getItem(), 30);
            Vec3 vec3 = pPlayer.getViewVector(0);
            vec3 = vec3.multiply(1,0,1);
            vec3 = vec3.normalize();
            vec3 = vec3.multiply(3,0,3);
            Vec3 offsetEyes = pPlayer.getEyePosition().add(vec3);
            vec3 = vec3.multiply(0.5,0,0.5);
            LivingEntity livingEntity = null;

            Object[] victimArray = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(offsetEyes.add(-2.5,-3,-2.5),offsetEyes.add(2.5,1,2.5))).toArray();
            for(Object a : victimArray)
            {

                if((LivingEntity)a != (LivingEntity)pPlayer)
                {
                    livingEntity = (LivingEntity)a;
                    if(!pLevel.isClientSide)
                    {
                        livingEntity.hurt(pLevel.damageSources().playerAttack(pPlayer),1*Elevel + 1);
                        livingEntity.setDeltaMovement(vec3.multiply(0.7 + Elevel*0.1,2,0.7  + Elevel*0.1).add(0,0.2  + Elevel*0.05,0));
                    }
                    Vec3 entiPos = livingEntity.getPosition(0).add(0,1.5,0);
                    if(pLevel.isClientSide())
                    {
                        pLevel.playSound(pPlayer, new BlockPos((int)entiPos.x,(int)entiPos.y,(int)entiPos.z), SoundEvents.SHIELD_BLOCK,SoundSource.PLAYERS);
                        for(int i = 0; i < 5; i++)
                            pLevel.addParticle(ParticleTypes.ENCHANTED_HIT,entiPos.x+Math.random()-0.5,entiPos.y+Math.random()-0.5,entiPos.z+Math.random()-0.5,
                                    vec3.x,0.2,vec3.z);
                        pLevel.addParticle(ModParticles.BASH_PARTICLES.get(),entiPos.x,entiPos.y,entiPos.z,
                                vec3.x/2,0.1,vec3.z/2);
                    }

                }
            }
                itemstack.hurtAndBreak(2,pPlayer,(p_43414_) -> {
                    p_43414_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });



            pPlayer.setDeltaMovement(vec3);
        }


        return InteractionResultHolder.consume(itemstack);
    }
}
