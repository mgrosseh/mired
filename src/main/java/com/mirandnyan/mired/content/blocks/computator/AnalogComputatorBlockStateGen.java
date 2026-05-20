package com.mirandnyan.mired.content.blocks.computator;

import com.mirandnyan.mired.helpers.DiodeBlockStateGenerator;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogComputatorBlockStateGen extends DiodeBlockStateGenerator<AnalogComputatorBlock> {
    public static AnalogComputatorBlockStateGen instance = new AnalogComputatorBlockStateGen();

    protected AnalogComputatorBlockStateGen() {
        super("analog_computator");
    }

    private ModelFile circuit;

    @Override
    protected void initModelPart(RegistrateBlockstateProvider prov) {
        circuit = block_model(prov, "circuit");
    }

    @Override
    protected void addModelPart(Direction facing, int yRot, boolean powered, boolean sidePowered, boolean powering, MultiPartBlockStateBuilder builder) {
        builder.part()
                .modelFile(circuit)
                .rotationY(yRot)
                .addModel()
                .condition(AnalogComputatorBlock.FACING, facing)
                .end();
    }
}
