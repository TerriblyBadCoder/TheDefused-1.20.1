package net.atired.thedefused.event;

import net.atired.thedefused.enchantment.ModEnchantments;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShovelEnchants {
    @SubscribeEvent
    public void Highland(LivingDamageEvent event)
    {
        System.out.println(event.getEntity().level());
        if (event.getSource().getDirectEntity() != null)
            if(event.getSource().getDirectEntity() instanceof Player)
            {


                Player faller = (Player) event.getSource().getDirectEntity();
                int enchantmentLevel = faller.getMainHandItem().getEnchantmentLevel(ModEnchantments.HIGHLANDER.get());
                float fall = faller.fallDistance;
                LivingEntity living = event.getEntity();

                if(enchantmentLevel!=0)
                {
                    event.setAmount(event.getAmount()+((fall*enchantmentLevel)/2));
                    if(fall > (float)0.5 & !faller.level().isClientSide())
                    {
                        System.out.println(event.getAmount()+((fall*enchantmentLevel)/1.5) + " " + event.getEntity().level());


                        ServerLevel serverLevel= (ServerLevel)faller.level();
                        serverLevel.sendParticles(ParticleTypes.FLASH,living.getX(),living.getY()+1,living.getZ(),1,0,0,0,0);
                    }
                }

            }

    }
}
