package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.MiredBlocks;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlock;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.redstone.diodes.AbstractDiodeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import org.joml.Vector3f;

public class AnalogSRLatchBlock extends AbstractBinaryRedstoneDiodeBlock<AnalogSRLatchBlockEntity> {
    public static final MapCodec<AnalogSRLatchBlock> CODEC = simpleCodec(AnalogSRLatchBlock::new);

    // TODO: make togglable only update on rising edge

    public AnalogSRLatchBlock(final Properties builder) {
        super(builder);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(POWERING, false)
                .setValue(SIDE_POWERED, false)
        );
    }

    @Override
    public MapCodec<AnalogSRLatchBlock> codec() {
        return CODEC;
    }


    @Override
    public BlockState updateShape(
            BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos
    ) {
        return direction == Direction.DOWN && !this.canSurviveOn(level, neighborPos, neighborState)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public Class<AnalogSRLatchBlockEntity> getBlockEntityClass() {
        return AnalogSRLatchBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AnalogSRLatchBlockEntity> getBlockEntityType() {
        return MiredBlocks.<AnalogSRLatchBlockEntity>getBlockEntity(MiredBlocks.ANALOG_SR_LATCH_BLOCK).get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, SIDE_POWERED, POWERING);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            addParticles(state, worldIn, pos, 1.0F);
            return InteractionResult.SUCCESS;
        }

        return onBlockEntityUse(worldIn, pos, be -> {
            boolean sneak = player.isShiftKeyDown();
            be.changeOutputSignal(sneak);
            float f = .25f + ((be.getOutputSignal() + 5) / 15f) * .5f;
            worldIn.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.2F, f);
            return InteractionResult.SUCCESS;
        });
    }


    private static void addParticles(BlockState state, LevelAccessor worldIn, BlockPos pos, float alpha) {
        Direction direction = state.getValue(FACING)
                .getOpposite();
        Direction direction1 = Direction.UP;
        double d0 =
                (double) pos.getX() + 0.5D + 0.1D * (double) direction.getStepX() + 0.2D * (double) direction1.getStepX();
        double d1 =
                (double) pos.getY() + 0.5D + 0.1D * (double) direction.getStepY() + 0.2D * (double) direction1.getStepY();
        double d2 =
                (double) pos.getZ() + 0.5D + 0.1D * (double) direction.getStepZ() + 0.2D * (double) direction1.getStepZ();
        worldIn.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), alpha), d0, d1, d2, 0.0D, 0.0D,
                0.0D);
    }
}
