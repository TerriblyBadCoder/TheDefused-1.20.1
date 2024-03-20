package net.atired.thedefused.block.custom;
import java.util.Optional;
import java.util.function.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.atired.thedefused.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Supplier;

public class ShaleBlock extends Block {

    private final Block ColdShale;

    public ShaleBlock(Block pBlock, Properties pProperties) {
        super(pProperties);
        this.ColdShale = pBlock;

    }
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.scanForWater(pLevel, pPos)) {
            pLevel.setBlock(pPos, this.ColdShale.defaultBlockState(), 2);
        }

    }
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (this.scanForWater(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 10 + pLevel.getRandom().nextInt(5));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    protected boolean scanForWater(BlockGetter pLevel, BlockPos pPos) {
        BlockState state = pLevel.getBlockState(pPos);
        Direction[] var4 = Direction.values();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Direction direction = var4[var6];
            FluidState fluidstate = pLevel.getFluidState(pPos.relative(direction));
            if (state.canBeHydrated(pLevel, pPos, fluidstate, pPos.relative(direction))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        System.out.println("eheheh");
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
