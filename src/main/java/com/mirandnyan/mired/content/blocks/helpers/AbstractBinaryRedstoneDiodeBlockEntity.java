package com.mirandnyan.mired.content.blocks.helpers;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public abstract class AbstractBinaryRedstoneDiodeBlockEntity extends AbstractRedstoneDiodeBlockEntity {
    protected int leftInputSignal;
    protected int rightInputSignal;

    protected int previousLeftSignal;
    protected int previousRightSignal;

    public AbstractBinaryRedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getSideInputSignal() {
        return Math.max(this.rightInputSignal, this.leftInputSignal);
    }

    public int getPreviousSideInputSignal() {
        return Math.max(this.previousRightSignal, this.previousLeftSignal);
    }

    public void setLeftInputSignal(int signal) {
        this.leftInputSignal = signal;
    }
    public void setRightInputSignal(int signal) {
        this.rightInputSignal = signal;
    }

    @Override
    protected void write(final CompoundTag tag, final HolderLookup.Provider registries, final boolean clientPacket) {
        tag.putInt("LeftInputSignal", this.leftInputSignal);
        tag.putInt("RightInputSignal", this.rightInputSignal);
        tag.putInt("PrevLeftInputSignal", this.previousLeftSignal);
        tag.putInt("PrevRightInputSignal", this.previousRightSignal);
        super.write(tag, registries, clientPacket);
    }
    @Override
    protected void read(final CompoundTag tag, final HolderLookup.Provider registries, final boolean clientPacket) {
        this.leftInputSignal = tag.getInt("LeftInputSignal");
        this.rightInputSignal = tag.getInt("RightInputSignal");
        this.previousLeftSignal = tag.getInt("PrevLeftInputSignal");
        this.previousRightSignal = tag.getInt("PrevRightInputSignal");
        super.read(tag, registries, clientPacket);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.level == null) return;

        previousLeftSignal = leftInputSignal;
        previousRightSignal = rightInputSignal;
    }
}
