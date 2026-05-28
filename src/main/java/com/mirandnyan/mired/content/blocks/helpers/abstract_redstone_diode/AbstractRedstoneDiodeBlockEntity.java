package com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode;

import com.mirandnyan.mired.content.blocks.helpers.binary_redstone_diode.AbstractBinaryRedstoneDiodeBlock;
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

public abstract class AbstractRedstoneDiodeBlockEntity extends SmartBlockEntity {
    protected int outputSignal;
    protected int backInputSignal;

    protected int previousBackSignal;

    public AbstractRedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }

    public int getBackInputSignal() {
        return this.backInputSignal;
    }

    public void setBackInputSignal(int signal) {
        this.backInputSignal = signal;
    }

    @Override
    protected void write(final CompoundTag tag, final HolderLookup.Provider registries, final boolean clientPacket) {
        tag.putInt("OutputSignal", this.outputSignal);
        tag.putInt("BackInputSignal", this.backInputSignal);
        tag.putInt("PrevBackInputSignal", this.previousBackSignal);
        super.write(tag, registries, clientPacket);
    }
    @Override
    protected void read(final CompoundTag tag, final HolderLookup.Provider registries, final boolean clientPacket) {
        this.outputSignal = tag.getInt("OutputSignal");
        this.backInputSignal = tag.getInt("BackInputSignal");
        this.previousBackSignal = tag.getInt("PrevBackInputSignal");
        super.read(tag, registries, clientPacket);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.level == null) return;

        calculateOutputSignal().ifPresent(this::setOutputSignal);

        previousBackSignal = backInputSignal;
    }

    public void setOutputSignal(final int output) {
        boolean update = output != this.outputSignal;
        this.outputSignal = Mth.clamp(output, 0, 15);
        if (update) {
            assert this.level != null;
            this.updateFacingBlock(this.getBlockState().getBlock(), this.level);
            this.sendData();
        }
    }

    protected void updateFacingBlock(Block block, Level level) {
        level.updateNeighborsAt(this.worldPosition, block);
        level.updateNeighborsAt(this.worldPosition.relative(this.getBlockState().getValue(AbstractBinaryRedstoneDiodeBlock.FACING).getOpposite()), block);
    }

    protected abstract Optional<Integer> calculateOutputSignal();
}
