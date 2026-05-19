package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockStateGen;
import com.mirandnyan.mired.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogSRLatchBlockStateGen extends BlockStateGenerator<AnalogSRLatchBlock> {
    public static AnalogSRLatchBlockStateGen instance = new AnalogSRLatchBlockStateGen();

    protected AnalogSRLatchBlockStateGen() {
        super("analog_sr_latch");
    }

    @Override
    public <P extends AnalogSRLatchBlock> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {
        final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

        for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {

        }
    }
}
