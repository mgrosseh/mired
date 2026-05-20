package com.mirandnyan.mired.content.blocks.computator;

import com.mirandnyan.mired.helpers.BlockStateGenerator;
import com.mirandnyan.mired.helpers.DiodeBlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class AnalogComputatorBlockStateGen extends DiodeBlockStateGenerator<AnalogComputatorBlock> {
    public static AnalogComputatorBlockStateGen instance = new AnalogComputatorBlockStateGen();

    protected AnalogComputatorBlockStateGen() {
        super("analog_computator");
    }

    @Override
    protected void initModelPart(RegistrateBlockstateProvider prov) {

    }

    @Override
    protected void addModelPart(Direction facing, int yRot, boolean powered, boolean sidePowered, boolean powering, MultiPartBlockStateBuilder builder) {

    }
}
