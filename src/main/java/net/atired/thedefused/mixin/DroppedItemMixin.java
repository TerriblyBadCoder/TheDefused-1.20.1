package net.atired.thedefused.mixin;


import net.atired.thedefused.particle.ModParticles;
import net.atired.thedefused.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class DroppedItemMixin extends Entity {
    public DroppedItemMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow public abstract ItemStack getItem();

    @Inject(method = "tick", at = @At(value = "TAIL"), cancellable = true)
    private void td_tick(CallbackInfo ci){
        if(getItem().getTags().toList().contains(ModTags.Items.VOID_TOUCHED))
        {

            this.setNoGravity(true);
            if(tickCount % (int)(3/getDeltaMovement().length()) == 0 & getDeltaMovement().length() > 0.3)
            {

                Vec3 vec3 = getDeltaMovement().multiply(-3,-3,-3);
                level().addParticle(ModParticles.VOID_PARTICLES.get(),getX()+(Math.random()-0.5)*getDeltaMovement().length()*2,getY()+(Math.random()-0.5)*getDeltaMovement().length()*2,getZ()+(Math.random()-0.5)*getDeltaMovement().length()*2,vec3.x+(Math.random()-0.5)/2,vec3.y+(Math.random()-0.5)/2,vec3.z+(Math.random()-0.5)/2);
            }
        }
    }
    

}
