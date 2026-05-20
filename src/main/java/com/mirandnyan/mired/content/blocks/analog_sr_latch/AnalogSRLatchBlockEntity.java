package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.Mired;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlock;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import dev.simulated_team.simulated.content.blocks.redstone.redstone_accumulator.RedstoneAccumulatorBlock;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class AnalogSRLatchBlockEntity extends AbstractBinaryRedstoneDiodeBlockEntity {

    LerpedFloat clientState;

    public AnalogSRLatchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        clientState = LerpedFloat.linear();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        clientState.chase(outputSignal, 0.2f, LerpedFloat.Chaser.EXP);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide)
            clientState.tickChaser();
    }

    @Override
    protected Optional<Integer> calculateOutputSignal(boolean backSignal, boolean sideSignal) {
        if (sideSignal) {
            return Optional.of(backSignal ? this.backInputSignal : 0);
        }
        return Optional.empty();
    }
}