package net.atired.thedefused.block.custom;

import net.atired.thedefused.item.Moditems;
import net.atired.thedefused.particle.ModParticles;
import net.atired.thedefused.particle.custom.VolatileParticle;
import net.atired.thedefused.particletypes.AnotherDustParticleOptions;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
public class AshBlock extends Block {
    public static final IntegerProperty CHARGE;
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
        explode(world, pos, this.getCharge(state));
    }
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        explode(pLevel,pPos,this.getCharge(pState));
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
            if (!level.isClientSide & explosion.getPosition() != pos.getCenter()) {
                explode(level,pos,this.getCharge(state));

            }
        super.onBlockExploded(state, level, pos, explosion);
    }
    public void explode(Level pLevel, BlockPos pPos, int i)
    {
        if(!pLevel.isClientSide())
        {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(),1);
            ExplosionDamageCalculator explosionDamageCalculator = new ExplosionDamageCalculator();
            pLevel.explode(null,pLevel.damageSources().lightningBolt(),explosionDamageCalculator,pPos.getCenter().x,pPos.getCenter().y,pPos.getCenter().z,
                    1.25F + ((float)i)/10,false, Level.ExplosionInteraction.TNT);
            Vec3 center = pPos.getCenter();
            if (pLevel instanceof ServerLevel serverLevel) {
                for(int j = 0; j < i/3+1; j++)
                    serverLevel.sendParticles(ModParticles.VOLATILE_PARTICLES.get(),center.x+randumb(i),center.y+randumb(i),center.z+randumb(i),1,0,0,0,0);
            }

        }
    }
    protected float randumb(int inty)
    {
        float floaty = inty/4.0F*(float)Math.random()-inty/8.0F;
        return floaty;
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
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(this.getCharge(pState) > 0)
        {

            for(int i = 0; i <this.getCharge(pState)/2+10; i++)

                pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, pPos.getX() + Math.random()*1.2, pPos.getY() + Math.random()*1.2, pPos.getZ() + Math.random()*1.2, 0.0, 0.0, 0.0);
        }

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pHand == InteractionHand.OFF_HAND)
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        if(pPlayer.getMainHandItem().getItem() == Moditems.CHARGEDASH.get())
        {

            Charge(pPlayer,pPos,InteractionHand.MAIN_HAND,pState,pLevel);
        }
        else if(pPlayer.getOffhandItem().getItem() == Moditems.CHARGEDASH.get())
        {
            Charge(pPlayer,pPos,InteractionHand.OFF_HAND, pState,pLevel);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
    public BlockState getStateForCharge(int pCharge) {
        return (BlockState)this.defaultBlockState().setValue(this.getChargeProperty(), pCharge);
    }
    public void Charge(Player pPlayer, BlockPos pPos, InteractionHand hand,BlockState state,Level level)
    {
        if(this.getCharge(state) < 20)
        {
            pPlayer.swing(hand);
            if(!pPlayer.isCreative())
                pPlayer.getItemInHand(hand).setCount(pPlayer.getItemInHand(hand).getCount()-1);

                level.setBlock(pPos, this.getStateForCharge(this.getCharge(state) + 1), 2);
        }

    }
    public int getCharge(BlockState pState) {
        return (Integer)pState.getValue(this.getChargeProperty());
    }
    protected IntegerProperty getChargeProperty() {
        return CHARGE;
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(new Property[]{CHARGE});
    }
    static {
        CHARGE = IntegerProperty.create("charge",0,21);
    }
}
