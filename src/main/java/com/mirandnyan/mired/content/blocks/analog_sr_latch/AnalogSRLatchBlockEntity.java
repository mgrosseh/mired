package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.Mired;
import com.mirandnyan.mired.MiredLang;
import com.mirandnyan.mired.MiredTranslations;
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

    boolean risingEdgeOnlyMode;

    public AnalogSRLatchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        clientState = LerpedFloat.linear();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putInt("ChangeTimer", lastChange);
        compound.putBoolean("RisingEdgeOnlyMode", risingEdgeOnlyMode);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        lastChange = compound.getInt("ChangeTimer");
        risingEdgeOnlyMode = compound.getBoolean("RisingEdgeOnlyMode");
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
    protected Optional<Integer> calculateOutputSignal() {
        boolean update = getSideInputSignal() > 0 && (!risingEdgeOnlyMode || getPreviousSideInputSignal() == 0);

        if (update)
            return Optional.of(getBackInputSignal());
        return Optional.empty();
    }

    @Override
    public void setOutputSignal(final int output) {
        boolean update = output != this.outputSignal;
        this.outputSignal = Mth.clamp(output, 0, 15);
        if (!update) return;

        if (risingEdgeOnlyMode) this.updateFacingBlock(this.getBlockState().getBlock(), this.level);
        else lastChange = 1; // TODO maybe implement this differently in the future

        this.sendData();
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

    public void toggleRisingEdgeOnlyMode() {
        risingEdgeOnlyMode = !risingEdgeOnlyMode;
        sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.analogStrength", this.outputSignal).forGoggles(tooltip);
        MiredLang.translate(risingEdgeOnlyMode ?
                MiredTranslations.TOOLTIP_RISING_EDGE_ONLY_ON :
                MiredTranslations.TOOLTIP_RISING_EDGE_ONLY_OFF).forGoggles(tooltip);

        return true;
    }
}