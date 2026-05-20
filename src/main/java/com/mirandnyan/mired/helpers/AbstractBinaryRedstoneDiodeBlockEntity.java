package com.mirandnyan.mired.helpers;

import com.mirandnyan.mired.Mired;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import dev.simulated_team.simulated.content.blocks.redstone.redstone_accumulator.RedstoneAccumulatorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public abstract class AbstractBinaryRedstoneDiodeBlockEntity extends SmartBlockEntity {
    protected int outputSignal;
    protected int backInputSignal;
    protected int leftInputSignal;
    protected int rightInputSignal;

    public AbstractBinaryRedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }

    public int getBackInputSignal() {
        return this.backInputSignal;
    }
    public int getLeftInputSignal() {
        return this.leftInputSignal;
    }
    public int getRightInputSignal() {
        return this.rightInputSignal;
    }
    public int getSideInputSignal() {
        return Math.max(this.rightInputSignal, this.leftInputSignal);
    }

    public void setBackInputSignal(int signal) {
        this.backInputSignal = signal;
    }
    public void setLeftInputSignal(int signal) {
        this.leftInputSignal = signal;
    }
    public void setRightInputSignal(int signal) {
        this.rightInputSignal = signal;
    }

    @Override
    protected void write(final CompoundTag tag, final HolderLookup.Provider registries, final boolean clientPacket) {
        tag.putInt("OutputSignal", this.outputSignal);
        tag.putInt("BackInputSignal", this.backInputSignal);
        tag.putInt("LeftInputSignal", this.leftInputSignal);
        tag.putInt("RightInputSignal", this.rightInputSignal);
        super.write(tag, registries, clientPacket);
    }
    @Override
    protected void read(final CompoundTag tag, final HolderLookup.Provider registries, final boolean clientPacket) {
        this.outputSignal = tag.getInt("OutputSignal");
        this.backInputSignal = tag.getInt("BackInputSignal");
        this.leftInputSignal = tag.getInt("LeftInputSignal");
        this.rightInputSignal = tag.getInt("RightInputSignal");
        super.read(tag, registries, clientPacket);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.level == null) return;

        final boolean backSignal = this.getBlockState().getValue(AbstractBinaryRedstoneDiodeBlock.POWERED);
        final boolean sideSignal = this.getBlockState().getValue(AbstractBinaryRedstoneDiodeBlock.SIDE_POWERED);

        if(!backSignal && !sideSignal) return;

        int x;
        if ((x = calculateOutputSignal(backSignal, sideSignal).orElse(-1)) != -1)
            setOutputSignal(x);
    }

    public void setOutputSignal(final int output) {
        boolean update = output != this.outputSignal;
        int old = this.outputSignal;
        this.outputSignal = Mth.clamp(output, 0, 15);
        if (update) {
            this.updateFacingBlock(this.getBlockState().getBlock(), this.level);
        }
    }

    private void updateFacingBlock(Block block, Level level) {
        level.updateNeighborsAt(this.worldPosition, block);
        level.updateNeighborsAt(this.worldPosition.relative(this.getBlockState().getValue(AbstractBinaryRedstoneDiodeBlock.FACING).getOpposite()), block);
    }

    protected abstract Optional<Integer> calculateOutputSignal(boolean backSignal, boolean sideSignal);
}
