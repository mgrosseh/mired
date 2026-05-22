package com.mirandnyan.mired.content.blocks.helpers;

import com.mirandnyan.mired.content.blocks.helpers.abstract_redstone_diode.AbstractRedstoneDiodeBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public abstract class UnaryDiodeBlockStateGenerator<T extends AbstractRedstoneDiodeBlock<?>> extends BlockStateGenerator<T> {
    protected UnaryDiodeBlockStateGenerator(String model_location) {
        super(model_location);
    }

    protected abstract void initModelPart(RegistrateBlockstateProvider prov);

    protected abstract void addModelPart(final Direction facing, final int yRot,
                                         final boolean powered, final boolean powering,
                                         MultiPartBlockStateBuilder builder);

    @Override
    public <P extends T> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {
        final ModelFile backOff = block_model(prov, "diode", "block_back_off");
        final ModelFile backOn = block_model(prov, "diode", "block_back_on");
        final ModelFile front = block_model(prov, "diode", "block_front");
        final ModelFile torchOff = block_model(prov, "diode", "torch_off");
        final ModelFile torchOn = block_model(prov, "diode", "torch_on");

        initModelPart(prov);

        final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

        for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
            final Direction facing = state.getValue(AbstractRedstoneDiodeBlock.FACING);
            final boolean powered = state.getValue(AbstractRedstoneDiodeBlock.POWERED);
            final boolean powering = state.getValue(AbstractRedstoneDiodeBlock.POWERING);

            final int yRot = ((int) facing.getOpposite().toYRot());

            addModelPart(facing, yRot, powered, powering,
                    builder.part()
                            .modelFile(front)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractRedstoneDiodeBlock.FACING, facing)
                            .end().part()

                            .modelFile(powered ? backOn : backOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractRedstoneDiodeBlock.POWERED, powered)
                            .condition(AbstractRedstoneDiodeBlock.FACING, facing)
                            .end().part()

                            .modelFile(powering ? torchOn : torchOff)
                            .rotationY(yRot)
                            .addModel()
                            .condition(AbstractRedstoneDiodeBlock.POWERING, powering)
                            .condition(AbstractRedstoneDiodeBlock.FACING, facing)
                            .end());
        }
    }

}
