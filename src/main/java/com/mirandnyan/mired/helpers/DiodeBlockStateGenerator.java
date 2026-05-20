package com.mirandnyan.mired.helpers;

import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlock;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public abstract class DiodeBlockStateGenerator<T extends AbstractBinaryRedstoneDiodeBlock<?>> extends BlockStateGenerator<T> {
    protected DiodeBlockStateGenerator(String model_location) {
        super(model_location);
    }

    protected abstract void initModelPart(RegistrateBlockstateProvider prov);

    protected abstract void addModelPart(final Direction facing, final int yRot,
                                         final boolean powered, final boolean sidePowered, final boolean powering,
                                         MultiPartBlockStateBuilder builder);

    @Override
    public <P extends T> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {
        final ModelFile backOff = block_model(prov, "diode", "block_back_off");
        final ModelFile backOn = block_model(prov, "diode", "block_back_on");
        final ModelFile front = block_model(prov, "diode", "block_front");
        final ModelFile middleOff = block_model(prov, "diode", "block_middle_off");
        final ModelFile middleOn = block_model(prov, "diode", "block_middle_on");
        final ModelFile torchOff = block_model(prov, "diode", "torch_off");
        final ModelFile torchOn = block_model(prov, "diode", "torch_on");

        initModelPart(prov);

        final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

        for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
            final Direction facing = state.getValue(AbstractBinaryRedstoneDiodeBlock.FACING);
            final boolean powered = state.getValue(AbstractBinaryRedstoneDiodeBlock.POWERED);
            final boolean sidePowered = state.getValue(AbstractBinaryRedstoneDiodeBlock.SIDE_POWERED);
            final boolean powering = state.getValue(AbstractBinaryRedstoneDiodeBlock.POWERING);

            final int yRot = ((int) facing.getOpposite().toYRot());

            addModelPart(facing, yRot, powered, sidePowered, powering,
                    builder.part()
                            .modelFile(front)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractBinaryRedstoneDiodeBlock.FACING, facing)
                            .end().part()

                            .modelFile(powered ? backOn : backOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractBinaryRedstoneDiodeBlock.POWERED, powered)
                            .condition(AbstractBinaryRedstoneDiodeBlock.FACING, facing)
                            .end().part()

                            .modelFile(sidePowered ? middleOn : middleOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractBinaryRedstoneDiodeBlock.SIDE_POWERED, sidePowered)
                            .condition(AbstractBinaryRedstoneDiodeBlock.FACING, facing)
                            .end().part()

                            .modelFile(powering ? torchOn : torchOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractBinaryRedstoneDiodeBlock.POWERING, powering)
                            .condition(AbstractBinaryRedstoneDiodeBlock.FACING, facing)
                            .end());
        }
    }

}
