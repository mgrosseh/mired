package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class AnalogGateBlockEntity extends AbstractRedstoneDiodeBlockEntity {
    public AnalogGateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    @Override
    protected Optional<Integer> calculateOutputSignal() {
        return Optional.empty();
    }
}
