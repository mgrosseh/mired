package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.MiredBlocks;
import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class AnalogGateBlock extends AbstractRedstoneDiodeBlock<AnalogGateBlockEntity> {
    public static final MapCodec<AnalogGateBlock> CODEC = simpleCodec(AnalogGateBlock::new);

    public AnalogGateBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(POWERING, false)
        );
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }

    @Override
    public Class<AnalogGateBlockEntity> getBlockEntityClass() {
        return AnalogGateBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AnalogGateBlockEntity> getBlockEntityType() {
        return MiredBlocks.<AnalogGateBlockEntity>getBlockEntity(MiredBlocks.ANALOG_GATE_BLOCK).get();
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, POWERING);
    }
}
