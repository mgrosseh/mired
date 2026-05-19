package com.mirandnyan.mired.helpers;

import com.simibubi.create.content.redstone.diodes.AbstractDiodeBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.simulated_team.simulated.multiloader.CommonRedstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBinaryRedstoneDiodeBlock<T extends AbstractBinaryRedstoneDiodeBlockEntity> extends AbstractDiodeBlock implements IBE<T>, CommonRedstoneBlock {
    public static BooleanProperty POWERING = BooleanProperty.create("powering");
    public static BooleanProperty SIDE_POWERED = BooleanProperty.create("side_powered");

    public AbstractBinaryRedstoneDiodeBlock(Properties builder) {
        super(builder);
    }

    @Override
    protected void checkTickOnNeighbor(final Level level, final BlockPos pos, final BlockState state) {
        super.checkTickOnNeighbor(level, pos, state);
        level.setBlock(pos, this.getUpdatedBlockstate(pos, state, level), 2);
    }

    @Override
    public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {}

    public BlockState getUpdatedBlockstate(final BlockPos pos, final BlockState state, final Level level) {
        final Direction facing = state.getValue(AbstractBinaryRedstoneDiodeBlock.FACING).getOpposite();
        final BlockPos offset = pos.relative(facing.getOpposite());

        final BlockPos leftSide = pos.offset(facing.getCounterClockWise().getNormal());
        final BlockPos rightSide = pos.offset(facing.getClockWise().getNormal());

        final boolean leftSignal = level.getSignal(leftSide, facing.getClockWise().getOpposite()) > 0;
        final boolean rightSignal = level.getSignal(rightSide, facing.getCounterClockWise().getOpposite()) > 0;

        final boolean backSignal = level.getSignal(offset, facing.getOpposite()) > 0;
        final boolean sideSignal = leftSignal || rightSignal;
        final boolean frontSignal = this.getOutputSignal(level, pos, state) > 0;

        return state
                .setValue(SIDE_POWERED, sideSignal)
                .setValue(POWERED, backSignal)
                .setValue(POWERING, frontSignal);
    }

    @Override
    public int getSignal(final BlockState state, final BlockGetter level, final BlockPos pos, final Direction dir) {
        return  state.getValue(FACING) == dir ? this.getOutputSignal(level, pos, state) : 0;
    }

    @Override
    public boolean commonConnectRedstone(final BlockState state, final BlockGetter level, final BlockPos pos, @Nullable final Direction direction) {
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
    protected int getDelay(final BlockState state) {
        return 0;
    }
}
