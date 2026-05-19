package com.mirandnyan.mired;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.mirandnyan.mired.Mired.MOD_ID;

public class MiredCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
        CREATIVE_MODE_TABS.register("mired_tab",
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.mired"))
                        .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                        .icon(() -> MiredBlocks.getItem(MiredBlocks.COG_BLOCK).asStack())
                        .displayItems((parameters, output) -> {
                            output.accept(MiredBlocks.getItem(MiredBlocks.COG_BLOCK));
                            output.accept(MiredBlocks.getItem(MiredBlocks.ANALOG_INVERTER_BLOCK));
                            output.accept(MiredBlocks.getItem(MiredBlocks.ANALOG_SR_LATCH_BLOCK));
                            output.accept(MiredBlocks.getItem(MiredBlocks.COMPUTATOR));
                        }).build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
