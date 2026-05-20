package com.mirandnyan.mired.content.blocks.brass_encased_redstone;

import com.mirandnyan.mired.Mired;
import com.mirandnyan.mired.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class BrassEncasedRedstoneBlockStateGen extends BlockStateGenerator<BrassEncasedRedstoneBlock> {
    public static BrassEncasedRedstoneBlockStateGen instance = new BrassEncasedRedstoneBlockStateGen();

    protected BrassEncasedRedstoneBlockStateGen() {
        super("brass_encased_redstone");
    }

    @Override
    public <P extends BrassEncasedRedstoneBlock> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {
        final ModelFile off = block_model(prov, "block");
        final ModelFile on = block_model(prov, "block_on");

        final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

        for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
            final int power = state.getValue(BrassEncasedRedstoneBlock.POWER);


            builder.part()
                    .modelFile(power == 0 ? off : on)
                    .addModel()
                    .condition(BrassEncasedRedstoneBlock.POWER, power)
                    .end();
        }

    }
}
