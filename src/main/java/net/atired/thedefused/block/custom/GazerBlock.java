package net.atired.thedefused.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class GazerBlock extends DirectionalBlock {

    public GazerBlock(Properties pProperties) {
        super(pProperties);
    }
    public static final BooleanProperty POWERED;
@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            if (!pLevel.isClientSide && (Boolean)pState.getValue(POWERED) && pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
                this.updateNeighborsInFront(pLevel, pPos, (BlockState)pState.setValue(POWERED, false));
            }

        }
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite().getOpposite());
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{FACING, POWERED});
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return (BlockState)pState.setValue(FACING, pRot.rotate((Direction)pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
    }
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pState.is(pOldState.getBlock())) {
            if (!pLevel.isClientSide() && (Boolean)pState.getValue(POWERED) && !pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
                BlockState $$5 = (BlockState)pState.setValue(POWERED, false);
                pLevel.setBlock(pPos, $$5, 18);
                this.updateNeighborsInFront(pLevel, pPos, $$5);
            }

        }
    }
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(FACING) == pFacing) {

            this.startSignal(pLevel, pCurrentPos);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    private void startSignal(LevelAccessor pLevel, BlockPos pPos) {
        if (!pLevel.isClientSide() && !pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
            pLevel.scheduleTick(pPos, this, 2);
        }
    }
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if ((Boolean)pState.getValue(POWERED)) {
            pLevel.setBlock(pPos, (BlockState)pState.setValue(POWERED, false), 2);
        } else {
            pLevel.setBlock(pPos, (BlockState)pState.setValue(POWERED, true), 2);

        }

        this.updateNeighborsInFront(pLevel, pPos, pState);
    }
    protected void updateNeighborsInFront(Level pLevel, BlockPos pPos, BlockState pState) {
        Direction $$3 = (Direction)pState.getValue(FACING);
        BlockPos $$4 = pPos.relative($$3.getOpposite());
        pLevel.neighborChanged($$4, this, pPos);
        pLevel.updateNeighborsAtExceptFromFacing($$4, this, $$3);
    }
    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }
    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getSignal(pBlockAccess, pPos, pSide);
    }
    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return (Boolean)pBlockState.getValue(POWERED) && pBlockState.getValue(FACING) == pSide ? 15 : 0;
    }
    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
