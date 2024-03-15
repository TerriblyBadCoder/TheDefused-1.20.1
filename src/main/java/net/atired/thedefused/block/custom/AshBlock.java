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
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class AshBlock extends Block {
    public AshBlock(Properties pProperties) {
        super(pProperties);
    }
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide() && (!pPlayer.isCreative()) ) {
            this.onCaughtFire(pState, pLevel, pPos, (Direction)null, (LivingEntity)null);
            System.out.println(pPlayer.getMainHandItem().getEnchantmentTags());
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
            System.out.println("Eh, ha, heh");
            ExplosionDamageCalculator explosionDamageCalculator = new ExplosionDamageCalculator();


            pLevel.explode(null,pLevel.damageSources().lightningBolt(),explosionDamageCalculator,pPos.getCenter().x,pPos.getCenter().y,pPos.getCenter().z,
                    (float)1.1,false, Level.ExplosionInteraction.TNT);
        }
    }
}
