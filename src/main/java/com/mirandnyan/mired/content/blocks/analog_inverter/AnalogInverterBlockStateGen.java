package com.mirandnyan.mired.content.blocks.analog_inverter;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogInverterBlockStateGen extends BlockStateGenerator<AnalogInverterBlock> {
    public static AnalogInverterBlockStateGen instance = new AnalogInverterBlockStateGen();

    protected AnalogInverterBlockStateGen() {
        super("analog_inverter");
    }

    @Override
    public <P extends AnalogInverterBlock> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {

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
    }
}
