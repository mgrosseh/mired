package com.mirandnyan.mired.helpers;

import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;

public class DiodeBlockStateGen {
    public static <T extends Block, P extends T> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate() {
        return (ctx, prov) -> {

        };
    }
}
