package com.mirandnyan.mired;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.mirandnyan.mired.Mired.MOD_ID;

public class MiredCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
        CREATIVE_MODE_TABS.register("mired_tab",
                () -> CreativeModeTab.builder()
                        .title(MiredTranslations.CREATIVE_MODE_TAB.resolveComponent())
                        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                        .icon(() -> MiredBlocks.getItem(MiredBlocks.ANALOG_SR_LATCH_BLOCK).asStack())
                        .displayItems((parameters, output) -> {
                            output.accept(MiredBlocks.getItem(MiredBlocks.ANDESITE_ENCASED_REDSTONE));
                            output.accept(MiredBlocks.getItem(MiredBlocks.COPPER_ENCASED_REDSTONE));
                            output.accept(MiredBlocks.getItem(MiredBlocks.BRASS_ENCASED_REDSTONE));
                            output.accept(MiredBlocks.getItem(MiredBlocks.ANALOG_INVERTER_BLOCK));
                            output.accept(MiredBlocks.getItem(MiredBlocks.ANALOG_SR_LATCH_BLOCK));
                            output.accept(MiredBlocks.getItem(MiredBlocks.COMPUTATOR));
                            output.accept(MiredBlocks.getItem(MiredBlocks.ANALOG_GATE_BLOCK));
                            output.accept(MiredBlocks.getItem(MiredBlocks.MEASURING_REDSTONE_LINK));
                            output.accept(MiredItems.COMPUTATION_CIRCUIT);
                            output.accept(MiredItems.DIODE_BASE);
                        }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
