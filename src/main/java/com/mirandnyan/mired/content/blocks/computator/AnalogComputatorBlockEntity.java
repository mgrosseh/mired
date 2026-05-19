package com.mirandnyan.mired.content.blocks.computator;

import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.redstone.diodes.BrassDiodeScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.gui.AllIcons;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import dev.simulated_team.simulated.content.blocks.redstone.redstone_accumulator.RedstoneAccumulatorBlock;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.IntStream;

public class AnalogComputatorBlockEntity extends AbstractBinaryRedstoneDiodeBlockEntity {
    protected ScrollOptionBehaviour<ComputationMode> computationMode;

    public AnalogComputatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void initialize() {
        super.initialize();
        //this.updateSignal();
    }

    @Override
    public void addBehaviours(final List<BlockEntityBehaviour> behaviours) {
        this.computationMode = new ScrollOptionBehaviour<>(ComputationMode.class,
                Component.translatable("block.mired.analog_computator.computation_mode"),
                this,
                new AnalogComputatorValueBoxTransform());

        behaviours.add(this.computationMode);

        //computationMode.onlyActiveWhen(this::predicate); // can disable conditionally

        //computationMode.withCallback(setting -> {
        //    // can perform action when changed
        //});
    }

    public enum ComputationMode implements INamedIconOptions {
        ADDITION(AllIcons.I_ADD),
        SUBTRACTION(AllIcons.I_ADD),
        MULTIPLICATION(AllIcons.I_ADD),
        DIVISION(AllIcons.I_ADD),
        ;

        private final String translationKey;
        private final AllIcons icon;

        ComputationMode(AllIcons icon) {
            this.icon = icon;
            this.translationKey = "mired.analog_computator.computation_mode." + Lang.asId(name());
        }

        @Override
        public AllIcons getIcon() {
            return icon;
        }

        @Override
        public String getTranslationKey() {
            return translationKey;
        }
    }

    private static class AnalogComputatorValueBoxTransform extends ValueBoxTransform {
        @Override
        public Vec3 getLocalOffset(final LevelAccessor levelAccessor, final BlockPos blockPos, final BlockState blockState) {
            return new Vec3(0.5, 6.6f / 16.0f, 0.5);
        }

        @Override
        public void rotate(final LevelAccessor levelAccessor, final BlockPos blockPos, final BlockState blockState, final PoseStack poseStack) {
            final float yRot = AngleHelper.horizontalAngle(blockState.getValue(RedstoneAccumulatorBlock.FACING)) + 180;
            TransformStack.of(poseStack)
                    .rotateYDegrees(yRot)
                    .rotateXDegrees(90);
        }
    }
}
