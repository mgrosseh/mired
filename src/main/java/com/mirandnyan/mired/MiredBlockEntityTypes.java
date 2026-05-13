package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockEntity;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.mirandnyan.mired.Mired.MOD_ID;

public class MiredBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final Supplier<BlockEntityType<AnalogSRLatchBlockEntity>> ANALOG_SR_LATCH = BLOCK_ENTITY_TYPES.register("analog_sr_latch_block_entity",
            () -> BlockEntityType.Builder.of(AnalogSRLatchBlockEntity::new, MiredBlocks.ANALOG_SR_LATCH_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<AnalogInverterBlockEntity>> ANALOG_INVERTER = BLOCK_ENTITY_TYPES.register("analog_inverter_block_entity",
            () -> BlockEntityType.Builder.of(AnalogInverterBlockEntity::new, MiredBlocks.ANALOG_INVERTER_BLOCK.get()).build(null));

    // Load this class
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
