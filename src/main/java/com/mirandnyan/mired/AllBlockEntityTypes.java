package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlockEntity;
import com.mirandnyan.mired.registrate.MiredRegistrate;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
    private static final MiredRegistrate REGISTRATE = Mired.getRegistrate();


    public static final BlockEntityEntry<AnalogSRLatchBlockEntity> ANALOG_SR_LATCH = REGISTRATE
            .blockEntity("analog_sr_latch", AnalogSRLatchBlockEntity::new)
            .validBlock(AllBlocks.ANALOG_SR_LATCH)
            .renderer(() -> SmartBlockEntityRenderer::new) // TODO: renderer
            .register();

    // Load this class
    public static void register() {}
}
