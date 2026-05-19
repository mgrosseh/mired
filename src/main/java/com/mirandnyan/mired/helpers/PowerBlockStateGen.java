package com.mirandnyan.mired.helpers;

import com.mirandnyan.mired.Mired;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.function.Function;

public class PowerBlockStateGen {
    public static <T extends Block, P extends T> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate(Function<Integer, ModelFile> func) {
        return (ctx, prov) -> {
//                prov.getVariantBuilder(ctx.getEntry()).forAllStates((state) -> ConfiguredModel.builder()
//                        .modelFile(off)
//                        .build());


        };
    }
}
