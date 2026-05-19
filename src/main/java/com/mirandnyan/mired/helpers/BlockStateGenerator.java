package com.mirandnyan.mired.helpers;

import com.mirandnyan.mired.Mired;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public abstract class BlockStateGenerator<T extends Block> {
    protected String model_location;

    protected BlockStateGenerator(String model_location) {
        this.model_location = model_location;
    }

    public abstract <P extends T> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov);
    public <P extends T> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate() {
        return this::generator;
    }

    protected ModelFile sub(final RegistrateBlockstateProvider p, final String suffix) {
        return p.models().getExistingFile(Mired.path("block/" + model_location + "/" + suffix));
    }
}
