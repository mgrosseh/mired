package com.mirandnyan.mired;

import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlock;
import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlockEntity;
import com.mirandnyan.mired.content.blocks.analog_gate.AnalogGateBlockStateGen;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlock;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockEntity;
import com.mirandnyan.mired.content.blocks.analog_inverter.AnalogInverterBlockStateGen;
import com.mirandnyan.mired.content.blocks.analog_sr_latch.*;
import com.mirandnyan.mired.content.blocks.encased_redstone.EncasedRedstoneBlock;
import com.mirandnyan.mired.content.blocks.encased_redstone.WrenchableEncasedRedstoneBlock;
import com.mirandnyan.mired.content.blocks.encased_redstone.EncasedRedstoneBlockStateGen;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlock;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlockEntity;
import com.mirandnyan.mired.content.blocks.computator.AnalogComputatorBlockStateGen;
import com.mirandnyan.mired.content.blocks.measuring_redstone_link.MeasuringRedstoneLinkBlock;
import com.mirandnyan.mired.content.blocks.measuring_redstone_link.MeasuringRedstoneLinkBlockEntity;
import com.mirandnyan.mired.content.blocks.measuring_redstone_link.MeasuringRedstoneLinkBlockStateGen;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.ItemUseOverrides;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;

import static com.simibubi.create.foundation.data.CreateRegistrate.casingConnectivity;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class MiredBlocks {
    private static final CreateRegistrate REGISTRATE = Mired.getRegistrate();

    public static final BlockEntry<WrenchableEncasedRedstoneBlock> BRASS_ENCASED_REDSTONE = REGISTRATE.block("brass_encased_redstone", WrenchableEncasedRedstoneBlock::new)
            .initialProperties(AllBlocks.BRASS_CASING)
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.BRASS_CASING)))
            .onRegister(casingConnectivity((block, cc) ->
                    cc.makeCasing(block, AllSpriteShifts.BRASS_CASING)))
            .blockstate(EncasedRedstoneBlockStateGen.wrenchable.generate())
            .properties(prop -> prop
                    .strength(3.0f, 6.0f)
                    .sound(SoundType.COPPER)
                    .requiresCorrectToolForDrops())
            .item()
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllBlocks.BRASS_CASING.asItem())
                    .requires(Items.REDSTONE)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllBlocks.BRASS_CASING.asItem()))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<WrenchableEncasedRedstoneBlock> COPPER_ENCASED_REDSTONE = REGISTRATE.block("copper_encased_redstone", WrenchableEncasedRedstoneBlock::new)
            .initialProperties(AllBlocks.COPPER_CASING)
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.COPPER_CASING)))
            .onRegister(casingConnectivity((block, cc) ->
                    cc.makeCasing(block, AllSpriteShifts.COPPER_CASING)))
            .blockstate(EncasedRedstoneBlockStateGen.wrenchable.generate())
            .properties(prop -> prop
                    .strength(3.0f, 6.0f)
                    .sound(SoundType.COPPER)
                    .requiresCorrectToolForDrops())
            .item()
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllBlocks.COPPER_CASING.asItem())
                    .requires(Items.REDSTONE)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllBlocks.COPPER_CASING.asItem()))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<WrenchableEncasedRedstoneBlock> ANDESITE_ENCASED_REDSTONE = REGISTRATE.block("andesite_encased_redstone", WrenchableEncasedRedstoneBlock::new)
            .initialProperties(AllBlocks.ANDESITE_CASING)
            .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.ANDESITE_CASING)))
            .onRegister(casingConnectivity((block, cc) ->
                    cc.makeCasing(block, AllSpriteShifts.ANDESITE_CASING)))
            .blockstate(EncasedRedstoneBlockStateGen.wrenchable.generate())
            .properties(prop -> prop
                    .strength(3.0f, 6.0f)
                    .requiresCorrectToolForDrops())
            .item()
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllBlocks.ANDESITE_CASING.asItem())
                    .requires(Items.REDSTONE)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllBlocks.ANDESITE_CASING.asItem()))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<AnalogComputatorBlock> COMPUTATOR = REGISTRATE.block("analog_computator", AnalogComputatorBlock::new)
            .initialProperties(() -> Blocks.REPEATER)
            .blockstate(AnalogComputatorBlockStateGen.instance.generate())
            .blockEntity(AnalogComputatorBlockEntity::new)
            .build()
            .item()
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("T")
                    .pattern("C")
                    .pattern("B")
                    .define('T', Items.REDSTONE_TORCH)
                    .define('C', MiredItems.COMPUTATION_CIRCUIT)
                    .define('B', MiredItems.DIODE_BASE)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllItems.BRASS_SHEET))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<AnalogInverterBlock> ANALOG_INVERTER_BLOCK = REGISTRATE.block("analog_inverter", AnalogInverterBlock::new)
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogInverterBlockStateGen.instance.generate())
            .blockEntity(AnalogInverterBlockEntity::new)
            .build()
            .item()
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern("  R")
                    .pattern("TCR")
                    .pattern("SSS")
                    .define('R', Items.REDSTONE)
                    .define('T', Items.REDSTONE_TORCH)
                    .define('C', Items.COMPARATOR)
                    .define('S', MiredTags.Items.STONES)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(Items.COMPARATOR))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<AnalogSRLatchBlock> ANALOG_SR_LATCH_BLOCK = REGISTRATE.block("analog_sr_latch", AnalogSRLatchBlock::new)
            .lang("Analog SR Latch")
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogSRLatchBlockStateGen.instance.generate())
            .onRegister(ItemUseOverrides::addBlock)
            .item()
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern(" T ")
                    .pattern(" A ")
                    .pattern("EBE")
                    .define('T', Items.REDSTONE_TORCH)
                    .define('A', AllBlocks.ANALOG_LEVER.asItem())
                    .define('E', AllItems.ELECTRON_TUBE)
                    .define('B', MiredItems.DIODE_BASE)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(MiredItems.DIODE_BASE))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntityEntry<AnalogSRLatchBlockEntity> ANALOG_SR_LATCH_BLOCK_ENTITY = REGISTRATE
            .blockEntity("analog_sr_latch", AnalogSRLatchBlockEntity::new)
            .visual(() -> AnalogSRLatchVisual::new, false)
            .validBlocks(ANALOG_SR_LATCH_BLOCK)
            .renderer(() -> AnalogSRLatchRenderer::new)
            .register();

    public static final BlockEntry<AnalogGateBlock> ANALOG_GATE_BLOCK = REGISTRATE.block("analog_gate", AnalogGateBlock::new)
            .initialProperties(() -> Blocks.COMPARATOR)
            .blockstate(AnalogGateBlockStateGen.instance.generate())
            .blockEntity(AnalogGateBlockEntity::new)
            .build()
            .item()
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get(), 1)
                    .pattern(" T ")
                    .pattern("EBE")
                    .pattern(" R ")
                    .define('B', MiredItems.DIODE_BASE)
                    .define('T', Items.REDSTONE_TORCH)
                    .define('R', Items.REDSTONE)
                    .define('E', Items.ECHO_SHARD)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(MiredItems.DIODE_BASE))
                    .save(p)
            )
            .transform(customItemModel())
            .register();

    public static final BlockEntry<MeasuringRedstoneLinkBlock> MEASURING_REDSTONE_LINK = REGISTRATE.block("measuring_redstone_link", MeasuringRedstoneLinkBlock::new)
            .initialProperties(AllBlocks.REDSTONE_LINK::get)
            .blockstate(new MeasuringRedstoneLinkBlockStateGen()::generate)
            .blockEntity(MeasuringRedstoneLinkBlockEntity::new)
            .renderer(() -> SmartBlockEntityRenderer::new)
            .build()
            .item()
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 1)
                    .requires(AllBlocks.REDSTONE_LINK.asItem())
                    .requires(Items.COMPARATOR)
                    .unlockedBy("has_ingredient", RegistrateRecipeProvider.has(AllBlocks.REDSTONE_LINK.asItem()))
                    .save(p)
            )
            .transform(customItemModel("_", "transmitter"))
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
