package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlock;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockEntity;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockStateGen;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlock;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlockStateGen;
import com.mirandnyan.mired.content.blocks.cog_block.CogBlock;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlockEntity;
import com.mirandnyan.mired.content.blocks.cog_block.CogBlockStateGen;
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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class MiredBlocks {
    private static final CreateRegistrate REGISTRATE = Mired.getRegistrate();

    public static final BlockEntry<CogBlock> COG_BLOCK = REGISTRATE.block("cog_block", CogBlock::new)
            .initialProperties(() -> Blocks.COPPER_BLOCK)
            .blockstate(CogBlockStateGen.generate())
            .properties(prop -> prop
                    .strength(3.0f, 6.0f)
                    .sound(SoundType.COPPER)
                    .requiresCorrectToolForDrops())
            .item()
            .build()
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
            .build()
            .register();

    public static final BlockEntry<AnalogSRLatchBlock> ANALOG_SR_LATCH_BLOCK = REGISTRATE.block("analog_sr_latch", AnalogSRLatchBlock::new)
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogSRLatchBlockStateGen.instance.generate())
            .blockEntity(AnalogSRLatchBlockEntity::new)
            .build()
            .item()
            .build()
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
