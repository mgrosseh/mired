package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel.Builder;

public class AnalogSRLatchBlockStateGen extends BlockStateGenerator<AnalogSRLatchBlock> {
    public static AnalogSRLatchBlockStateGen instance = new AnalogSRLatchBlockStateGen();

    protected AnalogSRLatchBlockStateGen() {
        super("analog_sr_latch");
    }

    private ModelFile connector;
    private ModelFile tube;

    protected void initModelPart(RegistrateBlockstateProvider prov) {
        connector = block_model(prov, "connector");
        tube = block_model(prov, "tube");
    }

    protected void addModelPart(final Direction facing, final int yRot,
                                final boolean powered, final boolean sidePowered, final boolean powering,
                                MultiPartBlockStateBuilder builder)
    {
        builder.part()
                .modelFile(connector)
                .rotationY(yRot)
                .addModel()
                .condition(AnalogComputatorBlock.FACING, facing)
                .end().part()
                .modelFile(tube)
                .rotationY(yRot)
                .addModel()
                .condition(AnalogComputatorBlock.FACING, facing)
                .end();
    }


    @Override
    public <P extends AnalogSRLatchBlock> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {
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
            final Direction facing = state.getValue(AnalogComputatorBlock.FACING);
            final boolean powered = state.getValue(AnalogComputatorBlock.POWERED);
            final boolean sidePowered = state.getValue(AnalogComputatorBlock.SIDE_POWERED);
            final boolean powering = state.getValue(AnalogComputatorBlock.POWERING);

            final int yRot = ((int) facing.getOpposite().toYRot());

            addModelPart(facing, yRot, powered, sidePowered, powering,
                    builder.part()
                            .modelFile(front)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AnalogComputatorBlock.FACING, facing)
                            .end().part()

                            .modelFile(powered ? backOn : backOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AnalogComputatorBlock.POWERED, powered)
                            .condition(AnalogComputatorBlock.FACING, facing)
                            .end().part()

                            .modelFile(sidePowered ? middleOn : middleOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AnalogComputatorBlock.SIDE_POWERED, sidePowered)
                            .condition(AnalogComputatorBlock.FACING, facing)
                            .end().part()

                            .modelFile(powering ? torchOn : torchOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AnalogComputatorBlock.POWERING, powering)
                            .condition(AnalogComputatorBlock.FACING, facing)
                            .end());
        }
    }
}
