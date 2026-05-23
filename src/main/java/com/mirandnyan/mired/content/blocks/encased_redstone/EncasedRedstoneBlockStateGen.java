package com.mirandnyan.mired.content.blocks.encased_redstone;

import com.mirandnyan.mired.content.blocks.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class EncasedRedstoneBlockStateGen<T extends Block> extends BlockStateGenerator<T> {
    public static EncasedRedstoneBlockStateGen<EncasedRedstoneBlock> basic =
            new EncasedRedstoneBlockStateGen<>(false);
    public static EncasedRedstoneBlockStateGen<BrassEncasedRedstoneBlock> wrenchable =
            new EncasedRedstoneBlockStateGen<>(true);

    protected boolean allowDirections;

    protected EncasedRedstoneBlockStateGen(boolean allowDirections) {
        super(""); // model_location unused
        this.allowDirections = allowDirections;
    }

    @Override
    public <P extends T> void generator(DataGenContext<Block, P> ctx, RegistrateBlockstateProvider prov) {
        final ModelFile block = block_model(prov, ctx.getName(), "block");
        final ModelFile off = block_model(prov, "encased_redstone", "connector");
        final ModelFile on = block_model(prov, "encased_redstone", "connector_on");

        final MultiPartBlockStateBuilder builder = prov.getMultipartBuilder(ctx.get());

        builder.part()
                .modelFile(block)
                .addModel()
                .end();

        for (final BlockState state : ctx.get().getStateDefinition().getPossibleStates()) {
            final int power = state.getValue(EncasedRedstoneBlock.POWER);

            builder.part()
                    .modelFile(power == 0 ? off : on)
                    .addModel()
                    .condition(EncasedRedstoneBlock.POWER, power)
                    .end();

            // add main
            // for dir: if allowDir || prop_true => show part
        }

        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
            Direction dir = e.getKey();
            builder.part().modelFile(off).rotationX(dirToRotX(dir)).rotationY(dirToRotY(dir)).uvLock(true).addModel()
                    .condition(e.getValue(), true);
        });

    }

    protected static int dirToRotX(Direction dir) {
        return switch (dir) {
            case DOWN -> 180;
            case UP -> 0;
            case NORTH -> 90;
            case SOUTH -> 90;
            case WEST -> 90;
            case EAST -> 90;
        };
    }

    protected static int dirToRotY(Direction dir) {
        return switch (dir) {
            case DOWN -> 0;
            case UP -> 0;
            case NORTH -> 270;
            case SOUTH -> 90;
            case WEST -> 180;
            case EAST -> 0;
        };
    }

}
