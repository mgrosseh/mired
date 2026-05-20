package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.helpers.BlockStateGenerator;
import com.mirandnyan.mired.helpers.DiodeBlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel.Builder;

public class AnalogSRLatchBlockStateGen extends DiodeBlockStateGenerator<AnalogSRLatchBlock> {
    public static AnalogSRLatchBlockStateGen instance = new AnalogSRLatchBlockStateGen();

    protected AnalogSRLatchBlockStateGen() {
        super("analog_sr_latch");
    }

    private ModelFile tube;

    protected void initModelPart(RegistrateBlockstateProvider prov) {
        tube = block_model(prov, "tube");
    }

    protected void addModelPart(final Direction facing, final int yRot,
                                final boolean powered, final boolean sidePowered, final boolean powering,
                                MultiPartBlockStateBuilder builder)
    {
        builder.part()
                .modelFile(tube)
                .rotationY(yRot)
                .addModel()
                .condition(AnalogComputatorBlock.FACING, facing)
                .end();
    }
}
