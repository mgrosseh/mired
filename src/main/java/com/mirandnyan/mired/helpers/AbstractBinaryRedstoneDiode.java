package com.mirandnyan.mired.helpers;

import com.simibubi.create.content.redstone.diodes.AbstractDiodeBlock;
import com.simibubi.create.foundation.block.IBE;
import dev.simulated_team.simulated.multiloader.CommonRedstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class AbstractBinaryRedstoneDiode<T> extends AbstractDiodeBlock implements IBE<T>, CommonRedstoneBlock {
    public static BooleanProperty POWERING = BooleanProperty.create("powering");
    public static BooleanProperty SIDE_POWERED = BooleanProperty.create("side_powered");

    public AbstractBinaryRedstoneDiode(Properties builder) {
        super(builder);
    }

    @Override
    protected void checkTickOnNeighbor(final Level level, final BlockPos pos, final BlockState state) {
        super.checkTickOnNeighbor(level, pos, state);
        level.setBlock(pos, this.getUpdatedBlockstate(pos, state, level), 2);
    }

    @Override
    public void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {}
}
