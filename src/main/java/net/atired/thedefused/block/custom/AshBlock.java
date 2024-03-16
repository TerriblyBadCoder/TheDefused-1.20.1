package net.atired.thedefused.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;

public class AshBlock extends Block {
    public AshBlock(Properties pProperties) {
        super(pProperties);
    }
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {

        if ((!pPlayer.isCreative() && pPlayer.getMainHandItem().getEnchantmentLevel(Enchantments.SILK_TOUCH) == 0) ) {
            this.onCaughtFire(pState, pLevel, pPos, (Direction)null, (LivingEntity)null);

        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos);
    }
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {

        explode(pLevel,pPos);
        super.stepOn(pLevel, pPos, pState, pEntity);
    }


    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide) {
            explode(pLevel,pPos);
        }
        super.wasExploded(pLevel, pPos, pExplosion);
    }

    public static void explode(Level pLevel, BlockPos pPos)
    {
        if(!pLevel.isClientSide())
        {

            ExplosionDamageCalculator explosionDamageCalculator = new ExplosionDamageCalculator();


            pLevel.explode(null,pLevel.damageSources().lightningBolt(),explosionDamageCalculator,pPos.getCenter().x,pPos.getCenter().y,pPos.getCenter().z,
                    (float)1.2,false, Level.ExplosionInteraction.TNT);
        }
    }
    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (pLevel.hasNeighborSignal(pPos)) {
            this.onCaughtFire(pState, pLevel, pPos, (Direction)null, (LivingEntity)null);
            pLevel.removeBlock(pPos, false);
        }

    }
    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pHit.getBlockPos();
            Entity entity = pProjectile.getOwner();
            if (pProjectile.isOnFire() && pProjectile.mayInteract(pLevel, blockpos)) {
                this.onCaughtFire(pState, pLevel, blockpos, (Direction)null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                pLevel.removeBlock(blockpos, false);
            }
        }

    }
}
