package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.content.blocks.helpers.UnaryDiodeBlockStateGenerator;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogGateBlockStateGen extends UnaryDiodeBlockStateGenerator<AnalogGateBlock> {
    public static AnalogGateBlockStateGen instance = new AnalogGateBlockStateGen();

    protected AnalogGateBlockStateGen() {
        super("analog_gate");
    }

    private ModelFile middle;

    @Override
    protected void initModelPart(RegistrateBlockstateProvider prov) {
        middle = block_model(prov, "block_middle");
    }

    @Override
    protected void addModelPart(Direction facing, int yRot, boolean powered, boolean powering, MultiPartBlockStateBuilder builder) {
        builder.part()
                .modelFile(middle)
                .rotationY(yRot)
                .addModel()
                .condition(AnalogComputatorBlock.FACING, facing)
                .end();
    }
}
