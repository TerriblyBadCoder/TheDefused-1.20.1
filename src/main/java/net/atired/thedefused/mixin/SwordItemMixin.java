package net.atired.thedefused.mixin;


import com.mojang.blaze3d.shaders.Effect;
import net.atired.thedefused.particletypes.AnotherDustParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.model.DynamicFluidContainerModel;
import net.minecraftforge.eventbus.api.Event;
import org.jline.utils.Colors;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.List;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Inject(method = "hurtEnemy", at = @At(value = "TAIL"), cancellable = true)
    public void givePot(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfoReturnable<Boolean> cir)
    {
        if(PotionUtils.getPotion(pStack) != Potions.EMPTY)
        {


            CompoundTag compound = new CompoundTag();
            Integer charges = ((CompoundTag)pStack.serializeNBT().get("tag")).getInt("potioncharges");
            int color = PotionUtils.getColor(pStack);
            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = color & 0xff;
            Vec3 vec3 = new Vec3(r,g,b);
            vec3 = vec3.normalize();

            Vec3 pos = pTarget.getPosition(0);
            if(pTarget.level() instanceof ServerLevel serverLevel)
            {
                AABB box = pTarget.getBoundingBox();

                serverLevel.sendParticles((new AnotherDustParticleOptions(new Vector3f((float)vec3.x,(float)vec3.y,(float)vec3.z), ((float) box.getSize())*2F)),pos.x,pos.y + (box.maxY - box.minY)/2+0.1F,pos.z,1,0,0,0,0);
            }
            compound.putInt("potioncharges",charges-1);
            pStack.addTagElement("potioncharges",compound.get("potioncharges"));
            if(!PotionUtils.getPotion(pStack).hasInstantEffects())
            {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(PotionUtils.getPotion(pStack).getEffects().get(0).getEffect(),PotionUtils.getPotion(pStack).getEffects().get(0).getDuration()/5,PotionUtils.getPotion(pStack).getEffects().get(0).getAmplifier());
                pTarget.addEffect(mobEffectInstance);
            }
            else
            {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(PotionUtils.getPotion(pStack).getEffects().get(0).getEffect(), 1,PotionUtils.getPotion(pStack).getEffects().get(0).getAmplifier());
                pTarget.addEffect(mobEffectInstance);
            }
            if(charges <=1)
            {
                pStack.removeTagKey("potioncharges");
                pStack = PotionUtils.setPotion(pStack,Potions.EMPTY);

            }

        }

    }
    

}
