package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlock;
import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlockEntity;
import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlockStateGen;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlock;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockEntity;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockStateGen;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.*;
import com.mirandnyan.mired.content.blocks.brass_encased_redstone.BrassEncasedRedstoneBlock;
import com.mirandnyan.mired.content.blocks.brass_encased_redstone.BrassEncasedRedstoneBlockStateGen;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlockEntity;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class MiredBlocks {
    private static final CreateRegistrate REGISTRATE = Mired.getRegistrate();

    public static final BlockEntry<BrassEncasedRedstoneBlock> BRASS_ENCASED_REDSTONE = REGISTRATE.block("brass_encased_redstone", BrassEncasedRedstoneBlock::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .blockstate(BrassEncasedRedstoneBlockStateGen.instance.generate())
            .properties(prop -> prop
                    .strength(3.0f, 6.0f)
                    .sound(SoundType.COPPER)
                    .requiresCorrectToolForDrops())
            .item()
//            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
//                    .pattern("A")
//                    .pattern("B")
//                    .pattern("")
//                    .define('A', AllBlocks.BRASS_CASING.asItem())
//                    .define('B', Items.REDSTONE)
//            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<AnalogComputatorBlock> COMPUTATOR = REGISTRATE.block("analog_computator", AnalogComputatorBlock::new)
            .initialProperties(() -> Blocks.REPEATER)
            .blockstate(AnalogComputatorBlockStateGen.instance.generate())
            .blockEntity(AnalogComputatorBlockEntity::new)
            .build()
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<AnalogInverterBlock> ANALOG_INVERTER_BLOCK = REGISTRATE.block("analog_inverter", AnalogInverterBlock::new)
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogInverterBlockStateGen.instance.generate())
            .blockEntity(AnalogInverterBlockEntity::new)
            .build()
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<AnalogSRLatchBlock> ANALOG_SR_LATCH_BLOCK = REGISTRATE.block("analog_sr_latch", AnalogSRLatchBlock::new)
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogSRLatchBlockStateGen.instance.generate())
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntityEntry<AnalogSRLatchBlockEntity> ANALOG_SR_LATCH_BLOCK_ENTITY = REGISTRATE
            .blockEntity("analog_sr_latch", AnalogSRLatchBlockEntity::new)
            .visual(() -> AnalogSRLatchVisual::new, false)
            .validBlock(ANALOG_SR_LATCH_BLOCK)
            .renderer(() -> AnalogSRLatchRenderer::new)
            .register();

    public static final BlockEntry<AnalogGateBlock> ANALOG_GATE_BLOCK = REGISTRATE.block("analog_gate", AnalogGateBlock::new)
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogGateBlockStateGen.instance.generate())
            .blockEntity(AnalogGateBlockEntity::new)
            .build()
            .item()
            .transform(customItemModel())
            .register();




    public static <T extends BlockEntity> BlockEntityEntry<T> getBlockEntity(BlockEntry<?> entry) {
        return BlockEntityEntry.cast(entry.getSibling(Registries.BLOCK_ENTITY_TYPE));
    }
    public static ItemEntry<? extends Item> getItem(BlockEntry<?> entry) {
        return ItemEntry.cast(entry.getSibling(Registries.ITEM));
    }

    public static void register() {
        // load class
    }
}
