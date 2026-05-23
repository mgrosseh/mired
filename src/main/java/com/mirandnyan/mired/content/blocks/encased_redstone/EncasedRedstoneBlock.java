package com.mirandnyan.mired.content.blocks.encased_redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;

public class EncasedRedstoneBlock extends Block {

    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public EncasedRedstoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = super.getStateForPlacement(context);

       return state.setValue(POWER, getBestNeighborSignal(state, context.getLevel(), context.getClickedPos()));
    }
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(state, level, pos, neighborBlock, fromPos, moving);
        updateSignal(state, level, pos);
    }

    protected void updateSignal(BlockState state, Level level, BlockPos pos) {
        int pow = getBestNeighborSignal(state, level, pos);
        level.setBlock(pos, state.setValue(POWER, Mth.clamp(pow, 0, 15)), UPDATE_ALL | UPDATE_INVISIBLE);
    }

    protected int getBestNeighborSignal(BlockState state, Level level, BlockPos pos) {
        return level.getBestNeighborSignal(pos);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return Math.max(0, blockState.getValue(POWER) - 1);
    }
}
