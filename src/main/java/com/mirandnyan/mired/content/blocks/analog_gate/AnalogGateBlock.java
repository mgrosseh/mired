package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.MiredBlocks;
import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlock;
import com.mojang.serialization.MapCodec;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class AnalogGateBlock extends AbstractRedstoneDiodeBlock<AnalogGateBlockEntity> implements IWrenchable {
    public static final MapCodec<AnalogGateBlock> CODEC = simpleCodec(AnalogGateBlock::new);

    public AnalogGateBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(POWERING, false)
        );
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }

    @Override
    public Class<AnalogGateBlockEntity> getBlockEntityClass() {
        return AnalogGateBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AnalogGateBlockEntity> getBlockEntityType() {
        return MiredBlocks.<AnalogGateBlockEntity>getBlockEntity(MiredBlocks.ANALOG_GATE_BLOCK).get();
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, POWERING);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        return onBlockEntityUse(worldIn, pos, be -> {
            be.cycleMode();
            worldIn.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.2F, .30f);
            return InteractionResult.SUCCESS;
        });
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(final ItemStack heldItem, final BlockState blockState, final Level level, final BlockPos blockPos, final Player player, final InteractionHand interactionHand, final BlockHitResult blockHitResult) {
        if (AllItems.WRENCH.isIn(heldItem)) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
