package net.atired.thedefused.event;

import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.Shader;
import net.atired.thedefused.effect.ModEffects;
import net.atired.thedefused.item.Moditems;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.EntitySpectatorShaderManager;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ModDropsHandler {
        public static boolean oxygen = false;
        public static int health = 0;
        public static int maxhealth = 0;
        public boolean hasToxic = false;
        public static boolean hasToxicity = false;
        public static boolean isCreative = false;
        public int invFrames = 0;
        @SubscribeEvent
        public void PlayerDie(LivingDeathEvent event)
        {
            if(event.getEntity().getType() == EntityType.PLAYER & !event.getEntity().level().isClientSide())
            {
                Player player = (Player)event.getEntity();
                BlockPos pos;
                    pos = player.level().getSharedSpawnPos();
                if(player.getMainHandItem().getItem() == Moditems.WARPEDEFFIGY.get())
                {
                    PlayerReviveTotem(player, event, InteractionHand.MAIN_HAND, pos);
                }
                else if(player.getOffhandItem().getItem() == Moditems.WARPEDEFFIGY.get())
                {
                    PlayerReviveTotem(player, event, InteractionHand.OFF_HAND, pos);
                }
            }
        }
        static void PlayerReviveTotem(Player player, Event event, InteractionHand hand, BlockPos pos)
        {
            event.setCanceled(true);
            player.setHealth(1);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,80,5));
            ItemStack item = player.getItemInHand(hand);
            item.setCount(item.getCount()-1);
            player.setItemInHand(hand,item) ;
            player.resetFallDistance();
            player.setRemainingFireTicks(0);
            player.setTicksFrozen(0);

            if(!player.level().isClientSide()) {

                ServerPlayer nextPlayer = player.getServer().getPlayerList().getPlayer(player.getUUID());
                ServerLevel nextWorld = player.getServer().getLevel(nextPlayer.getRespawnDimension());
                if (nextPlayer.getRespawnPosition() != null) {
                    pos = nextPlayer.getRespawnPosition();

                }
                nextWorld.getChunk(pos);    // make sure the chunk is loaded

                Set<RelativeMovement> relativeMovements = new Set<RelativeMovement>() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean contains(Object o) {
                        return false;
                    }

                    @NotNull
                    @Override
                    public Iterator<RelativeMovement> iterator() {
                        return null;
                    }

                    @NotNull
                    @Override
                    public Object[] toArray() {
                        return new Object[0];
                    }

                    @NotNull
                    @Override
                    public <T> T[] toArray(@NotNull T[] ts) {
                        return null;
                    }

                    @Override
                    public boolean add(RelativeMovement relativeMovement) {
                        return false;
                    }

                    @Override
                    public boolean remove(Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsAll(@NotNull Collection<?> collection) {
                        return false;
                    }

                    @Override
                    public boolean addAll(@NotNull Collection<? extends RelativeMovement> collection) {
                        return false;
                    }

                    @Override
                    public boolean retainAll(@NotNull Collection<?> collection) {
                        return false;
                    }

                    @Override
                    public boolean removeAll(@NotNull Collection<?> collection) {
                        return false;
                    }

                    @Override
                    public void clear() {

                    }
                };

                for (Object a : player.getActiveEffects().toArray())
                {
                    if(!((MobEffectInstance)a).getEffect().isBeneficial())
                    {
                        player.removeEffect(((MobEffectInstance)a).getEffect());
                    }
                 }

                player.teleportTo(nextWorld, pos.getX(), pos.getY(),pos.getZ(),relativeMovements,0,0);
                player.randomTeleport(player.getRandomX(10),player.getRandomY(),player.getRandomZ(10), true);
            }
        }





        @SubscribeEvent
        public void drop(LivingDropsEvent event)
        {

            if(event.getEntity().getTags().contains("defused_thedefused_mod"))
            {
                event.setCanceled(true);

            }
        }
        @SubscribeEvent
        public void itemLeave(EntityLeaveLevelEvent event)
        {
            Level level = event.getEntity().level();

            if(event.getEntity().getType().toString().equals("entity.minecraft.item") & event.getEntity().isRemoved())
            {


                ItemEntity EventEntity = (ItemEntity)event.getEntity();
                ItemStack EventItem = EventEntity.getItem();

                if(EventEntity.getY()<-64& !level.isClientSide() & !EventItem.getItem().toString().equals("air"))
                {

                    Item ItemGotten = EventItem.getItem();
                    if(ItemGotten == Items.COBBLESTONE||ItemGotten == Items.STONE)
                    {
                        ItemGotten = Items.END_STONE;
                    } else if (ItemGotten == Items.TOTEM_OF_UNDYING) {
                        ItemGotten = Moditems.WARPEDEFFIGY.get();
                    }
                    else if (ItemGotten == Items.HONEY_BOTTLE) {
                        ItemGotten = Moditems.PERFUME.get();
                    }
                    else if (ItemGotten == Items.GOLDEN_APPLE) {
                        ItemGotten = Moditems.BEJEVELEDFRUIT.get();
                    }
                    else if (ItemGotten == Items.ENCHANTED_GOLDEN_APPLE) {
                        ItemGotten = Moditems.ENCHANTEDBEJEVELEDFRUIT.get();
                    }



                    ItemEntity eheh = new ItemEntity(event.getLevel(), EventEntity.getX(),-50,EventEntity.getZ(),
                            (new ItemStack(ItemGotten, EventItem.getCount())));
                    eheh.setNoGravity(true);
                    eheh.addDeltaMovement(new Vec3(0,2,0));
                    eheh.moveTo(EventEntity.getX(),-60,EventEntity.getZ(),0,0);
                    level.addFreshEntity(eheh);



                }
            }

        }
        @SubscribeEvent
        public void playerTick(TickEvent.PlayerTickEvent event)
        {
            ModDropsHandler.oxygen = event.player.isInWater();
            ModDropsHandler.health = Math.round(event.player.getHealth()/2);
            ModDropsHandler.isCreative = event.player.isCreative();
            hasToxic = event.player.hasEffect(ModEffects.TOXIC.get());
            ModDropsHandler.hasToxicity = hasToxic;
            ModDropsHandler.maxhealth = Math.round(event.player.getMaxHealth()/2);

        }
        @SubscribeEvent
        public void renderthing(RenderGuiOverlayEvent.Pre event)
        {

            if(event.getOverlay().id().toString().equals("minecraft:player_health") & hasToxic == true)
            {
                event.setCanceled(true);

            }


        }

}
