package com.mirandnyan.mired.helpers;

import com.mirandnyan.mired.Mired;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public abstract class BlockStateGenerator<T extends Block> {
    protected String model_location;

    protected BlockStateGenerator(String model_location) {
        this.model_location = model_location;
    }

    public abstract <P extends T> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov);
    public <P extends T> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate() {
        return this::generator;
    }

    protected ModelFile block_model(final RegistrateBlockstateProvider p, final String suffix) {
        return block_model(p, model_location, suffix);
    }
    protected ModelFile block_model(final RegistrateBlockstateProvider p, final String infix, final String suffix) {
        return p.models().getExistingFile(Mired.path("block/" + infix + "/" + suffix));
    }
}
