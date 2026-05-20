package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.Mired;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlock;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlockEntity;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import dev.simulated_team.simulated.content.blocks.redstone.redstone_accumulator.RedstoneAccumulatorBlock;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class AnalogSRLatchBlockEntity extends AbstractBinaryRedstoneDiodeBlockEntity implements IHaveGoggleInformation {

    int lastChange;
    LerpedFloat clientState;

    public AnalogSRLatchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        clientState = LerpedFloat.linear();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putInt("ChangeTimer", lastChange);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        lastChange = compound.getInt("ChangeTimer");
        super.read(compound, registries, clientPacket);
        clientState.chase(outputSignal, 0.2f, LerpedFloat.Chaser.EXP);
    }

    @Override
    public void tick() {
        super.tick();
        if (lastChange > 0) {
            lastChange--;
            if (lastChange == 0)
                updateFacingBlock(getBlockState().getBlock(), level);
        }

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

    public void changeOutputSignal(boolean back) {
        // TODO: documentation: called for interaction results to mimic behaviour of analog lever, use setOutputSignal in most cases
        int prev = this.outputSignal;
        this.outputSignal += back ? -1 : 1;
        this.outputSignal = Mth.clamp(this.outputSignal, 0, 15);
        if (prev != this.outputSignal)
            lastChange = 15;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.analogStrength", this.outputSignal).forGoggles(tooltip);

        return true;
    }
}