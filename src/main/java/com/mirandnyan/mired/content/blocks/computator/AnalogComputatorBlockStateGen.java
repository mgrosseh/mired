package com.mirandnyan.mired.content.blocks.computator;

import com.mirandnyan.mired.Mired;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogComputatorBlockStateGen {
    public static ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> rotateHorizontal(final Direction direction, final ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> builder) {
        final int angleOffset = 0;
        builder.rotationY(((int) direction.toYRot() + angleOffset) % 360);
        return builder;
    }

    public static <P extends AnalogComputatorBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate() {
        return (ctx, prov) -> {
            final ModelFile backOff = sub(prov, "block_back_off");
            final ModelFile backOn = sub(prov, "block_back_on");
            final ModelFile front = sub(prov, "block_front");
            final ModelFile middleOff = sub(prov, "block_middle_off");
            final ModelFile middleOn = sub(prov, "block_middle_on");
            final ModelFile torchOff = sub(prov, "torch_off");
            final ModelFile torchOn = sub(prov, "torch_on");

            final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

            for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
                final Direction facing = state.getValue(AnalogComputatorBlock.FACING);
                final boolean powered = state.getValue(AnalogComputatorBlock.POWERED);
                final boolean sidePowered = state.getValue(AnalogComputatorBlock.SIDE_POWERED);
                final boolean powering = state.getValue(AnalogComputatorBlock.POWERING);

                final int yRot = ((int) facing.getOpposite().toYRot());

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
                        .end();
            }
        };
    }



    private static ModelFile sub(final RegistrateBlockstateProvider p, final String suffix) {
        return p.models().getExistingFile(Mired.path("block/analog_computator/" + suffix));
    }
}
