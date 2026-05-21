package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.content.blocks.helpers.AbstractRedstoneDiodeBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AnalogGateBlock extends AbstractRedstoneDiodeBlock<AnalogGateBlockEntity> {
    public AnalogGateBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return null;
    }

    @Override
    public Class<AnalogGateBlockEntity> getBlockEntityClass() {
        return null;
    }

    @Override
    public BlockEntityType<? extends AnalogGateBlockEntity> getBlockEntityType() {
        return null;
    }
}
