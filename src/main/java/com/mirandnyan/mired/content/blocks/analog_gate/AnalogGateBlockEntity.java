package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.MiredLang;
import com.mirandnyan.mired.MiredTranslations;
import com.mirandnyan.mired.content.blocks.helpers.AnalogScrollValueBehaviour;
import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class AnalogGateBlockEntity extends AbstractRedstoneDiodeBlockEntity implements IHaveGoggleInformation {
    protected ScrollValueBehaviour gateValue;
    protected AnalogGateMode mode;

    public AnalogGateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        mode = AnalogGateMode.GREATER_EQUAL;
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        mode = AnalogGateMode.valueOf(compound.getString("AnalogGateMode"));
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putString("AnalogGateMode", mode.toString());
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        this.gateValue = new AnalogScrollValueBehaviour(
                Component.translatable(MiredTranslations.GATE_VALUE.translationKey),
                this, new AnalogGateValueBoxTransform());
        this.gateValue.between(0, 15);
        this.gateValue.value = 8;
        this.gateValue.withCallback(this::gateValueChanged);
        behaviours.add(this.gateValue);
    }

    @Override
    protected Optional<Integer> calculateOutputSignal() {
        boolean allow = false;
        switch (this.mode) {
            case GREATER_EQUAL -> allow = getBackInputSignal() >= gateValue.value;
            case LESS_EQUAL -> allow = getBackInputSignal() <= gateValue.value;
        }

        if (allow)
            return Optional.of(getBackInputSignal());
        return Optional.of(0);
    }

    private void gateValueChanged(final Integer integer) {
        this.sendData(); // TODO
    }

    public void cycleMode() {
        this.mode = switch (this.mode) {
            case GREATER_EQUAL -> AnalogGateMode.LESS_EQUAL;
            case LESS_EQUAL -> AnalogGateMode.GREATER_EQUAL;
        };
        this.sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        MiredLang.translate(mode.entry).forGoggles(tooltip);
        return true;
    }

    protected enum AnalogGateMode {
        GREATER_EQUAL(MiredTranslations.TOOLTIP_MODE_GREATER_EQUAL),
        LESS_EQUAL(MiredTranslations.TOOLTIP_MODE_LESS_EQUAL);

        private final MiredTranslations.LangEntry entry;

        AnalogGateMode(MiredTranslations.LangEntry entry) {
            this.entry = entry;
        }
    }

    private static class AnalogGateValueBoxTransform extends ValueBoxTransform {
        @Override
        public Vec3 getLocalOffset(final LevelAccessor levelAccessor, final BlockPos blockPos, final BlockState blockState) {
            return new Vec3(0.5, 3.0f / 16.0f, 0.5);
        }

        @Override
        public void rotate(final LevelAccessor levelAccessor, final BlockPos blockPos, final BlockState blockState, final PoseStack poseStack) {
            final float yRot = AngleHelper.horizontalAngle(blockState.getValue(AnalogGateBlock.FACING)) + 180;
            TransformStack.of(poseStack)
                    .rotateYDegrees(yRot)
                    .rotateXDegrees(90);
        }
    }
}
