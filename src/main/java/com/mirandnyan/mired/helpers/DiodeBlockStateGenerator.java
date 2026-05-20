package com.mirandnyan.mired.helpers;

import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public abstract class DiodeBlockStateGenerator<T extends AbstractBinaryRedstoneDiodeBlock<?>> extends BlockStateGenerator<T> {
    protected DiodeBlockStateGenerator(String model_location) {
        super(model_location);
    }

    protected abstract void initModelPart(RegistrateBlockstateProvider prov);

    protected abstract void addModelPart(final Direction facing, final int yRot,
                                         final boolean powered, final boolean sidePowered, final boolean powering,
                                         MultiPartBlockStateBuilder builder);


}
