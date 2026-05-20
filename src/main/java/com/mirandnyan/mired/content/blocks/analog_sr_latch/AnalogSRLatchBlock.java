package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.MiredBlocks;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlock;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.redstone.diodes.AbstractDiodeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.ticks.TickPriority;

public class AnalogSRLatchBlock extends AbstractBinaryRedstoneDiodeBlock<AnalogSRLatchBlockEntity> {
    public static final MapCodec<AnalogSRLatchBlock> CODEC = simpleCodec(AnalogSRLatchBlock::new);

    // TODO: make togglable only update on rising edge

    public AnalogSRLatchBlock(final Properties builder) {
        super(builder);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(POWERING, false)
                .setValue(SIDE_POWERED, false)
        );
    }

    @Override
    public MapCodec<AnalogSRLatchBlock> codec() {
        return CODEC;
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
    public Class<AnalogSRLatchBlockEntity> getBlockEntityClass() {
        return AnalogSRLatchBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AnalogSRLatchBlockEntity> getBlockEntityType() {
        return MiredBlocks.<AnalogSRLatchBlockEntity>getBlockEntity(MiredBlocks.ANALOG_SR_LATCH_BLOCK).get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, SIDE_POWERED, POWERING);
    }
}
