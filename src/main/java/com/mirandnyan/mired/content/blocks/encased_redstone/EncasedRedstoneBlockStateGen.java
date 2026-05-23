package com.mirandnyan.mired.content.blocks.encased_redstone;

import com.mirandnyan.mired.content.blocks.helpers.BlockStateGenerator;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

public class EncasedRedstoneBlockStateGen<T extends Block> extends BlockStateGenerator<T> {
    public static EncasedRedstoneBlockStateGen<EncasedRedstoneBlock> basic =
            new EncasedRedstoneBlockStateGen<>(false);
    public static EncasedRedstoneBlockStateGen<WrenchableEncasedRedstoneBlock> wrenchable =
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

        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
            Direction dir = e.getKey();
            var part = rotate(builder.part().modelFile(off), dir).uvLock(true).addModel();
            if (this.allowDirections) part.condition(e.getValue(), true);
            part.condition(EncasedRedstoneBlock.POWER, 0);
            part.end();
            var part2 = rotate(builder.part().modelFile(on), dir).uvLock(true).addModel();
            if (this.allowDirections) part2.condition(e.getValue(), true);
            part2.condition(EncasedRedstoneBlock.POWER, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
            part2.end();
        });
    }
}
