package com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode;

import com.simibubi.create.content.redstone.diodes.AbstractDiodeBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractRedstoneDiodeBlock<T extends AbstractRedstoneDiodeBlockEntity> extends AbstractDiodeBlock implements IBE<T> {
    public static BooleanProperty POWERING = BooleanProperty.create("powering");

    public AbstractRedstoneDiodeBlock(Properties builder) {
        super(builder);
    }


    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        // make sure when rotated all values get updated properly
        InteractionResult result = super.onWrenched(state, context);
        if (result == InteractionResult.SUCCESS) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState newState = level.getBlockState(pos);
            context.getLevel().updateNeighborsAt(pos.relative(newState.getValue(FACING).getOpposite()), this);
        }
        return result;
    }

    @Override
    protected void checkTickOnNeighbor(final @NotNull Level level, final @NotNull BlockPos pos, final @NotNull BlockState state) {
        super.checkTickOnNeighbor(level, pos, state);
        this.refreshInputSignals(state, level, pos);
        level.setBlock(pos, this.getUpdatedBlockstate(pos, state, level), 2);
    }

    @Override
    public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        this.refreshInputSignals(state, level, pos);
    }


    protected void refreshInputSignals(BlockState state, Level level, BlockPos pos) {
        final Direction facing = state.getValue(AbstractRedstoneDiodeBlock.FACING).getOpposite();
        final BlockPos backSide = pos.relative(facing.getOpposite());

        final int backSignal = level.getSignal(backSide, facing.getOpposite());

        final T be = (T) level.getBlockEntity(pos);
        if (be == null)
            return;

        be.setBackInputSignal(backSignal);
    }

    public BlockState getUpdatedBlockstate(final BlockPos pos, final BlockState state, final Level level) {
        final Direction facing = state.getValue(AbstractRedstoneDiodeBlock.FACING).getOpposite();
        final BlockPos offset = pos.relative(facing.getOpposite());

        final boolean backSignal = level.getSignal(offset, facing.getOpposite()) > 0;
        final boolean frontSignal = this.getOutputSignal(level, pos, state) > 0;

        return state
                .setValue(POWERED, backSignal)
                .setValue(POWERING, frontSignal);
    }

    @Override
    public int getSignal(final BlockState state, final BlockGetter level, final BlockPos pos, final Direction dir) {
        return  state.getValue(FACING) == dir ? this.getOutputSignal(level, pos, state) : 0;
    }

    @Override
    public boolean canConnectRedstone(final BlockState state, final BlockGetter level, final BlockPos pos, @Nullable final Direction direction) {
        return direction != null;
    }

    protected int getOutputSignal(final BlockGetter pLevel, final BlockPos pPos, final BlockState pState) {
        final T be = (T) pLevel.getBlockEntity(pPos);
        if (be != null) {
            return be.outputSignal;
        }
        return 0;
    }

    @Override
    public boolean shouldCheckWeakPower(final BlockState state, final SignalGetter level, final BlockPos pos, final Direction side) {
        return false;
    }

    @Override
    protected int getDelay(final BlockState state) {
        return 0;
    }
}
