package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_sr_latch.AnalogSRLatchBlock;
import com.mirandnyan.mired.content.blocks.CogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.mirandnyan.mired.MiredItems.addBlockItem;
import static com.mirandnyan.mired.Mired.MOD_ID;

public class MiredBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static final List<DeferredBlock<? extends Block>> ALL_BLOCKS = new ArrayList<>();

    public static final DeferredBlock<CogBlock> COG_BLOCK = addWithProps(CogBlock::new, "cog_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
                    .strength(3.0f, 6.0f)
                    .sound(SoundType.COPPER)
                    .requiresCorrectToolForDrops());

    public static final DeferredBlock<AnalogSRLatchBlock> ANALOG_SR_LATCH_BLOCK = addWithProps(AnalogSRLatchBlock::new, "analog_sr_latch",
            BlockBehaviour.Properties.ofFullCopy(Blocks.COMPARATOR));


    private static <T extends Block> DeferredBlock<T> addWithProps(Function<BlockBehaviour.Properties, T> constructor, String name, BlockBehaviour.Properties properties) {
        return addBlockItem(name, BLOCKS.register(name, () -> constructor.apply(properties)));
    }

    // Load this class
    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
