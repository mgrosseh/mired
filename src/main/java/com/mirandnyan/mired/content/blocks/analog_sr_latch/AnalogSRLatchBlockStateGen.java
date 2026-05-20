package com.mirandnyan.mired.content.blocks.analog_sr_latch;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.helpers.DiodeBlockStateGenerator;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogSRLatchBlockStateGen extends DiodeBlockStateGenerator<AnalogSRLatchBlock> {
    public static AnalogSRLatchBlockStateGen instance = new AnalogSRLatchBlockStateGen();

    protected AnalogSRLatchBlockStateGen() {
        super("analog_sr_latch");
    }

    private ModelFile lever_base;

    protected void initModelPart(RegistrateBlockstateProvider prov) {
        lever_base = block_model(prov, "analog_lever_base");
    }

    protected void addModelPart(final Direction facing, final int yRot,
                                final boolean powered, final boolean sidePowered, final boolean powering,
                                MultiPartBlockStateBuilder builder)
    {
        builder.part()
                .modelFile(lever_base)
                .rotationY(yRot)
                .addModel()
                .condition(AnalogComputatorBlock.FACING, facing)
                .end();
    }
}
