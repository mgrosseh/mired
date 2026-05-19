package com.mirandnyan.mired.helpers;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractBinaryRedstoneDiodeBlockEntity extends SmartBlockEntity {
    protected int outputSignal;

    public AbstractBinaryRedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }

    public void setOutputSignal(int output) {
        this.outputSignal = output;
    }
}
