package com.mirandnyan.mired.content.blocks.cog_block;

import com.mirandnyan.mired.Mired;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class CogBlockStateGen {
    public static <P extends CogBlock> NonNullBiConsumer<DataGenContext<Block, P>, RegistrateBlockstateProvider> generate() {
        return (ctx, prov) -> {
            final ModelFile off = prov.models().getExistingFile(Mired.path("block/cog_block/block_off"));
            final ModelFile on = prov.models().getExistingFile(Mired.path("block/cog_block/block_on"));
            final ModelFile semi_on = prov.models().getExistingFile(Mired.path("block/cog_block/block_semi_on"));

            final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

            for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
                final int power = state.getValue(CogBlock.POWER);


                builder.part()
                        .modelFile(power == 0 ? off : power == 1 ? semi_on : on)
                        .addModel()
                        .condition(CogBlock.POWER, power)
                        .end();
            }
        };
    }
}
