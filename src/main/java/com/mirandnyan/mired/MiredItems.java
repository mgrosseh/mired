package com.mirandnyan.mired;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static com.mirandnyan.mired.Mired.MOD_ID;

public class MiredItems {

    public static final DeferredRegister.Items ITEMS =  DeferredRegister.createItems(MOD_ID);
    public static final List<DeferredItem<? extends Item>> ALL_ITEMS = new ArrayList<>();


    public static <T extends Block> DeferredBlock<T> addBlockItem(String name, DeferredBlock<T> block) {
        DeferredItem<BlockItem> item = MiredItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()/*.component(AeroDataComponents.LEVITATING, Levitating.LEVITITE)*/));
        MiredItems.ALL_ITEMS.add(item);
        return block;
    }

    // Load this class

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
