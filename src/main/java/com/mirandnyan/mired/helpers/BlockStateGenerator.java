package com.mirandnyan.mired.helpers;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;

public interface BlockStateGenerator<T extends Block> {

    <P extends T> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate();
}
