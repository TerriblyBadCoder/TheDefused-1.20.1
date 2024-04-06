package net.atired.thedefused.mixin;


import net.atired.thedefused.particle.ModParticles;
import net.atired.thedefused.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{

    @Shadow protected abstract void actuallyHurt(DamageSource pDamageSource, float pDamageAmount);

    @Shadow protected abstract void playHurtSound(DamageSource pSource);

    @Shadow @Nullable protected Player lastHurtByPlayer;

    @Shadow @Nullable private LivingEntity lastHurtByMob;

    @Shadow public abstract boolean hurt(DamageSource pSource, float pAmount);

    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"), cancellable = true)
    private void td_tick(CallbackInfo ci){
        int delay = this.getPersistentData().getInt("thedefused:collapse_delay");
        float hurtinger = this.getPersistentData().getFloat("thedefused:collapse");
        if(delay > 0 & hurtinger > 0)
        {
            this.getPersistentData().putInt("thedefused:collapse_delay",delay-1);
            if(delay-1 == 0)
            {
                invulnerableTime = -1;
                if(level() instanceof ServerLevel serverLevel)
                {
                    serverLevel.sendParticles(ModParticles.COLLAPSE_PARTICLES.get(),getX(),getY() + getBoundingBox().getYsize()/2,getZ(),1,0.1,0.1,0.1,0);
                }

                this.getPersistentData().putFloat("thedefused:collapse",0);
                if(lastHurtByPlayer != null)
                {
                    System.out.println("Eh");
                    hurt(this.damageSources().playerAttack(lastHurtByPlayer),hurtinger);
                }
                else if (lastHurtByMob != null) {
                    System.out.println("Eh");
                    hurt(this.damageSources().mobAttack(lastHurtByMob),hurtinger);
                }
                else
                {

                }

            }

        }
    }
    

}
