package com.mirandnyan.mired.content.blocks.encased_redstone;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Map;

public class BrassEncasedRedstoneBlock extends EncasedRedstoneBlock implements IWrenchable {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

    public BrassEncasedRedstoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWER, 0)
                .setValue(NORTH, true)
                .setValue(EAST, true)
                .setValue(SOUTH, true)
                .setValue(WEST, true)
                .setValue(UP, true)
                .setValue(DOWN, true)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
        builder.add(UP);
        builder.add(DOWN);
    }

    @Override
    protected int getBestNeighborSignal(BlockState state, Level level, BlockPos pos) {
        int i = 0;

        for(Direction direction : Direction.values()) {
            if (!state.getValue(PROPERTY_BY_DIRECTION.get(direction)))
                continue;
            int j = level.getSignal(pos.relative(direction), direction);
            if (j >= 15)
                return 15;

            if (j > i)
                i = j;
        }

        return i;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @org.jetbrains.annotations.Nullable Direction direction) {
        return ((direction == null) || state.getValue(PROPERTY_BY_DIRECTION.get(direction.getOpposite()))) && super.canConnectRedstone(state, level, pos, direction);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (state.getValue(PROPERTY_BY_DIRECTION.get(side.getOpposite())))
            return Math.max(0, state.getValue(POWER) - 1);
        return 0;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        var face = context.getClickedFace();
        var property = PROPERTY_BY_DIRECTION.get(face);
        context.getLevel().setBlock(context.getClickedPos(), state.cycle(property), UPDATE_ALL | UPDATE_INVISIBLE);
        return InteractionResult.SUCCESS;
    }
}
