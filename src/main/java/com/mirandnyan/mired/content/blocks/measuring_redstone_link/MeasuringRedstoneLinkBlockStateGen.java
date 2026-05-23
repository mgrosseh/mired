package com.mirandnyan.mired.content.blocks.measuring_redstone_link;

import com.simibubi.create.content.redstone.link.RedstoneLinkBlock;
import com.simibubi.create.content.redstone.link.RedstoneLinkGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;

public class MeasuringRedstoneLinkBlockStateGen extends RedstoneLinkGenerator {

    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        String variant = state.getValue(MeasuringRedstoneLinkBlock.RECEIVER) ? "receiver" : "transmitter";
        if (state.getValue(MeasuringRedstoneLinkBlock.FACING).getAxis().isHorizontal())
            variant += "_vertical";
        if (state.getValue(MeasuringRedstoneLinkBlock.POWERED))
            variant += "_powered";

        return prov.models().getExistingFile(prov.modLoc("block/measuring_redstone_link/" + variant));
    }
}
