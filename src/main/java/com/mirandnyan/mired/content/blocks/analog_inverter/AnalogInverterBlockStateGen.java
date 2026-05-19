package com.mirandnyan.mired.content.blocks.analog_inverter;

import com.mirandnyan.mired.Mired;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogInverterBlockStateGen {
    public static ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> rotateHorizontal(final Direction direction, final ConfiguredModel.Builder<MultiPartBlockStateBuilder.PartBuilder> builder) {
        final int angleOffset = 0;
        builder.rotationY(((int) direction.toYRot() + angleOffset) % 360);
        return builder;
    }

    public static <P extends AnalogInverterBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate() {
        return (ctx, prov) -> {
            final ModelFile off = sub(prov, "block_off");
            final ModelFile on = sub(prov, "block_on");

            final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

            for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
                final Direction facing = state.getValue(AnalogComputatorBlock.FACING);
                final boolean powered = state.getValue(AnalogInverterBlock.POWERED);

                final int yRot = ((int) facing.getOpposite().toYRot());

                builder.part()
                        .modelFile(powered ? on : off)
                        .rotationY(yRot)
                        .addModel()
                        .condition(AnalogInverterBlock.POWERED, powered)
                        .condition(AnalogInverterBlock.FACING, facing)
                        .end();
            }
        };
    }

    private static ModelFile sub(final RegistrateBlockstateProvider p, final String suffix) {
        return p.models().getExistingFile(Mired.path("block/analog_computator/" + suffix));
    }
}
