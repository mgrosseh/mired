package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlock;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class AnalogSRLatchBlockEntity extends AbstractBinaryRedstoneDiodeBlockEntity {

    public AnalogSRLatchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

}