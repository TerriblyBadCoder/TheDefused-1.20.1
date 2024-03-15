package net.atired.thedefused.event;

import net.atired.thedefused.item.Moditems;
import net.minecraft.client.particle.Particle;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.SlotItemHandler;


public class CreeperLobotomyEvent {


    @SubscribeEvent
    public void CreeperInteract(PlayerInteractEvent.EntityInteract event)
    {
        //Lobotomy
        //
        if(event.getTarget().getType().toString().equals("entity.minecraft.creeper") & (event.getEntity().getMainHandItem().getItem() == Items.SHEARS | event.getEntity().getOffhandItem().getItem() == Items.SHEARS ))
        {
            InteractionHand hand = InteractionHand.MAIN_HAND;
            if(event.getEntity().getMainHandItem().getItem() != Items.SHEARS)
            {
                hand = InteractionHand.OFF_HAND;
            }
            Creeper creep = (Creeper)event.getTarget();
            if(!creep.isIgnited() & !creep.getTags().contains("defused_thedefused_mod"))
            {



                creep.addTag("defused_thedefused_mod");
                creep.setTarget((LivingEntity) null);
                Vec3 eh = event.getEntity().getViewVector(1);
                eh = eh.multiply(0.6,0.6,0.6);
                creep.setDeltaMovement(eh.x,eh.y + 0.2,eh.z);
                creep.setSwellDir(-60);

                Creeper_Lobotomy(event,creep);

                event.getEntity().swing(hand);


            }


        }
        //De-Lobotomy
        //
        else if(event.getTarget().getType().toString().equals("entity.minecraft.creeper") & (event.getEntity().getMainHandItem().getItem() == Items.GUNPOWDER | event.getEntity().getOffhandItem().getItem() == Items.GUNPOWDER ))
        {
            Creeper creep = (Creeper)event.getTarget();
            if(creep.getTags().contains("defused_thedefused_mod"))
            {
                InteractionHand hand = InteractionHand.MAIN_HAND;
                if(event.getEntity().getMainHandItem().getItem() != Items.GUNPOWDER)
                {
                    ItemStack Powder = event.getEntity().getOffhandItem();
                    if(!event.getEntity().isCreative())
                    {
                        Powder.setCount(Powder.getCount()-1);
                        event.getEntity().setItemInHand(InteractionHand.OFF_HAND,Powder);
                    }
                    hand = InteractionHand.OFF_HAND;
                }
                else
                {
                    ItemStack Powder = event.getEntity().getMainHandItem();
                    if(!event.getEntity().isCreative())
                    {
                        Powder.setCount(Powder.getCount()-1);
                        event.getEntity().setItemInHand(InteractionHand.MAIN_HAND,Powder);
                    }
                }
                creep.removeTag("defused_thedefused_mod");
                creep.goalSelector.addGoal(2, new HurtByTargetGoal(creep, new Class[0]));
                event.getEntity().swing(hand);


            }
        }
        else if(event.getTarget().getType().toString().equals("entity.minecraft.creeper") &
                (event.getEntity().getMainHandItem().getItem() == Items.POTION | event.getEntity().getOffhandItem().getItem() == Items.POTION ))
        {
            Creeper creep = (Creeper)event.getTarget();
            Player player =event.getEntity();
            InteractionHand hand = InteractionHand.MAIN_HAND;
            ItemStack Powder = player.getOffhandItem();
            if(player.getMainHandItem().getItem() != Items.POTION)
            {



                if(!player.isCreative())
                {
                    Powder.setCount(Powder.getCount()-1);
                }
                hand = InteractionHand.OFF_HAND;
            }
            else
            {
                Powder = player.getMainHandItem();
                if(!player.isCreative())
                {
                    Powder.setCount(Powder.getCount()-1);
                }
            }

            player.setItemInHand(hand,new ItemStack(Items.GLASS_BOTTLE));
            player.swing(hand);
            PotionUtils.getPotion(Powder);
            for(Object a : PotionUtils.getPotion(Powder).getEffects().toArray())
                creep.addEffect((MobEffectInstance)a );


        }


    }
    @SubscribeEvent
    public void Exploding(LivingDeathEvent event)
    {
        if(event.getSource().getDirectEntity() != null)
        if(event.getSource().getDirectEntity().getType() == EntityType.CREEPER
                && !event.getSource().getDirectEntity().level().isClientSide())
        {
            Creeper creeper = (Creeper) event.getSource().getDirectEntity();
            if(creeper.isPowered())
            {
                Vec3 vec3 = event.getEntity().getPosition(1);
                ItemEntity itemEntity = new ItemEntity(creeper.level(),vec3.x,vec3.y,vec3.z,new ItemStack(Moditems.CHARGEDASH.get(),(int)event.getEntity().getMaxHealth()/5));
                creeper.level().addFreshEntity(itemEntity);
            }
        }

    }
    @SubscribeEvent
    public void CreeperTick(LivingEvent.LivingTickEvent event)
    {
        if(event.getEntity().getTags() != null)
        if(event.getEntity().getType() == EntityType.CREEPER & event.getEntity().getTags().contains("defused_thedefused_mod"))
        {
            if(((Creeper)event.getEntity()).getSwellDir() != -1)
                ((Creeper)event.getEntity()).setSwellDir(-1);
        }
    }
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.RightClickBlock event)
    {
      Player interacter = event.getEntity();
      BlockState state = event.getLevel().getBlockState(event.getPos());
      if(state.getBlock() instanceof BushBlock & (interacter.getMainHandItem().getItem() == Moditems.PERFUME.get() || interacter.getOffhandItem().getItem() == Moditems.PERFUME.get()))
      {
          event.setCanceled(true);
          Double offset =0.5;
          InteractionHand hand = InteractionHand.MAIN_HAND;
          if(interacter.getMainHandItem().getItem() == Moditems.PERFUME.get())
          {
              if(!interacter.isCreative()) interacter.getMainHandItem().setCount(interacter.getMainHandItem().getCount()-1);
          }
          else
          {
              offset = -0.5;
              if(!interacter.isCreative()) interacter.getOffhandItem().setCount(interacter.getOffhandItem().getCount()-1);
              hand = InteractionHand.OFF_HAND;
          }
          Vec3 Positun = interacter.getPosition(1);
          Vec3 Eyes = interacter.getViewVector(1);
          Vec3 OffsetEyes = Eyes.yRot(-45).cross(Eyes.yRot(45));
          OffsetEyes = Eyes.cross(OffsetEyes);
          OffsetEyes = OffsetEyes.multiply(offset,offset,offset);
          System.out.println(interacter.getScale() + "  " + OffsetEyes + "  " + Eyes.cross(OffsetEyes));
          Positun = Positun.add(new Vec3(0,interacter.getEyeHeight()/1.6,0));
          Positun = Positun.add(OffsetEyes);
          for(int i = 0; i < 5; i++)
          event.getLevel().addParticle(ParticleTypes.SMOKE, Positun.x,Positun.y,Positun.z,
                  Eyes.x/6 + (Math.random()-0.5)/12, Eyes.y/6 + (Math.random()-0.5)/12,Eyes.z/6 + (Math.random()-0.5)/12 );
          interacter.swing(hand);
          if(!event.getLevel().isClientSide())
          {
              ItemEntity item = new ItemEntity(event.getLevel(), event.getPos().getX(), event.getPos().getY() ,event.getPos().getZ(), new ItemStack(state.getBlock().asItem(), 2) );
              event.getLevel().addFreshEntity(item);
              event.getLevel().setBlock(event.getPos(), new BlockState(Blocks.AIR, null,null), 0);
          }

      }
    }
    @SubscribeEvent
    public void CreepInit(EntityJoinLevelEvent event)
    {
        if(event.getEntity().getTags() != null)
        if(event.getEntity().getType() == EntityType.CREEPER & event.getEntity().getTags().contains("defused_thedefused_mod"))
        {

            Creeper creep = (Creeper) event.getEntity();
            Creeper_Lobotomy(event,creep);


        }
    }
    @SubscribeEvent
    public void CreepDamaged(LivingAttackEvent event)
    {
        if(event.getEntity().getTags() != null)
        if(event.getEntity().getType() == EntityType.CREEPER & event.getEntity().getTags().contains("defused_thedefused_mod"))
        {

            Creeper creep = (Creeper) event.getEntity();

            creep.setTarget((LivingEntity) null);
            creep.setSwellDir(-40);
            Creeper_Lobotomy(event,creep);


        }
    }
    static void Creeper_Lobotomy(Event event, Creeper creep)
    {
        creep.setTarget((LivingEntity) null);
        creep.getPersistentData().putInt("Yeah", 1);
        creep.setSwellDir(-60);
        for(Object a : creep.goalSelector.getAvailableGoals().toArray())
        {
            if((Goal)a instanceof SwellGoal || (Goal)a instanceof MeleeAttackGoal)
            {
                creep.goalSelector.removeGoal((Goal) a);
            }
        }
        for(Object a : creep.targetSelector.getAvailableGoals().toArray())
        {
            if(((WrappedGoal)a).getGoal() instanceof NearestAttackableTargetGoal<?> || ((WrappedGoal)a).getGoal() instanceof HurtByTargetGoal)
            {
                creep.targetSelector.removeGoal(((WrappedGoal) a).getGoal());
            }

        }
        creep.setSwellDir(-60);
    }
}
