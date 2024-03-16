package net.atired.thedefused.event;

import net.atired.thedefused.enchantment.ModEnchantments;
import net.atired.thedefused.particle.ModParticles;
import net.atired.thedefused.particle.custom.CombustionParticles;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingGetProjectileEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class ShovelEnchants {
    private static final String TAG_COMBUSTIBLE = "thedefused:trident_combusts";
    @SubscribeEvent
    public void Highland(LivingDamageEvent event)
    {

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
                        ServerLevel serverLevel= (ServerLevel)faller.level();
                        serverLevel.sendParticles(ParticleTypes.FLASH,living.getX(),living.getY()+1,living.getZ(),1,0,0,0,0);
                    }
                }

            }

    }
    @SubscribeEvent
    public void combust(EntityJoinLevelEvent event) {
        if(event.getEntity().getType() == EntityType.TRIDENT)
        {

            ThrownTrident trident = (ThrownTrident) event.getEntity();
            Player player = (Player)trident.getOwner();
            if(player != null)
            if(player.getMainHandItem().getEnchantmentLevel(ModEnchantments.COMBUST.get()) != 0)
            {
                trident.getPersistentData().putBoolean(TAG_COMBUSTIBLE,true);
                if(!event.getEntity().level().isRaining() & !event.getEntity().level().isThundering() )
                trident.setSecondsOnFire(100);
            }

        }
    }


    @SubscribeEvent
    public void combusthit(ProjectileImpactEvent event)
    {
        System.out.println(event.getProjectile().level());
        if(event.getProjectile().getPersistentData().getBoolean(TAG_COMBUSTIBLE) & event.getProjectile().level().isClientSide())
        {
            Vec3 pos = event.getProjectile().getPosition(1);
            for(int i = 0; i < 2; i++)
            {
                System.out.println("HEH");
                event.getEntity().level().addParticle(ModParticles.COMBUSTION_PARTICLES.get(),pos.x+ (Math.random()-0.5)*2,pos.y+ (Math.random()-0.5)*2,pos.z+ (Math.random()-0.5)*2,0,0,0);
            }
            event.getProjectile().getPersistentData().putBoolean(TAG_COMBUSTIBLE,false);
        }

        if(event.getProjectile().getPersistentData().getBoolean(TAG_COMBUSTIBLE) & !event.getProjectile().level().isClientSide())
        {
            Boolean bool = false;
            if(!event.getProjectile().level().isRaining() & !event.getProjectile().level().isThundering() )
                bool = true;
            Projectile projectile = event.getProjectile();
            projectile.getPersistentData().putBoolean(TAG_COMBUSTIBLE,false);
            Level pLevel = projectile.level();
            Vec3 pPos = event.getRayTraceResult().getLocation();
            pLevel.explode(projectile.getOwner(),pLevel.damageSources().lightningBolt(),new ExplosionDamageCalculator(),pPos.x,pPos.y,pPos.z,
                    (float)0.7,bool, Level.ExplosionInteraction.NONE);


        }
    }
    @SubscribeEvent
    public void combustfly(LivingEntityUseItemEvent.Stop event)
    {


        if(event.getItem().getEnchantmentLevel(ModEnchantments.COMBUST.get()) != 0 & event.getItem().getEnchantmentLevel(Enchantments.RIPTIDE) != 0)
        {

            Vec3 eyePosition = event.getEntity().getPosition(1);
            Vec3 viewPosition = event.getEntity().getViewVector(1);
            viewPosition = viewPosition.multiply(-1,-1,-1);
            if(event.getEntity().level().isClientSide())
            {


                event.getEntity().level().addParticle(ModParticles.COMBUSTION_PARTICLES.get(),eyePosition.x,eyePosition.y,eyePosition.z,0,0,0);
                for(int i = 0; i < 4; i++)
                {
                    event.getEntity().level().addParticle(ParticleTypes.FLAME,eyePosition.x+ (Math.random()-0.5)*2,eyePosition.y+ (Math.random()-0.5)*2,eyePosition.z+ (Math.random()-0.5)*2,viewPosition.x,viewPosition.y,viewPosition.z);
                }
            }
            viewPosition = viewPosition.multiply(0.8,0.4,0.8);
            event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().add(viewPosition.x,viewPosition.y+0.2,viewPosition.z));
        }
    }
}
