package com.mirandnyan.mired.content.blocks.analog_gate;

import com.mirandnyan.mired.content.blocks.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

public class AnalogGateBlockStateGen extends BlockStateGenerator<AnalogGateBlock> {
    public static AnalogGateBlockStateGen instance = new AnalogGateBlockStateGen();

    protected AnalogGateBlockStateGen() {
        super("analog_gate");
    }

    @Override
    public void generator(DataGenContext ctx, RegistrateBlockstateProvider prov) {

    }
}
