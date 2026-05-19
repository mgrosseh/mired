package com.mirandnyan.mired.content.blocks.computator;

import com.mirandnyan.mired.MiredBlocks;
import com.mirandnyan.mired.helpers.AbstractBinaryRedstoneDiodeBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

public class AnalogComputatorBlock extends AbstractBinaryRedstoneDiodeBlock<AnalogComputatorBlockEntity> {
    public static final MapCodec<AnalogComputatorBlock> CODEC = simpleCodec(AnalogComputatorBlock::new);
//    public static BooleanProperty POWERING = BooleanProperty.create("powering");
//    public static BooleanProperty SIDE_POWERED = BooleanProperty.create("side_powered");

    public AnalogComputatorBlock(final Properties builder) {
        super(builder);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(POWERED, false)
                .setValue(POWERING, false)
                .setValue(SIDE_POWERED, false));
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, SIDE_POWERED, POWERING);
    }

    @Override
    public Class<AnalogComputatorBlockEntity> getBlockEntityClass() {
        return AnalogComputatorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends AnalogComputatorBlockEntity> getBlockEntityType() {
        return MiredBlocks.<AnalogComputatorBlockEntity>getBlockEntity(MiredBlocks.COMPUTATOR).get();
    }

    @Override
    public void animateTick(final BlockState pState, final Level pLevel, final BlockPos pPos, final RandomSource pRandom) {
        this.withBlockEntityDo(pLevel, pPos, be -> {
            if(pState.getValue(POWERED) || be.getOutputSignal() > 0)
                if(pRandom.nextFloat() < 0.25f)
                    addParticles(pState, pLevel, pPos, 1f);
        } );
    }

    private static void addParticles(final BlockState state, final LevelAccessor level, final BlockPos pos, final float alpha) {
        level.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), alpha), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0.0D, 0.0D,
                0.0D);
    }

    @Override
    public VoxelShape getShape(final BlockState pState, final BlockGetter pLevel, final BlockPos pPos, final CollisionContext pContext) {
        return Shapes.or(
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(4, 2, 4, 12, 7, 12)
        );
    }
}
