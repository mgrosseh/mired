package com.mirandnyan.mired.content.blocks.analog_inverter;

import com.mirandnyan.mired.MiredBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.ticks.TickPriority;

public class AnalogInverterBlock extends DiodeBlock implements EntityBlock {

    public static final MapCodec<AnalogInverterBlock> CODEC = simpleCodec(AnalogInverterBlock::new);

    @Override
    public MapCodec<AnalogInverterBlock> codec() {
        return CODEC;
    }

    public AnalogInverterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false))
        );
    }

    @Override
    protected int getDelay(BlockState state) {
        return 2;
    }

    @Override
    public BlockState updateShape(
            BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos
    ) {
        return direction == Direction.DOWN && !this.canSurviveOn(level, neighborPos, neighborState)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected int getOutputSignal(BlockGetter level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        return blockentity instanceof AnalogInverterBlockEntity ? ((AnalogInverterBlockEntity) blockentity).getOutputSignal() : 0;
    }

    private int calculateOutputSignal(Level level, BlockPos pos, BlockState state) {
        int i = this.getInputSignal(level, pos, state);
        return 15 - i;
    }

    @Override
    protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
        int i = this.getInputSignal(level, pos, state);
        return i != 15;
    }

    @Override
    protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
        int i = super.getInputSignal(level, pos, state);
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction);
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.hasAnalogOutputSignal()) {
            i = blockstate.getAnalogOutputSignal(level, blockpos);
        }

        return i;
    }

    /**
     * Check the output signal of this diode and schedule a new block tick if it should change.
     */
    @Override
    protected void checkTickOnNeighbor(Level level, BlockPos pos, BlockState state) {
        if (!level.getBlockTicks().willTickThisTick(pos, this)) {
            int i = this.calculateOutputSignal(level, pos, state);
            BlockEntity blockentity = level.getBlockEntity(pos);
            int j = blockentity instanceof AnalogInverterBlockEntity ? ((AnalogInverterBlockEntity)blockentity).getOutputSignal() : 0;
            if (i != j || state.getValue(POWERED) != this.shouldTurnOn(level, pos, state)) {
                TickPriority tickpriority = this.shouldPrioritize(level, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
                level.scheduleTick(pos, this, 2, tickpriority);
            }
        }
    }

    private void refreshOutputState(Level level, BlockPos pos, BlockState state) {
        int i = this.calculateOutputSignal(level, pos, state);
        BlockEntity blockentity = level.getBlockEntity(pos);
        int j = 0;
        if (blockentity instanceof AnalogInverterBlockEntity be) {
            j = be.getOutputSignal();
            be.setOutputSignal(i);
        }

        if (j != i) {
            boolean flag1 = this.shouldTurnOn(level, pos, state);
            boolean flag = state.getValue(POWERED);
            if (flag && !flag1) {
                level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(false)), 2);
            } else if (!flag && flag1) {
                level.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(true)), 2);
            }

            this.updateNeighborsInFront(level, pos, state);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.refreshOutputState(level, pos, state);
    }

    /**
     * Called on server when {@link Level#blockEvent} is called. If server returns true, then also called on the client. On the Server, this may perform additional changes to the world, like pistons replacing the block with an extended base. On the client, the update may involve replacing block entities or effects such as sounds or particles
     */
    @Override
    protected boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity blockentity = level.getBlockEntity(pos);
        return blockentity != null && blockentity.triggerEvent(id, param);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MiredBlocks.getBlockEntity(MiredBlocks.ANALOG_INVERTER_BLOCK).create(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public boolean getWeakChanges(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos) {
        return state.is(MiredBlocks.ANALOG_SR_LATCH_BLOCK);
    }

    @Override
    public void onNeighborChange(BlockState state, net.minecraft.world.level.LevelReader world, BlockPos pos, BlockPos neighbor) {
        if (pos.getY() == neighbor.getY() && world instanceof Level && !(world).isClientSide()) {
            state.handleNeighborChanged((Level)world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
        }
    }
}
