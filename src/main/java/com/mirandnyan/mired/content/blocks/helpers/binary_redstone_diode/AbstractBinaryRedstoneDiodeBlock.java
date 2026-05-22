package com.mirandnyan.mired.content.blocks.helpers.binary_redstone_diode;

import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.simulated_team.simulated.multiloader.CommonRedstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class AbstractBinaryRedstoneDiodeBlock<T extends AbstractBinaryRedstoneDiodeBlockEntity> extends AbstractRedstoneDiodeBlock<T> implements IBE<T> {
    public static BooleanProperty SIDE_POWERED = BooleanProperty.create("side_powered");

    public AbstractBinaryRedstoneDiodeBlock(Properties builder) {
        super(builder);
    }

    protected void refreshInputSignals(BlockState state, Level level, BlockPos pos) {
        final Direction facing = state.getValue(AbstractBinaryRedstoneDiodeBlock.FACING).getOpposite();
        final BlockPos backSide = pos.relative(facing.getOpposite());
        final BlockPos leftSide = pos.offset(facing.getCounterClockWise().getNormal());
        final BlockPos rightSide = pos.offset(facing.getClockWise().getNormal());

        final int leftSignal = level.getSignal(leftSide, facing.getClockWise().getOpposite());
        final int rightSignal = level.getSignal(rightSide, facing.getCounterClockWise().getOpposite());
        final int backSignal = level.getSignal(backSide, facing.getOpposite());

        final T be = (T) level.getBlockEntity(pos);
        if (be == null)
            return;

        be.setBackInputSignal(backSignal);
        be.setLeftInputSignal(leftSignal);
        be.setRightInputSignal(rightSignal);
    }

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
}
